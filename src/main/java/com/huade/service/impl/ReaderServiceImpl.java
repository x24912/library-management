package com.huade.service.impl;

import com.huade.dao.ReaderDao;
import com.huade.dao.impl.ReaderDaoImpl;
import com.huade.entity.PageResult;
import com.huade.entity.Reader;
import com.huade.service.ReaderService;
import java.util.List;

public class ReaderServiceImpl implements ReaderService {
    private ReaderDao readerDao = new ReaderDaoImpl();

    @Override
    public List<Reader> findAll() {
        return readerDao.findAll();
    }

    @Override
    public Reader findById(int id) {
        return readerDao.findById(id);
    }

    @Override
    public int add(Reader reader) {
        return readerDao.insert(reader);
    }

    @Override
    public int modify(Reader reader) {
        return readerDao.update(reader);
    }

    @Override
    public int remove(int id) {
        return readerDao.delete(id);
    }

    @Override
    public PageResult<Reader> search(String keyword, int page, int limit) {
        List<Reader> data = readerDao.search(keyword, page, limit);
        int total = readerDao.count(keyword);
        return new PageResult<>(data, page, limit, total);
    }
}
