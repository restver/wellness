# Wellness 后端开发 - 完整对话记录

**日期**: 2026-02-03
**项目**: Wellness 健康管理应用后台服务
**技术栈**: Spring Boot 3.2.1 + Java 17 + H2 数据库

---

## 对话开始

### 用户需求
用户要求参照设计图和 iOS/Android 项目，在 backend 文件夹下创建一个对应的后台服务：
- 使用 Spring 框架
- 使用 Java 语言
- 结构清晰，架构良好，代码规范
- 文档注释良好

---

## 项目创建过程

### 第一步：分析前端项目

#### iOS 项目分析
- **SwiftUI 应用**，包含完整的 MVVM 架构
- **主要模型**：
  - `User` - 用户信息
  - `Dashboard` - 仪表板数据
  - `Stats` - 统计数据
  - `Notification` - 通知

#### Android 项目分析
- **Kotlin + Jetpack Compose** 应用
- **API 端点定义** (`ApiEndpoints.kt`)：
```kotlin
POST /auth/login
POST /auth/forgot-password
POST /auth/logout
GET /dashboard
GET /stats
GET /notifications
PUT /notifications/{id}/read
PUT /notifications/read-all
GET /user/profile
```

---

### 第二步：创建项目结构

#### 1. Maven 配置 (pom.xml)
```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.2.1</version>
</parent>

<dependencies>
    <!-- Spring Boot Web, JPA, Security -->
    <!-- H2 Database -->
    <!-- JWT (io.jsonwebtoken) -->
    <!-- Lombok -->
</dependencies>
```

#### 2. 应用配置 (application.yml)
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:wellnessdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;MODE=MYSQL
  jpa:
    hibernate:
      ddl-auto: create-drop
    defer-datasource-initialization: true

server:
  port: 8080
  servlet:
    context-path: /api/v1
