package com.huade.dao.impl;

import com.huade.dao.BorrowRecordDao;
import com.huade.entity.BorrowRecord;
import com.huade.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowRecordDaoImpl implements BorrowRecordDao {

    @Override
    public List<BorrowRecord> findAll() {
        List<BorrowRecord> list = new ArrayList<>();
        String sql = "select * from borrow_record";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapBorrowRecord(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public BorrowRecord findById(int id) {
        String sql = "select * from borrow_record where id=?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapBorrowRecord(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BorrowRecord> search(String keyword, int page, int limit) {
        List<BorrowRecord> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("select * from borrow_record where 1=1");
        List<String> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" and (book_title like ? or reader_name like ? or status like ?)");
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
                    list.add(mapBorrowRecord(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int count(String keyword) {
        StringBuilder sql = new StringBuilder("select count(*) from borrow_record where 1=1");
        List<String> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" and (book_title like ? or reader_name like ? or status like ?)");
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
    public int insert(BorrowRecord record) {
        String sql = "insert into borrow_record(book_title,reader_name,borrow_date,due_date,actual_return_date,status) values(?,?,?,?,?,?)";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, record.getBookTitle());
            ps.setString(2, record.getReaderName());
            ps.setString(3, record.getBorrowDate());
            ps.setString(4, record.getDueDate());
            ps.setString(5, record.getActualReturnDate());
            ps.setString(6, record.getStatus());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(BorrowRecord record) {
        String sql = "update borrow_record set book_title=?,reader_name=?,borrow_date=?,due_date=?,actual_return_date=?,status=? where id=?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, record.getBookTitle());
            ps.setString(2, record.getReaderName());
            ps.setString(3, record.getBorrowDate());
            ps.setString(4, record.getDueDate());
            ps.setString(5, record.getActualReturnDate());
            ps.setString(6, record.getStatus());
            ps.setInt(7, record.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int delete(int id) {
        String sql = "delete from borrow_record where id=?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private BorrowRecord mapBorrowRecord(ResultSet rs) throws SQLException {
        BorrowRecord r = new BorrowRecord();
        r.setId(rs.getInt("id"));
        r.setBookTitle(rs.getString("book_title"));
        r.setReaderName(rs.getString("reader_name"));
        r.setBorrowDate(rs.getString("borrow_date"));
        r.setDueDate(rs.getString("due_date"));
        r.setActualReturnDate(rs.getString("actual_return_date"));
        r.setStatus(rs.getString("status"));
        return r;
    }
}
