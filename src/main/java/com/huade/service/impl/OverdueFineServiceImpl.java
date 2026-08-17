package com.huade.service.impl;

import com.huade.dao.OverdueFineDao;
import com.huade.dao.impl.OverdueFineDaoImpl;
import com.huade.entity.OverdueFine;
import com.huade.entity.PageResult;
import com.huade.service.OverdueFineService;
import java.util.List;

public class OverdueFineServiceImpl implements OverdueFineService {
    private OverdueFineDao fineDao = new OverdueFineDaoImpl();

    @Override
    public List<OverdueFine> findAll() {
        return fineDao.findAll();
    }

    @Override
    public OverdueFine findById(int id) {
        return fineDao.findById(id);
    }

    @Override
    public int add(OverdueFine fine) {
        return fineDao.insert(fine);
    }

    @Override
    public int modify(OverdueFine fine) {
        return fineDao.update(fine);
    }

    @Override
    public int remove(int id) {
        return fineDao.delete(id);
    }

    @Override
    public PageResult<OverdueFine> search(String keyword, int page, int limit) {
        List<OverdueFine> data = fineDao.search(keyword, page, limit);
        int total = fineDao.count(keyword);
        return new PageResult<>(data, page, limit, total);
    }
}
