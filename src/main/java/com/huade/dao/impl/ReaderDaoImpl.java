package com.huade.dao.impl;

import com.huade.dao.ReaderDao;
import com.huade.entity.Reader;
import com.huade.utils.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReaderDaoImpl implements ReaderDao {
    DBUtils db = new DBUtils();

    @Override
    public List<Reader> findAll() {
        List<Reader> list = new ArrayList<>();
        String sql = "select * from reader";
        ResultSet rs = db.executeQuery(sql);
        try {
            while (rs.next()) {
                list.add(mapReader(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeDB();
        }
        return list;
    }

    @Override
    public Reader findById(int id) {
        Reader r = null;
        String sql = "select * from reader where id=?";
        ResultSet rs = db.executeQuery(sql, new String[]{String.valueOf(id)});
        try {
            if (rs.next()) {
                r = mapReader(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeDB();
        }
        return r;
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
        sql.append(" order by id desc limit ").append(limit).append(" offset ").append((page - 1) * limit);

        ResultSet rs = db.executeQuery(sql.toString(), params.toArray(new String[0]));
        try {
            while (rs.next()) {
                list.add(mapReader(rs));
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
        StringBuilder sql = new StringBuilder("select count(*) from reader where 1=1");
        List<String> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" and (full_name like ? or student_id like ? or class_name like ?)");
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
    public int insert(Reader reader) {
        String sql = "insert into reader(full_name,student_id,class_name,max_borrow_count,borrowed_count,contact_number) values(?,?,?,?,?,?)";
        return db.executeSQL(sql, new String[]{
            reader.getFullName(), reader.getStudentId(), reader.getClassName(),
            String.valueOf(reader.getMaxBorrowCount()), String.valueOf(reader.getBorrowedCount()), reader.getContactNumber()
        });
    }

    @Override
    public int update(Reader reader) {
        String sql = "update reader set full_name=?,student_id=?,class_name=?,max_borrow_count=?,borrowed_count=?,contact_number=? where id=?";
        return db.executeSQL(sql, new String[]{
            reader.getFullName(), reader.getStudentId(), reader.getClassName(),
            String.valueOf(reader.getMaxBorrowCount()), String.valueOf(reader.getBorrowedCount()), reader.getContactNumber(),
            String.valueOf(reader.getId())
        });
    }

    @Override
    public int delete(int id) {
        String sql = "delete from reader where id=?";
        return db.executeSQL(sql, new String[]{String.valueOf(id)});
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
