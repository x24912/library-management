package com.huade.dao;

import com.huade.entity.Reader;
import java.util.List;

public interface ReaderDao {
    List<Reader> findAll();
    Reader findById(int id);
    int insert(Reader reader);
    int update(Reader reader);
    int delete(int id);

    List<Reader> search(String keyword, int page, int limit);
    int count(String keyword);
}
