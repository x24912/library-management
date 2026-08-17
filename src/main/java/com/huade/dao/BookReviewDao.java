package com.huade.dao;

import com.huade.entity.BookReview;
import java.util.List;

public interface BookReviewDao {
    List<BookReview> findAll();
    BookReview findById(int id);
    int insert(BookReview review);
    int update(BookReview review);
    int delete(int id);

    List<BookReview> search(String keyword, int page, int limit);
    int count(String keyword);
}
