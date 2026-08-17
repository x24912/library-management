package com.huade.service;

import com.huade.entity.OverdueFine;
import com.huade.entity.PageResult;
import java.util.List;

public interface OverdueFineService {
    List<OverdueFine> findAll();
    OverdueFine findById(int id);
    int add(OverdueFine fine);
    int modify(OverdueFine fine);
    int remove(int id);
    PageResult<OverdueFine> search(String keyword, int page, int limit);
}
