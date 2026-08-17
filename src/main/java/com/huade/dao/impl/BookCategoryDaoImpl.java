package com.huade.dao.impl;

import com.huade.dao.BookCategoryDao;
import com.huade.entity.BookCategory;
import com.huade.utils.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookCategoryDaoImpl implements BookCategoryDao {
    DBUtils db = new DBUtils();

    @Override
    public List<BookCategory> findAll() {
        List<BookCategory> list = new ArrayList<>();
        String sql = "select * from book_category";
        ResultSet rs = db.executeQuery(sql);
        try {
            while (rs.next()) {
                list.add(mapCategory(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeDB();
        }
        return list;
    }

    @Override
    public BookCategory findById(int id) {
        BookCategory c = null;
        String sql = "select * from book_category where id=?";
        ResultSet rs = db.executeQuery(sql, new String[]{String.valueOf(id)});
        try {
            if (rs.next()) {
                c = mapCategory(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeDB();
        }
        return c;
    }

    @Override
    public List<BookCategory> search(String keyword, int page, int limit) {
        List<BookCategory> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("select * from book_category where 1=1");
        List<String> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" and (category_name like ? or description like ?)");
            String like = "%" + keyword.trim() + "%";
            params.add(like);
            params.add(like);
        }
        sql.append(" order by id desc limit ").append(limit).append(" offset ").append((page - 1) * limit);

        ResultSet rs = db.executeQuery(sql.toString(), params.toArray(new String[0]));
        try {
            while (rs.next()) {
                list.add(mapCategory(rs));
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
        StringBuilder sql = new StringBuilder("select count(*) from book_category where 1=1");
        List<String> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" and (category_name like ? or description like ?)");
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
    public int insert(BookCategory category) {
        String sql = "insert into book_category(category_name,allowed_borrow_days,description) values(?,?,?)";
        return db.executeSQL(sql, new String[]{
            category.getCategoryName(), String.valueOf(category.getAllowedBorrowDays()), category.getDescription()
        });
    }

    @Override
    public int update(BookCategory category) {
        String sql = "update book_category set category_name=?,allowed_borrow_days=?,description=? where id=?";
        return db.executeSQL(sql, new String[]{
            category.getCategoryName(), String.valueOf(category.getAllowedBorrowDays()), category.getDescription(),
            String.valueOf(category.getId())
        });
    }

    @Override
    public int delete(int id) {
        String sql = "delete from book_category where id=?";
        return db.executeSQL(sql, new String[]{String.valueOf(id)});
    }

    private BookCategory mapCategory(ResultSet rs) throws SQLException {
        BookCategory c = new BookCategory();
        c.setId(rs.getInt("id"));
        c.setCategoryName(rs.getString("category_name"));
        c.setAllowedBorrowDays(rs.getInt("allowed_borrow_days"));
        c.setDescription(rs.getString("description"));
        return c;
    }
}
