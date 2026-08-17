package com.huade.service;

import com.huade.entity.BookCategory;
import com.huade.entity.PageResult;
import java.util.List;

public interface BookCategoryService {
    List<BookCategory> findAll();
    BookCategory findById(int id);
    int add(BookCategory category);
    int modify(BookCategory category);
    int remove(int id);
    PageResult<BookCategory> search(String keyword, int page, int limit);
}
