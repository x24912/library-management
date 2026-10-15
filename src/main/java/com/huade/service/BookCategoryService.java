package com.huade.service;

import com.huade.entity.BookCategory;
import com.huade.entity.PageResult;

public interface BookCategoryService {
    BookCategory findById(int id);
    int add(BookCategory category);
    int modify(BookCategory category);
    int remove(int id);
    PageResult<BookCategory> search(String keyword, int page, int limit);
}
