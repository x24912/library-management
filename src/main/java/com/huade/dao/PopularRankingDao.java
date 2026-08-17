package com.huade.dao;

import com.huade.entity.PopularRanking;
import java.util.List;

public interface PopularRankingDao {
    List<PopularRanking> findAll();
    PopularRanking findById(int id);
    int insert(PopularRanking ranking);
    int update(PopularRanking ranking);
    int delete(int id);

    List<PopularRanking> search(String keyword, int page, int limit);
    int count(String keyword);
}
