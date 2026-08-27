package com.huade.mapper;

import com.huade.entity.BookReview;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookReviewMapper {
    List<BookReview> findAll();

    BookReview findById(int id);

    int insert(BookReview review);

    int update(BookReview review);

    int deleteById(int id);

    List<BookReview> search(@Param("keyword") String keyword, @Param("offset") int offset, @Param("limit") int limit);

    int count(@Param("keyword") String keyword);
}
