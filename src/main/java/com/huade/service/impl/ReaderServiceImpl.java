package com.huade.service.impl;

import com.huade.entity.PageResult;
import com.huade.entity.Reader;
import com.huade.mapper.ReaderMapper;
import com.huade.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderServiceImpl implements ReaderService {

    @Autowired
    private ReaderMapper readerMapper;

    @Override
    public List<Reader> findAll() {
        return readerMapper.findAll();
    }

    @Override
    public Reader findById(int id) {
        return readerMapper.findById(id);
    }

    @Override
    public int add(Reader reader) {
        return readerMapper.insert(reader);
    }

    @Override
    public int modify(Reader reader) {
        return readerMapper.update(reader);
    }

    @Override
    public int remove(int id) {
        return readerMapper.deleteById(id);
    }

    @Override
    public PageResult<Reader> search(String keyword, int page, int limit) {
        List<Reader> data = readerMapper.search(keyword, (page - 1) * limit, limit);
        int total = readerMapper.count(keyword);
        return new PageResult<>(data, page, limit, total);
    }
}
