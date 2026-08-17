package com.huade.dao.impl;

import com.huade.dao.BookReviewDao;
import com.huade.entity.BookReview;
import com.huade.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookReviewDaoImpl implements BookReviewDao {

    @Override
    public List<BookReview> findAll() {
        List<BookReview> list = new ArrayList<>();
        String sql = "select * from book_review";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapReview(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public BookReview findById(int id) {
        String sql = "select * from book_review where id=?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapReview(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BookReview> search(String keyword, int page, int limit) {
        List<BookReview> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("select * from book_review where 1=1");
        List<String> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" and (book_title like ? or reviewer_name like ? or review_content like ?)");
            String like = "%" + keyword.trim() + "%";
            params.add(like);
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
                    list.add(mapReview(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int count(String keyword) {
        StringBuilder sql = new StringBuilder("select count(*) from book_review where 1=1");
        List<String> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" and (book_title like ? or reviewer_name like ? or review_content like ?)");
            String like = "%" + keyword.trim() + "%";
            params.add(like);
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
    public int insert(BookReview review) {
        String sql = "insert into book_review(book_title,reviewer_name,rating,review_content,review_time) values(?,?,?,?,?)";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, review.getBookTitle());
            ps.setString(2, review.getReviewerName());
            ps.setInt(3, review.getRating());
            ps.setString(4, review.getReviewContent());
            ps.setString(5, review.getReviewTime());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(BookReview review) {
        String sql = "update book_review set book_title=?,reviewer_name=?,rating=?,review_content=?,review_time=? where id=?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, review.getBookTitle());
            ps.setString(2, review.getReviewerName());
            ps.setInt(3, review.getRating());
            ps.setString(4, review.getReviewContent());
            ps.setString(5, review.getReviewTime());
            ps.setInt(6, review.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int delete(int id) {
        String sql = "delete from book_review where id=?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private BookReview mapReview(ResultSet rs) throws SQLException {
        BookReview r = new BookReview();
        r.setId(rs.getInt("id"));
        r.setBookTitle(rs.getString("book_title"));
        r.setReviewerName(rs.getString("reviewer_name"));
        r.setRating(rs.getInt("rating"));
        r.setReviewContent(rs.getString("review_content"));
        r.setReviewTime(rs.getString("review_time"));
        return r;
    }
}
