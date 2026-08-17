package com.huade.dao.impl;

import com.huade.dao.BookDao;
import com.huade.entity.Book;
import com.huade.utils.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {
    DBUtils db = new DBUtils();

    @Override
    public List<Book> findAll() {
        List<Book> list = new ArrayList<>();
        String sql = "select * from book";
        ResultSet rs = db.executeQuery(sql);
        try {
            while (rs.next()) {
                list.add(mapBook(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeDB();
        }
        return list;
    }

    @Override
    public Book findById(int id) {
        Book b = null;
        String sql = "select * from book where id=?";
        ResultSet rs = db.executeQuery(sql, new String[]{String.valueOf(id)});
        try {
            if (rs.next()) {
                b = mapBook(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeDB();
        }
        return b;
    }

    @Override
    public List<Book> search(String keyword, int page, int limit) {
        List<Book> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("select * from book where 1=1");
        List<String> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" and (book_title like ? or author like ? or isbn like ?)");
            String like = "%" + keyword.trim() + "%";
            params.add(like);
            params.add(like);
            params.add(like);
        }
        sql.append(" order by id desc limit ").append(limit).append(" offset ").append((page - 1) * limit);

        ResultSet rs = db.executeQuery(sql.toString(), params.toArray(new String[0]));
        try {
            while (rs.next()) {
                list.add(mapBook(rs));
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
        StringBuilder sql = new StringBuilder("select count(*) from book where 1=1");
        List<String> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" and (book_title like ? or author like ? or isbn like ?)");
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
    public int insert(Book book) {
        String sql = "insert into book(book_title,author,isbn,category,stock_quantity,storage_location) values(?,?,?,?,?,?)";
        return db.executeSQL(sql, new String[]{
            book.getBookTitle(), book.getAuthor(), book.getIsbn(),
            book.getCategory(), String.valueOf(book.getStockQuantity()), book.getStorageLocation()
        });
    }

    @Override
    public int update(Book book) {
        String sql = "update book set book_title=?,author=?,isbn=?,category=?,stock_quantity=?,storage_location=? where id=?";
        return db.executeSQL(sql, new String[]{
            book.getBookTitle(), book.getAuthor(), book.getIsbn(),
            book.getCategory(), String.valueOf(book.getStockQuantity()), book.getStorageLocation(),
            String.valueOf(book.getId())
        });
    }

    @Override
    public int delete(int id) {
        String sql = "delete from book where id=?";
        return db.executeSQL(sql, new String[]{String.valueOf(id)});
    }

    private Book mapBook(ResultSet rs) throws SQLException {
        Book b = new Book();
        b.setId(rs.getInt("id"));
        b.setBookTitle(rs.getString("book_title"));
        b.setAuthor(rs.getString("author"));
        b.setIsbn(rs.getString("isbn"));
        b.setCategory(rs.getString("category"));
        b.setStockQuantity(rs.getInt("stock_quantity"));
        b.setStorageLocation(rs.getString("storage_location"));
        return b;
    }
}
