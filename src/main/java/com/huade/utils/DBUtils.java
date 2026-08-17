package com.huade.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据库连接池工具类（HikariCP）
 * 连接信息从 classpath 下的 jdbc.properties 读取（该文件不入库，见 .gitignore）
 */
public class DBUtils {

    private static HikariDataSource dataSource;

    static {
        // 0. 显式注册 MySQL 驱动（容器环境下 ServiceLoader 自动注册不可靠，必须手动）
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("MySQL driver not found: " + e.getMessage());
        }

        // 1. 读取 jdbc.properties（classpath 根目录）
        Properties props = new Properties();
        try (InputStream in = DBUtils.class.getClassLoader()
                .getResourceAsStream("jdbc.properties")) {
            if (in == null) {
                throw new ExceptionInInitializerError(
                        "jdbc.properties not found in classpath, copy jdbc.properties.example to jdbc.properties");
            }
            props.load(in);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }

        // 2. 用配置初始化连接池（显式指定驱动类，双保险）
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl(props.getProperty("jdbc.url"));
        config.setUsername(props.getProperty("jdbc.username"));
        config.setPassword(props.getProperty("jdbc.password"));
        config.setMaximumPoolSize(Integer.parseInt(props.getProperty("jdbc.maxPoolSize", "10")));
        config.setMinimumIdle(Integer.parseInt(props.getProperty("jdbc.minIdle", "5")));
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        dataSource = new HikariDataSource(config);
    }

    /** 从连接池借一个连接，调用方用完必须 close()（实际是归还池中） */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
