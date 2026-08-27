package com.huade.mapper;

import com.huade.entity.BorrowRecord;
import com.huade.entity.PopularRanking;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BorrowRecordMapper {
    List<BorrowRecord> findAll();

    BorrowRecord findById(int id);

    int insert(BorrowRecord record);

    int update(BorrowRecord record);

    int deleteById(int id);

    /** 某读者当前在借某书的记录数（借书时查重：>0 说明已借未还） */
    int countActive(@Param("bookId") int bookId, @Param("readerId") int readerId);

    /** 热门排行：按借阅次数从 borrow_record 实时聚合 */
    List<PopularRanking> selectRanking(@Param("limit") int limit);

    List<BorrowRecord> search(@Param("keyword") String keyword, @Param("offset") int offset, @Param("limit") int limit);

    int count(@Param("keyword") String keyword);
}
