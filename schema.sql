-- ============================================================
-- 图书借阅管理系统 建表脚本（MySQL 8）
-- 新环境：先执行本文件，再执行 seed_data.sql 灌入演示数据
-- 已有环境升级：见 migrate.sql（只加列和回填，不动原数据）
-- ============================================================

CREATE DATABASE IF NOT EXISTS library_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE library_db;

-- 图书分类
CREATE TABLE IF NOT EXISTS book_category (
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    category_name       VARCHAR(50)  NOT NULL UNIQUE COMMENT '分类名称',
    allowed_borrow_days INT          NOT NULL DEFAULT 30 COMMENT '可借天数',
    description         VARCHAR(255) NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='图书分类';

-- 图书
CREATE TABLE IF NOT EXISTS book (
    id               INT AUTO_INCREMENT PRIMARY KEY,
    book_title       VARCHAR(200) NOT NULL COMMENT '书名',
    author           VARCHAR(100) NOT NULL COMMENT '作者',
    isbn             VARCHAR(50)  NULL COMMENT 'ISBN',
    category         VARCHAR(50)  NULL COMMENT '分类名称（对应 book_category.category_name）',
    stock_quantity   INT          NOT NULL DEFAULT 0 COMMENT '库存数量',
    storage_location VARCHAR(50)  NULL COMMENT '馆藏位置',
    INDEX idx_book_title (book_title),
    INDEX idx_book_category (category)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='图书';

-- 读者
CREATE TABLE IF NOT EXISTS reader (
    id               INT AUTO_INCREMENT PRIMARY KEY,
    full_name        VARCHAR(50) NOT NULL COMMENT '姓名',
    student_id       VARCHAR(30) NULL COMMENT '学号',
    class_name       VARCHAR(100) NULL COMMENT '班级',
    max_borrow_count INT         NOT NULL DEFAULT 5 COMMENT '最大可借数量',
    borrowed_count   INT         NOT NULL DEFAULT 0 COMMENT '当前已借数量',
    contact_number   VARCHAR(20) NULL COMMENT '联系电话',
    INDEX idx_reader_name (full_name)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='读者';

-- 借阅记录（book_id/reader_id 外键，book_title/reader_name 为冗余快照）
CREATE TABLE IF NOT EXISTS borrow_record (
    id                INT AUTO_INCREMENT PRIMARY KEY,
    book_id           INT          NULL COMMENT '图书ID',
    reader_id         INT          NULL COMMENT '读者ID',
    book_title        VARCHAR(200) NULL COMMENT '书名快照',
    reader_name       VARCHAR(50)  NULL COMMENT '读者姓名快照',
    borrow_date       DATE         NOT NULL COMMENT '借出日期',
    due_date          DATE         NOT NULL COMMENT '应还日期',
    actual_return_date DATE        NULL COMMENT '实际归还日期',
    status            VARCHAR(20)  NOT NULL DEFAULT '借出' COMMENT '借出/已还/逾期',
    INDEX idx_borrow_book (book_id),
    INDEX idx_borrow_reader (reader_id),
    INDEX idx_borrow_status (status),
    CONSTRAINT fk_borrow_book FOREIGN KEY (book_id) REFERENCES book (id),
    CONSTRAINT fk_borrow_reader FOREIGN KEY (reader_id) REFERENCES reader (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='借阅记录';

-- 逾期罚款（还书时自动生成）
CREATE TABLE IF NOT EXISTS overdue_fine (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    reader_name    VARCHAR(50)  NOT NULL COMMENT '读者姓名',
    book_title     VARCHAR(200) NOT NULL COMMENT '书名',
    overdue_days   INT          NOT NULL COMMENT '逾期天数',
    fine_amount    DECIMAL(10, 2) NOT NULL COMMENT '罚款金额（0.5元/天）',
    payment_status VARCHAR(20)  NOT NULL DEFAULT '未缴纳' COMMENT '未缴纳/已缴纳'
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='逾期罚款';

-- 图书评价
CREATE TABLE IF NOT EXISTS book_review (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    book_title     VARCHAR(200) NOT NULL COMMENT '书名',
    reviewer_name  VARCHAR(50)  NOT NULL COMMENT '评价人',
    rating         INT          NOT NULL DEFAULT 5 COMMENT '评分 1-5',
    review_content TEXT         NULL COMMENT '评价内容',
    review_time    DATETIME     NULL COMMENT '评价时间'
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='图书评价';

-- 热门排行：原 popular_ranking 表已删除
-- 排行榜现由 borrow_record 实时聚合（见 BorrowRecordMapper.xml 的 selectRanking），
-- 该表无任何代码写入或读取，纯属历史遗留，故移除。

-- 管理员（登录认证）
CREATE TABLE IF NOT EXISTS admin (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(50)  NOT NULL UNIQUE COMMENT '用户名',
    password   VARCHAR(255) NOT NULL COMMENT 'PBKDF2 密码哈希',
    created_at DATETIME     DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='管理员';
