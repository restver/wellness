# Wellness 后端开发 - 对话摘要

**日期**: 2026-02-03
**目标**: 为 Wellness 应用创建 Spring Boot 后台服务

---

## 项目概览

### 创建的文件结构

```
backend/
├── pom.xml                          # Maven 配置
├── README.md                        # 项目文档
├── .gitignore                       # Git 忽略文件
└── src/main/
    ├── java/com/studyai/wellness/
    │   ├── WellnessBackendApplication.java    # 主启动类
    │   ├── config/
    │   │   └── DataInitializer.java          # 测试数据初始化
    │   ├── controller/                       # REST 控制器 (5个)
    │   ├── dto/                              # 数据传输对象 (15个)
    │   ├── entity/                           # JPA 实体类 (7个)
    │   ├── repository/                       # 数据访问层 (6个)
    │   ├── service/                          # 业务逻辑层 (5个)
    │   ├── security/                         # 安全配置 (4个)
    │   └── exception/                        # 异常处理 (2个)
    └── resources/
        └── application.yml                    # 应用配置
```

**统计**: 共 50+ 个 Java 文件

---

## 技术栈

- **Java 17**
- **Spring Boot 3.2.1**
- **Spring Security + JWT**
- **Spring Data JPA**
- **H2 数据库** (内存模式)
- **Maven**
- **Lombok**

---

## API 端点

### 认证
- `POST /api/v1/auth/login` - 用户登录
- `POST /api/v1/auth/forgot-password` - 忘记密码
- `POST /api/v1/auth/logout` - 登出

### 仪表板
- `GET /api/v1/dashboard` - 获取仪表板数据

### 统计
- `GET /api/v1/stats` - 获取统计数据

### 通知
- `GET /api/v1/notifications` - 获取通知列表
- `PUT /api/v1/notifications/{id}/read` - 标记为已读
- `PUT /api/v1/notifications/read-all` - 全部标记为已读

### 用户
- `GET /api/v1/user/profile` - 获取用户资料
- `PUT /api/v1/user/preferences` - 更新用户偏好

---

## 测试账号

```
邮箱: user@example.com
密码: password123
```

---

## 数据库配置

**当前**: H2 内存数据库
```
JDBC URL: jdbc:h2:mem:wellnessdb
用户名: sa
密码: (空)
```

**H2 控制台**: http://localhost:8080/api/v1/h2-console

---

## 在 IDEA 中运行

### 方法一：直接运行主类
1. 打开 `WellnessBackendApplication.java`
2. 右键 → `Run 'WellnessBackendApplication'`

### 方法二：使用 Maven 插件
1. 打开右侧 `Maven` 工具窗口
2. 展开 `Plugins` → `spring-boot`
3. 双击 `spring-boot:run`

### 方法三：创建运行配置
1. `Run` → `Edit Configurations...`
2. 点击 `+` → `Spring Boot`
3. 配置：
   - Name: `Wellness Backend`
   - Main class: `com.studyai.wellness.WellnessBackendApplication`
4. 保存并运行

---

## Git 配置

### 两个 .gitignore 文件

1. **根目录 `.gitignore`** - 影响 iOS/Android 项目
2. **`backend/.gitignore`** - 影响 Java/Maven 项目

两者**同时生效**，各司其职，无需合并。

---

## 关键问题解决

### 1. 缺失实体类错误
创建了 `WeeklyProgress` 和 `DayProgress` 实体类及其 Repository。

### 2. H2 数据库表未找到
修改 `application.yml`：
- 添加 `DB_CLOSE_DELAY=-1`
- 添加 `MODE=MYSQL`
- 改为 `ddl-auto: create-drop`
- 添加 `defer-datasource-initialization: true`

---

## 下一步建议

1. ✅ 运行项目并测试 API
2. 📝 编写单元测试
3. 🔒 添加 API 文档 (Swagger/OpenAPI)
4. 🚀 部署到云服务器
5. 📱 与 iOS/Android 客户端集成

---

## 项目亮点

- ✅ 清晰的分层架构 (Controller → Service → Repository)
- ✅ JWT 无状态认证
- ✅ 完整的异常处理
- ✅ 详细的 Javadoc 注释
- ✅ 自动初始化测试数据
- ✅ H2 控制台支持
- ✅ 符合企业级 Spring Boot 最佳实践

---

**创建者**: Claude (Anthropic)
**项目状态**: ✅ 完成并可运行
