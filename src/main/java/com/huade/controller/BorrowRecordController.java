package com.huade.controller;

import com.huade.common.Result;
import com.huade.entity.BorrowRecord;
import com.huade.entity.OverdueFine;
import com.huade.entity.PageResult;
import com.huade.service.BorrowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrow")
public class BorrowRecordController {

    @Autowired
    private BorrowRecordService borrowRecordService;

    /** 分页列表 / 搜索：GET /api/borrow?page=1&limit=10&keyword=xxx */
    @GetMapping
    public Result<PageResult<BorrowRecord>> list(@RequestParam(defaultValue = "1") int page,
                                                 @RequestParam(defaultValue = "10") int limit,
                                                 @RequestParam(required = false) String keyword) {
        limit = Math.max(1, Math.min(limit, 100));
        page = Math.max(1, page);
        return Result.success(borrowRecordService.search(keyword, page, limit));
    }

    @GetMapping("/{id}")
    public Result<BorrowRecord> detail(@PathVariable int id) {
        BorrowRecord record = borrowRecordService.findById(id);
        if (record == null) {
            return Result.error(404, "数据未找到");
        }
        return Result.success(record);
    }

    /** 借书：POST /api/borrow，body 传 {bookId, readerId} */
    @PostMapping
    public Result<BorrowRecord> borrow(@RequestBody BorrowRecord req) {
        BorrowRecord record = borrowRecordService.borrow(req.getBookId(), req.getReaderId());
        return Result.success("借书成功", record);
    }

    /** 还书：PUT /api/borrow/{id}/return，超期自动生成罚款 */
    @PutMapping("/{id}/return")
    public Result<OverdueFine> returnBook(@PathVariable int id) {
        OverdueFine fine = borrowRecordService.returnBook(id);
        if (fine != null) {
            return Result.success("归还成功，逾期 " + fine.getOverdueDays() + " 天，罚款 "
                    + fine.getFineAmount() + " 元", fine);
        }
        return Result.success("归还成功", null);
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable int id, @RequestBody BorrowRecord record) {
        record.setId(id);
        if (borrowRecordService.modify(record) > 0) {
            return Result.success("更新成功", null);
        }
        return Result.error(404, "数据未找到");
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable int id) {
        borrowRecordService.remove(id);
        return Result.success("删除成功", null);
    }
}
