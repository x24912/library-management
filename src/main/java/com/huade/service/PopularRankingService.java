package com.huade.service;

import com.huade.entity.PopularRanking;
import com.huade.entity.PageResult;
import java.util.List;

public interface PopularRankingService {
    List<PopularRanking> findAll();
    PopularRanking findById(int id);
    int add(PopularRanking ranking);
    int modify(PopularRanking ranking);
    int remove(int id);
    PageResult<PopularRanking> search(String keyword, int page, int limit);
}
