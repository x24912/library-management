package com.huade.mapper;

import com.huade.entity.Reader;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReaderMapper {
    Reader findById(int id);

    int insert(Reader reader);

    int update(Reader reader);

    int deleteById(int id);

    /** 已借数量增减（借书 +1 / 还书 -1），条件更新防止负数 */
    int updateBorrowedCount(@Param("id") int id, @Param("delta") int delta);

    List<Reader> search(@Param("keyword") String keyword, @Param("offset") int offset, @Param("limit") int limit);

    int count(@Param("keyword") String keyword);
}
