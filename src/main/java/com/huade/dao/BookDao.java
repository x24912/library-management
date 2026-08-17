package com.huade.dao;

import com.huade.entity.Book;
import java.util.List;

public interface BookDao {
    List<Book> findAll();
    Book findById(int id);
    int insert(Book book);
    int update(Book book);
    int delete(int id);

    /** 分页搜索：keyword 模糊匹配书名/作者/ISBN，返回指定页数据 */
    List<Book> search(String keyword, int page, int limit);

    /** 搜索匹配的总记录数 */
    int count(String keyword);
}
