package com.huade.service.impl;

import com.huade.dao.BorrowRecordDao;
import com.huade.dao.impl.BorrowRecordDaoImpl;
import com.huade.entity.BorrowRecord;
import com.huade.entity.PageResult;
import com.huade.service.BorrowRecordService;
import java.util.List;

public class BorrowRecordServiceImpl implements BorrowRecordService {
    private BorrowRecordDao borrowRecordDao = new BorrowRecordDaoImpl();

    @Override
    public List<BorrowRecord> findAll() {
        return borrowRecordDao.findAll();
    }

    @Override
    public BorrowRecord findById(int id) {
        return borrowRecordDao.findById(id);
    }

    @Override
    public int add(BorrowRecord record) {
        return borrowRecordDao.insert(record);
    }

    @Override
    public int modify(BorrowRecord record) {
        return borrowRecordDao.update(record);
    }

    @Override
    public int remove(int id) {
        return borrowRecordDao.delete(id);
    }

    @Override
    public PageResult<BorrowRecord> search(String keyword, int page, int limit) {
        List<BorrowRecord> data = borrowRecordDao.search(keyword, page, limit);
        int total = borrowRecordDao.count(keyword);
        return new PageResult<>(data, page, limit, total);
    }
}