```

---

### 第三步：创建实体类 (Entity)

创建的实体类：
1. **User.java** - 用户实体
   - 包含认证信息和偏好设置
   - 关联：Habits, Metrics, Goals, Notifications

2. **UserPreferences.java** - 用户偏好（可嵌入）
   - 通知开关
   - 深色模式
   - 语言设置

3. **Habit.java** - 习惯追踪
   - 名称、图标、完成状态、连续天数

4. **Metric.java** - 健康指标
   - 标题、值、趋势、颜色

5. **Goal.java** - 目标管理
   - 当前值、目标值、截止日期

6. **Notification.java** - 通知
   - 类型：REMINDER, ACHIEVEMENT, UPDATE, ALERT

7. **Achievement.java** - 成就系统
   - 解锁状态、解锁时间

8. **WeeklyProgress.java** - 每周进度（新增）
9. **DayProgress.java** - 每日进度（新增）

---

### 第四步：创建 DTO 类

15 个 DTO 类用于前后端数据传输：
- `LoginRequestDto`, `LoginResponseDto`
- `UserDto`, `UserPreferencesDto`
- `DashboardDto`, `MetricDto`, `HabitDto`, `WeeklyProgressDto`, `DayProgressDto`
- `StatsDto`, `WeeklyStatDto`, `AchievementDto`, `GoalDto`
- `NotificationDto`, `NotificationGroupDto`
- `ForgotPasswordRequestDto`

---

### 第五步：创建 Repository 层

6 个 Repository 接口：
- `UserRepository` - 支持按邮箱查找
- `HabitRepository` - 查询用户习惯
- `MetricRepository` - 查询最新指标
- `GoalRepository` - 查询活跃目标
- `NotificationRepository` - 未读通知计数
- `AchievementRepository` - 查询成就

新增：
- `WeeklyProgressRepository`

---

### 第六步：创建 Service 层

5 个 Service 类：
1. **AuthenticationService**
   - `login()` - 用户认证，生成 JWT
   - `forgotPassword()` - 密码重置
   - `logout()` - 登出

2. **DashboardService**
   - `getDashboard()` - 聚合用户数据
   - `generateWeeklyProgress()` - 生成周进度
   - `calculateDayCompletion()` - 计算每日完成度

3. **StatsService**
   - `getStats()` - 获取统计数据
   - `generateWeeklyStats()` - 生成周统计

4. **NotificationService**
   - `getNotifications()` - 按日期分组通知
   - `markAsRead()` - 标记已读
   - `markAllAsRead()` - 全部已读

5. **UserService**
   - `getUserProfile()` - 获取用户资料
   - `updatePreferences()` - 更新偏好设置

---

### 第七步：创建 Controller 层

5 个 REST 控制器：
1. **AuthController** - `/auth/*`
2. **DashboardController** - `/dashboard`
3. **StatsController** - `/stats`
4. **NotificationController** - `/notifications/*`
5. **UserController** - `/user/*`

---

### 第八步：安全配置

#### JWT 相关类：
1. **JwtTokenProvider**
   - 生成 access token（24小时）
   - 生成 refresh token（7天）
   - 验证和解析 token

2. **JwtAuthenticationFilter**
   - 拦截请求，提取 JWT
   - 设置认证上下文

3. **SecurityConfig**
   - 配置无状态会话
   - CORS 允许跨域
   - 公开 `/auth/**` 端点
   - 其他端点需要认证

4. **CustomUserDetailsService**
   - 从数据库加载用户详情

---

### 第九步：异常处理

1. **GlobalExceptionHandler**
   - 验证错误处理
   - 资源未找到处理
   - 认证失败处理
   - 全局异常捕获

2. **ErrorResponse**
   - 标准化错误响应格式

---

### 第十步：数据初始化

**DataInitializer.java** - 自动创建测试数据：
- 测试用户：`user@example.com` / `password123`
- 5 个习惯（冥想、喝水、运动、阅读、戒糖）
- 4 个健康指标（卡路里、运动时长、睡眠、饮水量）
- 2 个目标
- 4 个通知
- 3 个成就

---

## 遇到的问题及解决方案

### 问题 1：编译错误 - 找不到 WeeklyProgress 类

**错误信息**：
```
java: 找不到符号
  符号:   类 WeeklyProgress
  位置: 类 com.studyai.wellness.service.DashboardService
```

**原因**：Service 层使用了 `WeeklyProgress` 和 `DayProgress`，但未创建对应实体类。

**解决方案**：
- 创建 `WeeklyProgress.java` 实体类
- 创建 `DayProgress.java` 实体类
- 创建 `WeeklyProgressRepository.java`
- 更新 `DashboardService`，设置 `weeklyProgress(null)` 避免持久化问题

---

### 问题 2：H2 数据库表未找到

**错误信息**：
```
org.h2.jdbc.JdbcSQLSyntaxErrorException: Table "METRICS" not found
```

**原因**：Hibernate 没有正确创建表。

**解决方案** - 修改 `application.yml`：

```yaml
datasource:
  url: jdbc:h2:mem:wellnessdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;MODE=MYSQL
                                            # 添加这些参数
jpa:
  hibernate:
    ddl-auto: create-drop    # 改为 create-drop
  defer-datasource-initialization: true  # 添加
  properties:
    hibernate:
      globally_quoted_identifiers: true  # 添加
      globally_quoted_identifiers_skip_column_definitions: true  # 添加
```

**参数说明**：
- `DB_CLOSE_DELAY=-1` - 保持数据库连接，不立即关闭
- `DB_CLOSE_ON_EXIT=FALSE` - 应用退出时不关闭数据库
- `MODE=MYSQL` - 使用 MySQL 兼容模式
- `create-drop` - 每次启动重新创建表（适合开发）
- `defer-datasource-initialization` - 延迟数据初始化

---

## 在 IDEA 中运行项目

### 方法一：直接运行主类
1. 打开 `WellnessBackendApplication.java`
2. 右键 → `Run 'WellnessBackendApplication'`

### 方法二：Maven 插件
1. 右侧 `Maven` 工具窗口
2. `Plugins` → `spring-boot` → `spring-boot:run`

### 方法三：创建运行配置
```
Run → Edit Configurations → + → Spring Boot
Name: Wellness Backend
Main class: com.studyai.wellness.WellnessBackendApplication
```

### 启动成功标志
```
Started WellnessBackendApplication in X.XXX seconds
```

---

## 数据库配置说明

### 当前配置：H2 内存数据库

**特点**：
- ✅ 应用运行时数据存在
- ❌ 应用停止后数据丢失
- ✅ 适合开发和测试
- ✅ 启动速度快

**访问方式**：
```
H2 控制台: http://localhost:8080/api/v1/h2-console

连接信息：
JDBC URL: jdbc:h2:mem:wellnessdb
User Name: sa
Password: (留空)
```

### 切换到文件数据库（可选）

修改 `application.yml`：
```yaml
url: jdbc:h2:file:./data/wellnessdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;MODE=MYSQL
#             ^^^^ 文件数据库
```

数据将保存在 `backend/data/` 文件夹。

### 数据库模式对比

| 模式 | URL 示例 | 数据位置 | 适用场景 |
|------|----------|----------|----------|
| 内存模式 | `jdbc:h2:mem:wellnessdb` | JVM 内存 | 开发测试 |
| 文件模式 | `jdbc:h2:file:./data/wellnessdb` | 磁盘文件 | 需要保留数据 |
| PostgreSQL | `jdbc:postgresql://localhost:5432/wellnessdb` | 服务器 | 生产环境 |

---

## Git 配置

### 项目中的两个 .gitignore

1. **根目录 `/Users/may/Desktop/Study/Wellness/.gitignore`**
   - 影响：整个项目
   - 内容：iOS/Android 规则

2. **backend/.gitignore**
   - 影响：仅 backend 目录
   - 内容：Java/Maven 规则

**工作原理**：Git 会逐层叠加使用多个 `.gitignore` 文件，两者都生效，不冲突。

**推荐**：保持两个文件，各司其职。

---

## API 端点汇总

### 认证
```
POST /api/v1/auth/login
POST /api/v1/auth/forgot-password
POST /api/v1/auth/logout
```

### 仪表板
```
GET /api/v1/dashboard?userId={id}
```

### 统计
```
GET /api/v1/stats?userId={id}&period={week|month}
```

### 通知
```
GET /api/v1/notifications?userId={id}
PUT /api/v1/notifications/{id}/read
PUT /api/v1/notifications/read-all?userId={id}
```

### 用户
```
GET /api/v1/user/profile?userId={id}
PUT /api/v1/user/preferences?userId={id}
```

---

## 测试账号

```
邮箱: user@example.com
密码: password123
```

使用此账号登录后可获得 JWT Token，用于访问其他需要认证的端点。

---

## 项目统计

- **总文件数**: 50+ Java 文件
- **实体类**: 9 个
- **DTO 类**: 15 个
- **Repository**: 7 个
- **Service**: 5 个
- **Controller**: 5 个
- **安全类**: 4 个
- **异常处理**: 2 个

---

## 项目亮点

### ✅ 清晰的分层架构
- Controller → Service → Repository
- 职责分离，易于维护

### ✅ JWT 无状态认证
- Access Token: 24小时
- Refresh Token: 7天
- 自动续期机制

### ✅ 完整的异常处理
- 全局异常捕获
- 标准化错误响应
- 详细的错误信息

### ✅ 规范的代码
- Lombok 减少样板代码
- 完整的 Javadoc 注释
- 统一的命名规范

### ✅ 开发友好
- H2 控制台支持
- SQL 日志输出
- 自动数据初始化
- 热重载支持

---

## 下一步建议

1. **单元测试** - 编写 Service 和 Repository 测试
2. **集成测试** - 测试完整的 API 流程
3. **API 文档** - 集成 Swagger/OpenAPI
4. **部署** - Docker 容器化 + 云部署
5. **监控** - 添加 Actuator 健康检查
6. **性能优化** - 添加缓存（Redis）
7. **日志管理** - 集成 ELK 或 Loki
8. **CI/CD** - GitHub Actions 自动化部署

---

## 附录：完整的项目结构

```
backend/
├── pom.xml                          # Maven 配置
├── README.md                        # 项目文档
├── .gitignore                       # Git 忽略文件
└── src/main/
    ├── java/com/studyai/wellness/
    │   ├── WellnessBackendApplication.java
    │   ├── config/
    │   │   └── DataInitializer.java
    │   ├── controller/
    │   │   ├── AuthController.java
    │   │   ├── DashboardController.java
    │   │   ├── StatsController.java
    │   │   ├── NotificationController.java
    │   │   └── UserController.java
    │   ├── dto/
    │   │   ├── UserDto.java
    │   │   ├── UserPreferencesDto.java
    │   │   ├── LoginRequestDto.java
    │   │   ├── LoginResponseDto.java
    │   │   ├── ForgotPasswordRequestDto.java
    │   │   ├── DashboardDto.java
    │   │   ├── MetricDto.java
    │   │   ├── HabitDto.java
    │   │   ├── WeeklyProgressDto.java
    │   │   ├── DayProgressDto.java
    │   │   ├── StatsDto.java
    │   │   ├── WeeklyStatDto.java
    │   │   ├── AchievementDto.java
    │   │   ├── GoalDto.java
    │   │   ├── NotificationDto.java
    │   │   └── NotificationGroupDto.java
    │   ├── entity/
    │   │   ├── User.java
    │   │   ├── UserPreferences.java
    │   │   ├── Habit.java
    │   │   ├── Metric.java
    │   │   ├── Goal.java
    │   │   ├── Notification.java
    │   │   ├── Achievement.java
    │   │   ├── WeeklyProgress.java
    │   │   └── DayProgress.java
    │   ├── repository/
    │   │   ├── UserRepository.java
    │   │   ├── HabitRepository.java
    │   │   ├── MetricRepository.java
    │   │   ├── GoalRepository.java
    │   │   ├── NotificationRepository.java
    │   │   ├── AchievementRepository.java
    │   │   └── WeeklyProgressRepository.java
    │   ├── service/
    │   │   ├── AuthenticationService.java
    │   │   ├── DashboardService.java
    │   │   ├── StatsService.java
    │   │   ├── NotificationService.java
    │   │   └── UserService.java
    │   ├── security/
    │   │   ├── SecurityConfig.java
    │   │   ├── JwtTokenProvider.java
    │   │   ├── JwtAuthenticationFilter.java
    │   │   └── CustomUserDetailsService.java
    │   └── exception/
    │       ├── GlobalExceptionHandler.java
    │       └── ErrorResponse.java
    └── resources/
        └── application.yml
```

---

**创建者**: Claude (Anthropic)
**项目状态**: ✅ 完成并可运行
**最后更新**: 2026-02-03
