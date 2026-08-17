package com.huade.dao.impl;

import com.huade.dao.ReaderDao;
import com.huade.entity.Reader;
import com.huade.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReaderDaoImpl implements ReaderDao {

    @Override
    public List<Reader> findAll() {
        List<Reader> list = new ArrayList<>();
        String sql = "select * from reader";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapReader(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Reader findById(int id) {
        String sql = "select * from reader where id=?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapReader(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reader> search(String keyword, int page, int limit) {
        List<Reader> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("select * from reader where 1=1");
        List<String> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" and (full_name like ? or student_id like ? or class_name like ?)");
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
                    list.add(mapReader(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int count(String keyword) {
        StringBuilder sql = new StringBuilder("select count(*) from reader where 1=1");
        List<String> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" and (full_name like ? or student_id like ? or class_name like ?)");
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
    public int insert(Reader reader) {
        String sql = "insert into reader(full_name,student_id,class_name,max_borrow_count,borrowed_count,contact_number) values(?,?,?,?,?,?)";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, reader.getFullName());
            ps.setString(2, reader.getStudentId());
            ps.setString(3, reader.getClassName());
            ps.setInt(4, reader.getMaxBorrowCount());
            ps.setInt(5, reader.getBorrowedCount());
            ps.setString(6, reader.getContactNumber());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(Reader reader) {
        String sql = "update reader set full_name=?,student_id=?,class_name=?,max_borrow_count=?,borrowed_count=?,contact_number=? where id=?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, reader.getFullName());
            ps.setString(2, reader.getStudentId());
            ps.setString(3, reader.getClassName());
            ps.setInt(4, reader.getMaxBorrowCount());
            ps.setInt(5, reader.getBorrowedCount());
            ps.setString(6, reader.getContactNumber());
            ps.setInt(7, reader.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int delete(int id) {
        String sql = "delete from reader where id=?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private Reader mapReader(ResultSet rs) throws SQLException {
        Reader r = new Reader();
        r.setId(rs.getInt("id"));
        r.setFullName(rs.getString("full_name"));
        r.setStudentId(rs.getString("student_id"));
        r.setClassName(rs.getString("class_name"));
        r.setMaxBorrowCount(rs.getInt("max_borrow_count"));
        r.setBorrowedCount(rs.getInt("borrowed_count"));
        r.setContactNumber(rs.getString("contact_number"));
        return r;
    }
}
