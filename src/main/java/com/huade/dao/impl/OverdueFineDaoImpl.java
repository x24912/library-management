package com.huade.dao.impl;

import com.huade.dao.OverdueFineDao;
import com.huade.entity.OverdueFine;
import com.huade.utils.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OverdueFineDaoImpl implements OverdueFineDao {
    DBUtils db = new DBUtils();

    @Override
    public List<OverdueFine> findAll() {
        List<OverdueFine> list = new ArrayList<>();
        String sql = "select * from overdue_fine";
        ResultSet rs = db.executeQuery(sql);
        try {
            while (rs.next()) {
                list.add(mapFine(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeDB();
        }
        return list;
    }

    @Override
    public OverdueFine findById(int id) {
        OverdueFine f = null;
        String sql = "select * from overdue_fine where id=?";
        ResultSet rs = db.executeQuery(sql, new String[]{String.valueOf(id)});
        try {
            if (rs.next()) {
                f = mapFine(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeDB();
        }
        return f;
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
        sql.append(" order by id desc limit ").append(limit).append(" offset ").append((page - 1) * limit);

        ResultSet rs = db.executeQuery(sql.toString(), params.toArray(new String[0]));
        try {
            while (rs.next()) {
                list.add(mapFine(rs));
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
        StringBuilder sql = new StringBuilder("select count(*) from overdue_fine where 1=1");
        List<String> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" and (reader_name like ? or book_title like ? or payment_status like ?)");
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
    public int insert(OverdueFine fine) {
        String sql = "insert into overdue_fine(reader_name,book_title,overdue_days,fine_amount,payment_status) values(?,?,?,?,?)";
        return db.executeSQL(sql, new String[]{
            fine.getReaderName(), fine.getBookTitle(), String.valueOf(fine.getOverdueDays()),
            String.valueOf(fine.getFineAmount()), fine.getPaymentStatus()
        });
    }

    @Override
    public int update(OverdueFine fine) {
        String sql = "update overdue_fine set reader_name=?,book_title=?,overdue_days=?,fine_amount=?,payment_status=? where id=?";
        return db.executeSQL(sql, new String[]{
            fine.getReaderName(), fine.getBookTitle(), String.valueOf(fine.getOverdueDays()),
            String.valueOf(fine.getFineAmount()), fine.getPaymentStatus(),
            String.valueOf(fine.getId())
        });
    }

    @Override
    public int delete(int id) {
        String sql = "delete from overdue_fine where id=?";
        return db.executeSQL(sql, new String[]{String.valueOf(id)});
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
