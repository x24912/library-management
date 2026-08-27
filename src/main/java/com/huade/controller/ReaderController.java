package com.huade.controller;

import com.huade.common.Result;
import com.huade.entity.PageResult;
import com.huade.entity.Reader;
import com.huade.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reader")
public class ReaderController {

    @Autowired
    private ReaderService readerService;

    @GetMapping
    public Result<PageResult<Reader>> list(@RequestParam(defaultValue = "1") int page,
                                           @RequestParam(defaultValue = "10") int limit,
                                           @RequestParam(required = false) String keyword) {
        limit = Math.max(1, Math.min(limit, 100));
        page = Math.max(1, page);
        return Result.success(readerService.search(keyword, page, limit));
    }

    @GetMapping("/{id}")
    public Result<Reader> detail(@PathVariable int id) {
        Reader reader = readerService.findById(id);
        if (reader == null) {
            return Result.error(404, "数据未找到");
        }
        return Result.success(reader);
    }

    @PostMapping
    public Result<Void> add(@RequestBody Reader reader) {
        readerService.add(reader);
        return Result.success("添加成功", null);
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable int id, @RequestBody Reader reader) {
        reader.setId(id);
        if (readerService.modify(reader) > 0) {
            return Result.success("更新成功", null);
        }
        return Result.error(404, "数据未找到");
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable int id) {
        readerService.remove(id);
        return Result.success("删除成功", null);
    }
}
