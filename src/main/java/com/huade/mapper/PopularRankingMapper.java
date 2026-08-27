package com.huade.mapper;

import com.huade.entity.PopularRanking;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PopularRankingMapper {
    List<PopularRanking> findAll();

    PopularRanking findById(int id);

    int insert(PopularRanking ranking);

    int update(PopularRanking ranking);

    int deleteById(int id);

    List<PopularRanking> search(@Param("keyword") String keyword, @Param("offset") int offset, @Param("limit") int limit);

    int count(@Param("keyword") String keyword);
}
