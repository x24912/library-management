package com.huade.dao.impl;

import com.huade.dao.OverdueFineDao;
import com.huade.entity.OverdueFine;
import com.huade.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OverdueFineDaoImpl implements OverdueFineDao {

    @Override
    public List<OverdueFine> findAll() {
        List<OverdueFine> list = new ArrayList<>();
        String sql = "select * from overdue_fine";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapFine(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public OverdueFine findById(int id) {
        String sql = "select * from overdue_fine where id=?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapFine(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<OverdueFine> search(String keyword, int page, int limit) {
        List<OverdueFine> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("select * from overdue_fine where 1=1");
        List<String> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" and (reader_name like ? or book_title like ? or payment_status like ?)");
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
                    list.add(mapFine(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int count(String keyword) {
        StringBuilder sql = new StringBuilder("select count(*) from overdue_fine where 1=1");
        List<String> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" and (reader_name like ? or book_title like ? or payment_status like ?)");
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
    public int insert(OverdueFine fine) {
        String sql = "insert into overdue_fine(reader_name,book_title,overdue_days,fine_amount,payment_status) values(?,?,?,?,?)";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fine.getReaderName());
            ps.setString(2, fine.getBookTitle());
            ps.setInt(3, fine.getOverdueDays());
            ps.setDouble(4, fine.getFineAmount());
            ps.setString(5, fine.getPaymentStatus());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(OverdueFine fine) {
        String sql = "update overdue_fine set reader_name=?,book_title=?,overdue_days=?,fine_amount=?,payment_status=? where id=?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fine.getReaderName());
            ps.setString(2, fine.getBookTitle());
            ps.setInt(3, fine.getOverdueDays());
            ps.setDouble(4, fine.getFineAmount());
            ps.setString(5, fine.getPaymentStatus());
            ps.setInt(6, fine.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int delete(int id) {
        String sql = "delete from overdue_fine where id=?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private OverdueFine mapFine(ResultSet rs) throws SQLException {
        OverdueFine f = new OverdueFine();
        f.setId(rs.getInt("id"));
        f.setReaderName(rs.getString("reader_name"));
        f.setBookTitle(rs.getString("book_title"));
        f.setOverdueDays(rs.getInt("overdue_days"));
        f.setFineAmount(rs.getDouble("fine_amount"));
        f.setPaymentStatus(rs.getString("payment_status"));
        return f;
    }
}
