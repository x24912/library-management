package com.huade.service.impl;

import com.huade.entity.OverdueFine;
import com.huade.entity.PageResult;
import com.huade.mapper.OverdueFineMapper;
import com.huade.service.OverdueFineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OverdueFineServiceImpl implements OverdueFineService {

    @Autowired
    private OverdueFineMapper overdueFineMapper;

    @Override
    public OverdueFine findById(int id) {
        return overdueFineMapper.findById(id);
    }

    @Override
    public int add(OverdueFine fine) {
        return overdueFineMapper.insert(fine);
    }

    @Override
    public int modify(OverdueFine fine) {
        return overdueFineMapper.update(fine);
    }

    @Override
    public int remove(int id) {
        return overdueFineMapper.deleteById(id);
    }

    @Override
    public PageResult<OverdueFine> search(String keyword, int page, int limit) {
        List<OverdueFine> data = overdueFineMapper.search(keyword, (page - 1) * limit, limit);
        int total = overdueFineMapper.count(keyword);
        return new PageResult<>(data, page, limit, total);
    }
}
