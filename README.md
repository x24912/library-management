# 图书借阅管理系统（SSM 版）

基于 **SSM（Spring 5 + Spring MVC + MyBatis）+ Vue 3** 的前后端分离图书借阅管理系统，JavaEE 大作业。

## 功能模块

| 模块 | 说明 |
|---|---|
| 图书管理 | 图书 CRUD、按书名/作者/ISBN 搜索、库存管理 |
| 读者管理 | 读者 CRUD、借阅额度（已借数/最大可借数） |
| 借阅记录 | **借书 / 还书完整业务闭环**：借书校验库存与额度、自动计算应还日期（按分类可借天数）；还书自动恢复库存、超期自动生成罚款（0.5 元/天） |
| 图书分类 | 分类 CRUD，配置各类别可借天数 |
| 逾期罚款 | 罚款记录查看与缴纳状态管理（还书时自动生成） |
| 图书评价 | 读者评分与评价 CRUD |
| 热门排行 | 从借阅记录**实时聚合**（无需手工维护） |
| 登录认证 | 管理员登录（Session + 拦截器）、PBKDF2 密码哈希 |

## 技术栈

- 后端：Java 17、Spring 5.3、Spring MVC 5.3、MyBatis 3.5、MyBatis-Spring、HikariCP、Jackson、MySQL 8
- 前端：Vue 3、Vite、Vue Router、Pinia、Axios
- 部署：Maven WAR 打包，外置 Tomcat 9

## 项目结构

```
├── pom.xml                       # Maven 配置（WAR 打包）
├── schema.sql                    # 建库建表脚本（新环境）
├── migrate.sql                   # 旧库升级脚本（老版本 → SSM 版）
├── seed_data.sql                 # 演示数据（含默认管理员）
├── src/main/java/com/huade/
│   ├── controller/               # RestController + 全局异常处理
│   ├── service/                  # 业务层（借/还书事务 @Transactional）
│   ├── mapper/                   # MyBatis Mapper 接口
│   ├── entity/                   # 实体类
│   ├── interceptor/              # 登录拦截器
│   └── utils/                    # 密码哈希工具
├── src/main/resources/
│   ├── applicationContext.xml    # Spring 根容器（数据源/MyBatis/事务）
│   ├── spring-mvc.xml            # Spring MVC 配置（控制器/拦截器/CORS）
│   ├── mybatis-config.xml        # MyBatis 配置（驼峰映射）
│   ├── mapper/                   # Mapper XML（SQL）
│   └── jdbc.properties(.example) # 数据库配置（不入库）
└── library-vue/                  # Vue 3 前端
```

## 快速启动

### 1. 数据库

```bash
mysql -uroot -p < schema.sql      # 新环境：建库建表
mysql -uroot -p < seed_data.sql   # 灌入演示数据
# 已有旧库：执行 migrate.sql 升级（加外键列 + admin 表）
```

数据库连接配置在 `src/main/resources/jdbc.properties`（已 gitignore，参考 `jdbc.properties.example` 创建）。

### 2. 后端（Tomcat 9）

```bash
mvn package                       # 产出 target/library-management-1.0-SNAPSHOT.war
cp target/library-management-1.0-SNAPSHOT.war $TOMCAT/webapps/library_management_war.war
$TOMCAT/bin/startup.sh            # 启动 Tomcat（JDK 17 + JAVA_HOME）
```

部署后接口地址：`http://localhost:8080/library_management_war/api`

### 3. 前端（Vue 开发服务器）

```bash
cd library-vue
npm install
npm run dev                       # http://localhost:3000（/api 已代理到后端）
```

## 默认账号

| 用户名 | 密码 |
|---|---|
| admin | 123456 |

> 生产环境请通过数据库修改或重新生成密码哈希（`PasswordUtil.hash("新密码")`）。

## 核心业务说明

**借书** `POST /api/borrow`（body: `{bookId, readerId}`，事务）：
1. 校验图书/读者存在、未重复借阅、读者未达最大可借数
2. 条件更新扣库存（`stock_quantity > 0` 才扣成功，防超借）
3. 按图书分类的 `allowed_borrow_days` 计算应还日期，写入借阅记录
4. 读者已借数 +1；任一步失败整体回滚

**还书** `PUT /api/borrow/{id}/return`（事务）：
1. 写入实际归还日期、状态置为已还
2. 超期自动生成 `overdue_fine`（0.5 元/天）
3. 库存 +1、读者已借数 -1

**热门排行** `GET /api/ranking`：`SELECT book_title, COUNT(*) FROM borrow_record GROUP BY book_title ORDER BY COUNT(*) DESC` 实时聚合。

## 验证

- 后端：`mvn package` 编译打包；接口可用 curl / 前端页面验证
- 全流程：登录 → 借书（库存/已借数变化）→ 还书（库存恢复、超期生成罚款）→ 排行自动更新 → 未登录访问 API 返回 401
