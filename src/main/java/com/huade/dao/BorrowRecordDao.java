package com.huade.dao;

import com.huade.entity.BorrowRecord;
import java.util.List;

public interface BorrowRecordDao {
    List<BorrowRecord> findAll();
    BorrowRecord findById(int id);
    int insert(BorrowRecord record);
    int update(BorrowRecord record);
    int delete(int id);

    List<BorrowRecord> search(String keyword, int page, int limit);
    int count(String keyword);
}
