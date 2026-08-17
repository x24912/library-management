package com.huade.dao.impl;

import com.huade.dao.BookReviewDao;
import com.huade.entity.BookReview;
import com.huade.utils.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookReviewDaoImpl implements BookReviewDao {
    DBUtils db = new DBUtils();

    @Override
    public List<BookReview> findAll() {
        List<BookReview> list = new ArrayList<>();
        String sql = "select * from book_review";
        ResultSet rs = db.executeQuery(sql);
        try {
            while (rs.next()) {
                list.add(mapReview(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeDB();
        }
        return list;
    }

    @Override
    public BookReview findById(int id) {
        BookReview r = null;
        String sql = "select * from book_review where id=?";
        ResultSet rs = db.executeQuery(sql, new String[]{String.valueOf(id)});
        try {
            if (rs.next()) {
                r = mapReview(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeDB();
        }
        return r;
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
        sql.append(" order by id desc limit ").append(limit).append(" offset ").append((page - 1) * limit);

        ResultSet rs = db.executeQuery(sql.toString(), params.toArray(new String[0]));
        try {
            while (rs.next()) {
                list.add(mapReview(rs));
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
        StringBuilder sql = new StringBuilder("select count(*) from book_review where 1=1");
        List<String> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" and (book_title like ? or reviewer_name like ? or review_content like ?)");
            String like = "%" + keyword.trim() + "%";
            params.add(like);
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
    public int insert(BookReview review) {
        String sql = "insert into book_review(book_title,reviewer_name,rating,review_content,review_time) values(?,?,?,?,?)";
        return db.executeSQL(sql, new String[]{
            review.getBookTitle(), review.getReviewerName(), String.valueOf(review.getRating()),
            review.getReviewContent(), review.getReviewTime()
        });
    }

    @Override
    public int update(BookReview review) {
        String sql = "update book_review set book_title=?,reviewer_name=?,rating=?,review_content=?,review_time=? where id=?";
        return db.executeSQL(sql, new String[]{
            review.getBookTitle(), review.getReviewerName(), String.valueOf(review.getRating()),
            review.getReviewContent(), review.getReviewTime(),
            String.valueOf(review.getId())
        });
    }

    @Override
    public int delete(int id) {
        String sql = "delete from book_review where id=?";
        return db.executeSQL(sql, new String[]{String.valueOf(id)});
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
