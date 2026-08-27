package com.huade.service;

import com.huade.entity.PopularRanking;

import java.util.List;

public interface PopularRankingService {
    /** 热门排行：从借阅记录实时聚合，返回前 limit 名 */
    List<PopularRanking> getRanking(int limit);
}
