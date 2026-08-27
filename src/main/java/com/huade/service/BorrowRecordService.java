package com.huade.service;

import com.huade.entity.BorrowRecord;
import com.huade.entity.OverdueFine;
import com.huade.entity.PageResult;

import java.util.List;

public interface BorrowRecordService {
    List<BorrowRecord> findAll();

    BorrowRecord findById(int id);

    /** 借书：校验库存/读者额度/重复借阅，扣库存、记已借数、生成借阅记录（事务） */
    BorrowRecord borrow(int bookId, int readerId);

    /** 还书：写归还日期，超期自动生成罚款，恢复库存与已借数（事务）；返回生成的罚款（无逾期为 null） */
    OverdueFine returnBook(int id);

    int modify(BorrowRecord record);

    int remove(int id);

    PageResult<BorrowRecord> search(String keyword, int page, int limit);
}
