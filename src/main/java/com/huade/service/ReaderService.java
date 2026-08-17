package com.huade.service;

import com.huade.entity.Reader;
import com.huade.entity.PageResult;
import java.util.List;

public interface ReaderService {
    List<Reader> findAll();
    Reader findById(int id);
    int add(Reader reader);
    int modify(Reader reader);
    int remove(int id);
    PageResult<Reader> search(String keyword, int page, int limit);
}
