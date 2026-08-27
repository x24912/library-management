-- ============================================================
-- 图书借阅管理系统 数据库升级脚本（老版本 -> SSM 版）
-- 只加列/回填/加约束，不删除或修改原数据，可重复执行
-- ============================================================
USE library_db;

-- 1. 借阅记录增加外键列
ALTER TABLE borrow_record
    ADD COLUMN book_id INT NULL COMMENT '图书ID' AFTER id;
ALTER TABLE borrow_record
    ADD COLUMN reader_id INT NULL COMMENT '读者ID' AFTER book_id;

-- 2. 用冗余快照（book_title/reader_name）回填外键
UPDATE borrow_record br
    JOIN book b ON b.book_title = br.book_title
SET br.book_id = b.id;

UPDATE borrow_record br
    JOIN reader r ON r.full_name = br.reader_name
SET br.reader_id = r.id;

-- 3. 加外键约束与索引（若已有约束会报错，可忽略；约束冲突说明存在脏数据）
ALTER TABLE borrow_record
    ADD INDEX idx_borrow_book (book_id),
    ADD INDEX idx_borrow_reader (reader_id),
    ADD INDEX idx_borrow_status (status);
ALTER TABLE borrow_record
    ADD CONSTRAINT fk_borrow_book FOREIGN KEY (book_id) REFERENCES book (id),
    ADD CONSTRAINT fk_borrow_reader FOREIGN KEY (reader_id) REFERENCES reader (id);

-- 4. 管理员表（登录认证）
CREATE TABLE IF NOT EXISTS admin (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(50)  NOT NULL UNIQUE COMMENT '用户名',
    password   VARCHAR(255) NOT NULL COMMENT 'PBKDF2 密码哈希',
    created_at DATETIME     DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='管理员';
