package com.huade.service;

import com.huade.entity.BookReview;
import com.huade.entity.PageResult;
import java.util.List;

public interface BookReviewService {
    List<BookReview> findAll();
    BookReview findById(int id);
    int add(BookReview review);
    int modify(BookReview review);
    int remove(int id);
    PageResult<BookReview> search(String keyword, int page, int limit);
}
