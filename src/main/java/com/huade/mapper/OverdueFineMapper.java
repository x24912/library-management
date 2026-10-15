package com.huade.mapper;

import com.huade.entity.OverdueFine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OverdueFineMapper {
    OverdueFine findById(int id);

    int insert(OverdueFine fine);

    int update(OverdueFine fine);

    int deleteById(int id);

    List<OverdueFine> search(@Param("keyword") String keyword, @Param("offset") int offset, @Param("limit") int limit);

    int count(@Param("keyword") String keyword);
}
