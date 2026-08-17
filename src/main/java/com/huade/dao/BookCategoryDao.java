package com.huade.dao;

import com.huade.entity.BookCategory;
import java.util.List;

public interface BookCategoryDao {
    List<BookCategory> findAll();
    BookCategory findById(int id);
    int insert(BookCategory category);
    int update(BookCategory category);
    int delete(int id);

    List<BookCategory> search(String keyword, int page, int limit);
    int count(String keyword);
}
