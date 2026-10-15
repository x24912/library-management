package com.huade.service.impl;

import com.huade.entity.Book;
import com.huade.entity.BookCategory;
import com.huade.entity.BorrowRecord;
import com.huade.entity.OverdueFine;
import com.huade.entity.PageResult;
import com.huade.entity.Reader;
import com.huade.mapper.BookCategoryMapper;
import com.huade.mapper.BookMapper;
import com.huade.mapper.BorrowRecordMapper;
import com.huade.mapper.OverdueFineMapper;
import com.huade.mapper.ReaderMapper;
import com.huade.service.BorrowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BorrowRecordServiceImpl implements BorrowRecordService {

    /** 逾期罚款单价：0.5 元/天（与种子数据口径一致） */
    private static final double FINE_PER_DAY = 0.5;

    /** 图书分类查不到时默认的可借天数 */
    private static final int DEFAULT_BORROW_DAYS = 30;

    @Autowired
    private BorrowRecordMapper borrowRecordMapper;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private ReaderMapper readerMapper;
    @Autowired
    private BookCategoryMapper bookCategoryMapper;
    @Autowired
    private OverdueFineMapper overdueFineMapper;

    @Override
    public BorrowRecord findById(int id) {
        return borrowRecordMapper.findById(id);
    }

    @Override
    @Transactional
    public BorrowRecord borrow(int bookId, int readerId) {
        Book book = bookMapper.findById(bookId);
        if (book == null) {
            throw new IllegalArgumentException("图书不存在");
        }
        Reader reader = readerMapper.findById(readerId);
        if (reader == null) {
            throw new IllegalArgumentException("读者不存在");
        }
        // 同一读者不能重复借同一本书（未还状态）
        if (borrowRecordMapper.countActive(bookId, readerId) > 0) {
            throw new IllegalArgumentException("该读者已在借阅此书，请先归还");
        }
        // 读者借阅额度校验
        if (reader.getBorrowedCount() >= reader.getMaxBorrowCount()) {
            throw new IllegalArgumentException("该读者已达到最大借阅数量（" + reader.getMaxBorrowCount() + " 本）");
        }
        // 扣库存：条件更新，库存不足时影响行数为 0，防超借
        if (bookMapper.updateStock(bookId, -1) == 0) {
            throw new IllegalArgumentException("图书库存不足");
        }

        // 应还日期 = 借出日期 + 分类可借天数
        BookCategory category = bookCategoryMapper.findByCategoryName(book.getCategory());
        int days = category != null ? category.getAllowedBorrowDays() : DEFAULT_BORROW_DAYS;
        LocalDate today = LocalDate.now();

        BorrowRecord record = new BorrowRecord();
        record.setBookId(bookId);
        record.setReaderId(readerId);
        record.setBookTitle(book.getBookTitle());
        record.setReaderName(reader.getFullName());
        record.setBorrowDate(today.toString());
        record.setDueDate(today.plusDays(days).toString());
        record.setStatus("借出");
        borrowRecordMapper.insert(record);
        readerMapper.updateBorrowedCount(readerId, 1);
        return record;
    }

    @Override
    @Transactional
    public OverdueFine returnBook(int id) {
        BorrowRecord record = borrowRecordMapper.findById(id);
        if (record == null) {
            throw new IllegalArgumentException("借阅记录不存在");
        }
        if ("已还".equals(record.getStatus())) {
            throw new IllegalArgumentException("该记录已归还，请勿重复操作");
        }

        // 逾期天数 = 今天 - 应还日期（未超期为 0）
        long overdueDays = 0;
        LocalDate due = parseDate(record.getDueDate());
        if (due != null) {
            long diff = ChronoUnit.DAYS.between(due, LocalDate.now());
            overdueDays = Math.max(0, diff);
        }

        OverdueFine fine = null;
        if (overdueDays > 0) {
            fine = new OverdueFine();
            fine.setReaderName(record.getReaderName());
            fine.setBookTitle(record.getBookTitle());
            fine.setOverdueDays((int) overdueDays);
            fine.setFineAmount(overdueDays * FINE_PER_DAY);
            fine.setPaymentStatus("未缴纳");
            overdueFineMapper.insert(fine);
        }

        record.setActualReturnDate(LocalDate.now().toString());
        record.setStatus("已还");
        borrowRecordMapper.update(record);

        // 恢复库存与已借数（旧数据可能没有外键 ID，>0 才执行）
        if (record.getBookId() > 0) {
            bookMapper.updateStock(record.getBookId(), 1);
        }
        if (record.getReaderId() > 0) {
            readerMapper.updateBorrowedCount(record.getReaderId(), -1);
        }
        return fine;
    }

    @Override
    public int modify(BorrowRecord record) {
        return borrowRecordMapper.update(record);
    }

    @Override
    public int remove(int id) {
        return borrowRecordMapper.deleteById(id);
    }

    @Override
    public PageResult<BorrowRecord> search(String keyword, int page, int limit) {
        List<BorrowRecord> data = borrowRecordMapper.search(keyword, (page - 1) * limit, limit);
        int total = borrowRecordMapper.count(keyword);
        return new PageResult<>(data, page, limit, total);
    }

    /**
     * 兼容两种日期格式：旧表列为 datetime（yyyy-MM-dd HH:mm:ss），新写入为 date（yyyy-MM-dd）
     */
    private LocalDate parseDate(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(value);
        } catch (DateTimeParseException e) {
            // MySQL datetime 返回 "yyyy-MM-dd HH:mm:ss"，ISO 标准要求 T 分隔，替换后解析
            return LocalDateTime.parse(value.trim().replace(' ', 'T')).toLocalDate();
        }
    }
}
