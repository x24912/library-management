package com.huade.mapper;

import com.huade.entity.Book;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookMapper {
    Book findById(int id);

    int insert(Book book);

    int update(Book book);

    int deleteById(int id);

    /**
     * 库存增减（借书 -1 / 还书 +1）。
     * 条件更新：delta 为负时要求当前库存足够扣减，否则影响行数为 0，可防超借。
     */
    int updateStock(@Param("id") int id, @Param("delta") int delta);

    /** 分页搜索：keyword 模糊匹配书名/作者/ISBN */
    List<Book> search(@Param("keyword") String keyword, @Param("offset") int offset, @Param("limit") int limit);

    /** 搜索匹配的总记录数 */
    int count(@Param("keyword") String keyword);
}
