package com.huade.service.impl;

import com.huade.dao.PopularRankingDao;
import com.huade.dao.impl.PopularRankingDaoImpl;
import com.huade.entity.PageResult;
import com.huade.entity.PopularRanking;
import com.huade.service.PopularRankingService;
import java.util.List;

public class PopularRankingServiceImpl implements PopularRankingService {
    private PopularRankingDao rankingDao = new PopularRankingDaoImpl();

    @Override
    public List<PopularRanking> findAll() {
        return rankingDao.findAll();
    }

    @Override
    public PopularRanking findById(int id) {
        return rankingDao.findById(id);
    }

    @Override
    public int add(PopularRanking ranking) {
        return rankingDao.insert(ranking);
    }

    @Override
    public int modify(PopularRanking ranking) {
        return rankingDao.update(ranking);
    }

    @Override
    public int remove(int id) {
        return rankingDao.delete(id);
    }

    @Override
    public PageResult<PopularRanking> search(String keyword, int page, int limit) {
        List<PopularRanking> data = rankingDao.search(keyword, page, limit);
        int total = rankingDao.count(keyword);
        return new PageResult<>(data, page, limit, total);
    }
}
