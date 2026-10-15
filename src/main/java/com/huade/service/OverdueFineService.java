package com.huade.service;

import com.huade.entity.OverdueFine;
import com.huade.entity.PageResult;

public interface OverdueFineService {
    OverdueFine findById(int id);
    int add(OverdueFine fine);
    int modify(OverdueFine fine);
    int remove(int id);
    PageResult<OverdueFine> search(String keyword, int page, int limit);
}
