package com.huade.service;

import com.huade.entity.Reader;
import com.huade.entity.PageResult;

public interface ReaderService {
    Reader findById(int id);
    int add(Reader reader);
    int modify(Reader reader);
    int remove(int id);
    PageResult<Reader> search(String keyword, int page, int limit);
}
