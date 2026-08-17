package com.huade.dao;

import com.huade.entity.OverdueFine;
import java.util.List;

public interface OverdueFineDao {
    List<OverdueFine> findAll();
    OverdueFine findById(int id);
    int insert(OverdueFine fine);
    int update(OverdueFine fine);
    int delete(int id);

    List<OverdueFine> search(String keyword, int page, int limit);
    int count(String keyword);
}
