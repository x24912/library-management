package com.huade.controller;

import com.huade.common.Result;
import com.huade.entity.BookCategory;
import com.huade.entity.PageResult;
import com.huade.service.BookCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class BookCategoryController {

    @Autowired
    private BookCategoryService bookCategoryService;

    @GetMapping
    public Result<PageResult<BookCategory>> list(@RequestParam(defaultValue = "1") int page,
                                                 @RequestParam(defaultValue = "10") int limit,
                                                 @RequestParam(required = false) String keyword) {
        limit = Math.max(1, Math.min(limit, 100));
        page = Math.max(1, page);
        return Result.success(bookCategoryService.search(keyword, page, limit));
    }

    @GetMapping("/{id}")
    public Result<BookCategory> detail(@PathVariable int id) {
        BookCategory category = bookCategoryService.findById(id);
        if (category == null) {
            return Result.error(404, "数据未找到");
        }
        return Result.success(category);
    }

    @PostMapping
    public Result<Void> add(@RequestBody BookCategory category) {
        bookCategoryService.add(category);
        return Result.success("添加成功", null);
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable int id, @RequestBody BookCategory category) {
        category.setId(id);
        if (bookCategoryService.modify(category) > 0) {
            return Result.success("更新成功", null);
        }
        return Result.error(404, "数据未找到");
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable int id) {
        bookCategoryService.remove(id);
        return Result.success("删除成功", null);
    }
}
