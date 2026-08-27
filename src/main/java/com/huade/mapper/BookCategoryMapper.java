package com.huade.mapper;

import com.huade.entity.BookCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookCategoryMapper {
    List<BookCategory> findAll();

    BookCategory findById(int id);

    /** 按分类名查询（图书的 category 存的是分类名称，借书时据此取可借天数） */
    BookCategory findByCategoryName(@Param("categoryName") String categoryName);

    int insert(BookCategory category);

    int update(BookCategory category);

    int deleteById(int id);

    List<BookCategory> search(@Param("keyword") String keyword, @Param("offset") int offset, @Param("limit") int limit);

    int count(@Param("keyword") String keyword);
}
