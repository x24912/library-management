package com.huade.dao.impl;

import com.huade.dao.BookCategoryDao;
import com.huade.entity.BookCategory;
import com.huade.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookCategoryDaoImpl implements BookCategoryDao {

    @Override
    public List<BookCategory> findAll() {
        List<BookCategory> list = new ArrayList<>();
        String sql = "select * from book_category";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapCategory(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public BookCategory findById(int id) {
        String sql = "select * from book_category where id=?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapCategory(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
        sql.append(" order by category_name asc limit ? offset ?");

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
                    list.add(mapCategory(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
    public int insert(BookCategory category) {
        String sql = "insert into book_category(category_name,allowed_borrow_days,description) values(?,?,?)";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category.getCategoryName());
            ps.setInt(2, category.getAllowedBorrowDays());
            ps.setString(3, category.getDescription());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(BookCategory category) {
        String sql = "update book_category set category_name=?,allowed_borrow_days=?,description=? where id=?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category.getCategoryName());
            ps.setInt(2, category.getAllowedBorrowDays());
            ps.setString(3, category.getDescription());
            ps.setInt(4, category.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int delete(int id) {
        String sql = "delete from book_category where id=?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
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
