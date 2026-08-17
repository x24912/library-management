package com.huade.dao.impl;

import com.huade.dao.PopularRankingDao;
import com.huade.entity.PopularRanking;
import com.huade.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PopularRankingDaoImpl implements PopularRankingDao {

    @Override
    public List<PopularRanking> findAll() {
        List<PopularRanking> list = new ArrayList<>();
        String sql = "select * from popular_ranking";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRanking(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public PopularRanking findById(int id) {
        String sql = "select * from popular_ranking where id=?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRanking(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
        sql.append(" order by id desc limit ? offset ?");

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int i = 1;
            for (String p : params) {
                ps.setString(i++, p);
            }
            ps.setInt(i++, limit);
            ps.setInt(i, (page - 1) * limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRanking(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int i = 1;
            for (String p : params) {
                ps.setString(i++, p);
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int insert(PopularRanking ranking) {
        String sql = "insert into popular_ranking(book_title,monthly_borrow_times,total_borrow_times,statistics_month) values(?,?,?,?)";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ranking.getBookTitle());
            ps.setInt(2, ranking.getMonthlyBorrowTimes());
            ps.setInt(3, ranking.getTotalBorrowTimes());
            ps.setString(4, ranking.getStatisticsMonth());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(PopularRanking ranking) {
        String sql = "update popular_ranking set book_title=?,monthly_borrow_times=?,total_borrow_times=?,statistics_month=? where id=?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ranking.getBookTitle());
            ps.setInt(2, ranking.getMonthlyBorrowTimes());
            ps.setInt(3, ranking.getTotalBorrowTimes());
            ps.setString(4, ranking.getStatisticsMonth());
            ps.setInt(5, ranking.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int delete(int id) {
        String sql = "delete from popular_ranking where id=?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
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
