package com.huade.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtils {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/library_db"
            + "?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai";
    private static final String USER = "root";
    private static final String PASS = "xz18676137952";

    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL driver not found", e);
        }
    }

    public Connection getConn() throws SQLException {
        conn = DriverManager.getConnection(URL, USER, PASS);
        return conn;
    }

    public void closeDB() {
        closeQuietly(rs);
        closeQuietly(pstmt);
        closeQuietly(conn);
    }

    private void closeQuietly(AutoCloseable ac) {
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int executeSQL(String preparedSql, String[] param) {
        int num = 0;
        try {
            conn = getConn();
            pstmt = conn.prepareStatement(preparedSql);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    pstmt.setString(i + 1, param[i]);
                }
            }
            num = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDB();
        }
        return num;
    }

    public ResultSet executeQuery(String preparedSql) {
        return executeQuery(preparedSql, null);
    }

    public ResultSet executeQuery(String preparedSql, String[] param) {
        try {
            conn = getConn();
            pstmt = conn.prepareStatement(preparedSql);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    pstmt.setString(i + 1, param[i]);
                }
            }
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
}