package com.huade.controller;

import com.huade.common.Result;
import com.huade.entity.PopularRanking;
import com.huade.service.PopularRankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 热门排行：数据来自借阅记录的实时聚合，不再手工维护 popular_ranking 表
 */
@RestController
@RequestMapping("/api/ranking")
public class PopularRankingController {

    @Autowired
    private PopularRankingService popularRankingService;

    @GetMapping
    public Result<List<PopularRanking>> list(@RequestParam(defaultValue = "10") int limit) {
        limit = Math.max(1, Math.min(limit, 100));
        return Result.success(popularRankingService.getRanking(limit));
    }
}
