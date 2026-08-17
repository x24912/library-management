package com.huade.service;

import com.huade.entity.BorrowRecord;
import com.huade.entity.PageResult;
import java.util.List;

public interface BorrowRecordService {
    List<BorrowRecord> findAll();
    BorrowRecord findById(int id);
    int add(BorrowRecord record);
    int modify(BorrowRecord record);
    int remove(int id);
    PageResult<BorrowRecord> search(String keyword, int page, int limit);
}
