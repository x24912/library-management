package com.huade.dao.impl;

import com.huade.dao.BorrowRecordDao;
import com.huade.entity.BorrowRecord;
import com.huade.utils.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowRecordDaoImpl implements BorrowRecordDao {
    DBUtils db = new DBUtils();

    @Override
    public List<BorrowRecord> findAll() {
        List<BorrowRecord> list = new ArrayList<>();
        String sql = "select * from borrow_record";
        ResultSet rs = db.executeQuery(sql);
        try {
            while (rs.next()) {
                list.add(mapBorrowRecord(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeDB();
        }
        return list;
    }

    @Override
    public BorrowRecord findById(int id) {
        BorrowRecord r = null;
        String sql = "select * from borrow_record where id=?";
        ResultSet rs = db.executeQuery(sql, new String[]{String.valueOf(id)});
        try {
            if (rs.next()) {
                r = mapBorrowRecord(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeDB();
        }
        return r;
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
        sql.append(" order by id desc limit ").append(limit).append(" offset ").append((page - 1) * limit);

        ResultSet rs = db.executeQuery(sql.toString(), params.toArray(new String[0]));
        try {
            while (rs.next()) {
                list.add(mapBorrowRecord(rs));
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
        StringBuilder sql = new StringBuilder("select count(*) from borrow_record where 1=1");
        List<String> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" and (book_title like ? or reader_name like ? or status like ?)");
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
    public int insert(BorrowRecord record) {
        String sql = "insert into borrow_record(book_title,reader_name,borrow_date,due_date,actual_return_date,status) values(?,?,?,?,?,?)";
        return db.executeSQL(sql, new String[]{
            record.getBookTitle(), record.getReaderName(), record.getBorrowDate(),
            record.getDueDate(), record.getActualReturnDate(), record.getStatus()
        });
    }

    @Override
    public int update(BorrowRecord record) {
        String sql = "update borrow_record set book_title=?,reader_name=?,borrow_date=?,due_date=?,actual_return_date=?,status=? where id=?";
        return db.executeSQL(sql, new String[]{
            record.getBookTitle(), record.getReaderName(), record.getBorrowDate(),
            record.getDueDate(), record.getActualReturnDate(), record.getStatus(),
            String.valueOf(record.getId())
        });
    }

    @Override
    public int delete(int id) {
        String sql = "delete from borrow_record where id=?";
        return db.executeSQL(sql, new String[]{String.valueOf(id)});
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
