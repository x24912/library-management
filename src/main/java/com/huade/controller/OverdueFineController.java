package com.huade.controller;

import com.huade.common.Result;
import com.huade.entity.OverdueFine;
import com.huade.entity.PageResult;
import com.huade.service.OverdueFineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fine")
public class OverdueFineController {

    @Autowired
    private OverdueFineService overdueFineService;

    @GetMapping
    public Result<PageResult<OverdueFine>> list(@RequestParam(defaultValue = "1") int page,
                                                @RequestParam(defaultValue = "10") int limit,
                                                @RequestParam(required = false) String keyword) {
        limit = Math.max(1, Math.min(limit, 100));
        page = Math.max(1, page);
        return Result.success(overdueFineService.search(keyword, page, limit));
    }

    @GetMapping("/{id}")
    public Result<OverdueFine> detail(@PathVariable int id) {
        OverdueFine fine = overdueFineService.findById(id);
        if (fine == null) {
            return Result.error(404, "数据未找到");
        }
        return Result.success(fine);
    }

    @PostMapping
    public Result<Void> add(@RequestBody OverdueFine fine) {
        overdueFineService.add(fine);
        return Result.success("添加成功", null);
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable int id, @RequestBody OverdueFine fine) {
        fine.setId(id);
        if (overdueFineService.modify(fine) > 0) {
            return Result.success("更新成功", null);
        }
        return Result.error(404, "数据未找到");
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable int id) {
        overdueFineService.remove(id);
        return Result.success("删除成功", null);
    }
}
