package com.huade.service;

import com.huade.entity.BookReview;
import com.huade.entity.PageResult;

public interface BookReviewService {
    BookReview findById(int id);
    int add(BookReview review);
    int modify(BookReview review);
    int remove(int id);
    PageResult<BookReview> search(String keyword, int page, int limit);
}
