package com.huade.dao.impl;

import com.huade.dao.PopularRankingDao;
import com.huade.entity.PopularRanking;
import com.huade.utils.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PopularRankingDaoImpl implements PopularRankingDao {
    DBUtils db = new DBUtils();

    @Override
    public List<PopularRanking> findAll() {
        List<PopularRanking> list = new ArrayList<>();
        String sql = "select * from popular_ranking";
        ResultSet rs = db.executeQuery(sql);
        try {
            while (rs.next()) {
                list.add(mapRanking(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeDB();
        }
        return list;
    }

    @Override
    public PopularRanking findById(int id) {
        PopularRanking r = null;
        String sql = "select * from popular_ranking where id=?";
        ResultSet rs = db.executeQuery(sql, new String[]{String.valueOf(id)});
        try {
            if (rs.next()) {
                r = mapRanking(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeDB();
        }
        return r;
    }

    @Override
    public List<PopularRanking> search(String keyword, int page, int limit) {
        List<PopularRanking> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("select * from popular_ranking where 1=1");
        List<String> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" and (book_title like ? or statistics_month like ?)");
            String like = "%" + keyword.trim() + "%";
            params.add(like);
            params.add(like);
        }
        sql.append(" order by id desc limit ").append(limit).append(" offset ").append((page - 1) * limit);

        ResultSet rs = db.executeQuery(sql.toString(), params.toArray(new String[0]));
        try {
            while (rs.next()) {
                list.add(mapRanking(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeDB();
        }
        return list;
    }
    @Override
    public int count(String keyword) {
        StringBuilder sql = new StringBuilder("select count(*) from popular_ranking where 1=1");
        List<String> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" and (book_title like ? or statistics_month like ?)");
            String like = "%" + keyword.trim() + "%";
            params.add(like);
            params.add(like);
        }
        ResultSet rs = db.executeQuery(sql.toString(), params.toArray(new String[0]));
        try {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeDB();
        }
        return 0;
    }

    @Override
    public int insert(PopularRanking ranking) {
        String sql = "insert into popular_ranking(book_title,monthly_borrow_times,total_borrow_times,statistics_month) values(?,?,?,?)";
        return db.executeSQL(sql, new String[]{
            ranking.getBookTitle(), String.valueOf(ranking.getMonthlyBorrowTimes()),
            String.valueOf(ranking.getTotalBorrowTimes()), ranking.getStatisticsMonth()
        });
    }

    @Override
    public int update(PopularRanking ranking) {
        String sql = "update popular_ranking set book_title=?,monthly_borrow_times=?,total_borrow_times=?,statistics_month=? where id=?";
        return db.executeSQL(sql, new String[]{
            ranking.getBookTitle(), String.valueOf(ranking.getMonthlyBorrowTimes()),
            String.valueOf(ranking.getTotalBorrowTimes()), ranking.getStatisticsMonth(),
            String.valueOf(ranking.getId())
        });
    }

    @Override
    public int delete(int id) {
        String sql = "delete from popular_ranking where id=?";
        return db.executeSQL(sql, new String[]{String.valueOf(id)});
    }

    private PopularRanking mapRanking(ResultSet rs) throws SQLException {
        PopularRanking r = new PopularRanking();
        r.setId(rs.getInt("id"));
        r.setBookTitle(rs.getString("book_title"));
        r.setMonthlyBorrowTimes(rs.getInt("monthly_borrow_times"));
        r.setTotalBorrowTimes(rs.getInt("total_borrow_times"));
        r.setStatisticsMonth(rs.getString("statistics_month"));
        return r;
    }
}
