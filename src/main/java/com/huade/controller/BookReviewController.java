package com.huade.controller;

import com.huade.common.Result;
import com.huade.entity.BookReview;
import com.huade.entity.PageResult;
import com.huade.service.BookReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
public class BookReviewController {

    @Autowired
    private BookReviewService bookReviewService;

    @GetMapping
    public Result<PageResult<BookReview>> list(@RequestParam(defaultValue = "1") int page,
                                               @RequestParam(defaultValue = "10") int limit,
                                               @RequestParam(required = false) String keyword) {
        limit = Math.max(1, Math.min(limit, 100));
        page = Math.max(1, page);
        return Result.success(bookReviewService.search(keyword, page, limit));
    }

    @GetMapping("/{id}")
    public Result<BookReview> detail(@PathVariable int id) {
        BookReview review = bookReviewService.findById(id);
        if (review == null) {
            return Result.error(404, "数据未找到");
        }
        return Result.success(review);
    }

    @PostMapping
    public Result<Void> add(@RequestBody BookReview review) {
        bookReviewService.add(review);
        return Result.success("添加成功", null);
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable int id, @RequestBody BookReview review) {
        review.setId(id);
        if (bookReviewService.modify(review) > 0) {
            return Result.success("更新成功", null);
        }
        return Result.error(404, "数据未找到");
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable int id) {
        bookReviewService.remove(id);
        return Result.success("删除成功", null);
    }
}
