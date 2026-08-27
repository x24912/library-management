package com.huade.service.impl;

import com.huade.entity.PopularRanking;
import com.huade.mapper.BorrowRecordMapper;
import com.huade.service.PopularRankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PopularRankingServiceImpl implements PopularRankingService {

    @Autowired
    private BorrowRecordMapper borrowRecordMapper;

    @Override
    public List<PopularRanking> getRanking(int limit) {
        return borrowRecordMapper.selectRanking(limit);
    }
}
