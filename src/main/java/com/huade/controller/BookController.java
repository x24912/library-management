package com.huade.controller;

import com.huade.common.Result;
import com.huade.entity.Book;
import com.huade.entity.PageResult;
import com.huade.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private BookService bookService;

    /** 分页列表 / 搜索：GET /api/book?page=1&limit=10&keyword=xxx */
    @GetMapping
    public Result<PageResult<Book>> list(@RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "10") int limit,
                                         @RequestParam(required = false) String keyword) {
        limit = Math.max(1, Math.min(limit, 100));
        page = Math.max(1, page);
        return Result.success(bookService.search(keyword, page, limit));
    }

    /** 详情：GET /api/book/{id} */
    @GetMapping("/{id}")
    public Result<Book> detail(@PathVariable int id) {
        Book book = bookService.findById(id);
        if (book == null) {
            return Result.error(404, "数据未找到");
        }
        return Result.success(book);
    }

    @PostMapping
    public Result<Void> add(@RequestBody Book book) {
        bookService.add(book);
        return Result.success("添加成功", null);
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable int id, @RequestBody Book book) {
        book.setId(id);
        if (bookService.modify(book) > 0) {
            return Result.success("更新成功", null);
        }
        return Result.error(404, "数据未找到");
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable int id) {
        bookService.remove(id);
        return Result.success("删除成功", null);
    }
}
