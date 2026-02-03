# Wellness Backend

Backend service for the Wellness mobile application using Spring Boot and Java.

## Overview

This backend provides RESTful APIs for the Wellness application, supporting features such as:
- User authentication and authorization
- Dashboard with metrics and habits tracking
- Statistics and analytics
- Notifications management
- User profile management

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.1**
- **Spring Security** with JWT authentication
- **Spring Data JPA** for data persistence
- **H2 Database** (in-memory for development)
- **PostgreSQL** (production-ready configuration included)
- **Maven** for dependency management
- **Lombok** to reduce boilerplate code

## Project Structure

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/studyai/wellness/
│   │   │   ├── config/              # Configuration classes
│   │   │   ├── controller/          # REST controllers
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   ├── entity/              # JPA entities
│   │   │   ├── exception/           # Exception handling
│   │   │   ├── repository/          # Data repositories
│   │   │   ├── security/            # Security & JWT configuration
│   │   │   ├── service/             # Business logic
│   │   │   └── WellnessBackendApplication.java
│   │   └── resources/
│   │       └── application.yml      # Application configuration
│   └── test/                        # Test classes
└── pom.xml                          # Maven dependencies
```

中文

```
backend/
├── pom.xml                          # Maven配置文件
├── README.md                        # 项目文档
├── .gitignore                       # Git忽略文件
└── src/main/
    ├── java/com/studyai/wellness/
    │   ├── WellnessBackendApplication.java    # 主启动类
    │   ├── config/
    │   │   └── DataInitializer.java          # 测试数据初始化
    │   ├── controller/                       # REST控制器
    │   │   ├── AuthController.java           # 认证接口
    │   │   ├── DashboardController.java      # 仪表板接口
    │   │   ├── StatsController.java          # 统计接口
    │   │   ├── NotificationController.java   # 通知接口
    │   │   └── UserController.java           # 用户接口
    │   ├── dto/                              # 数据传输对象
    │   │   ├── LoginRequestDto.java
    │   │   ├── LoginResponseDto.java
    │   │   ├── DashboardDto.java
    │   │   ├── StatsDto.java
    │   │   ├── NotificationDto.java
    │   │   ├── NotificationGroupDto.java
    │   │   └── ... (共15个DTO)
    │   ├── entity/                           # JPA实体类
    │   │   ├── User.java
    │   │   ├── UserPreferences.java
    │   │   ├── Habit.java
    │   │   ├── Metric.java
    │   │   ├── Goal.java
    │   │   ├── Notification.java
    │   │   └── Achievement.java
    │   ├── repository/                       # 数据访问层
    │   │   ├── UserRepository.java
    │   │   ├── HabitRepository.java
    │   │   ├── MetricRepository.java
    │   │   ├── GoalRepository.java
    │   │   ├── NotificationRepository.java
    │   │   └── AchievementRepository.java
    │   ├── service/                          # 业务逻辑层
    │   │   ├── AuthenticationService.java
    │   │   ├── DashboardService.java
    │   │   ├── StatsService.java
    │   │   ├── NotificationService.java
    │   │   └── UserService.java
    │   ├── security/                         # 安全配置
    │   │   ├── SecurityConfig.java           # Spring Security配置
    │   │   ├── JwtTokenProvider.java         # JWT工具类
    │   │   ├── JwtAuthenticationFilter.java  # JWT过滤器
    │   │   └── CustomUserDetailsService.java # 用户详情服务
    │   └── exception/                        # 异常处理
    │       ├── GlobalExceptionHandler.java
    │       └── ErrorResponse.java
    └── resources/
        └── application.yml                    # 应用配置
```

## API Endpoints

### Authentication
- `POST /api/v1/auth/login` - User login
- `POST /api/v1/auth/forgot-password` - Request password reset
- `POST /api/v1/auth/logout` - User logout

### Dashboard
- `GET /api/v1/dashboard` - Get dashboard data (metrics, habits, progress)

### Statistics
- `GET /api/v1/stats?period=week` - Get statistics data

### Notifications
- `GET /api/v1/notifications` - Get user notifications
- `PUT /api/v1/notifications/{id}/read` - Mark notification as read
- `PUT /api/v1/notifications/read-all` - Mark all notifications as read

### User
- `GET /api/v1/user/profile` - Get user profile
- `PUT /api/v1/user/preferences` - Update user preferences

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Running the Application

1. Build the project:
```bash
mvn clean install
```

2. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Sample Data

On first run, the application automatically creates:
- A sample user with email: `user@example.com`
- Password: `password123`
- Sample habits, metrics, goals, notifications, and achievements

### H2 Console

For development, you can access the H2 database console at:
```
http://localhost:8080/api/v1/h2-console
```

JDBC URL: `jdbc:h2:mem:wellnessdb`
Username: `sa`
Password: (leave empty)

## Configuration

### JWT Configuration

JWT tokens are configured in `application.yml`:
- Token expiration: 24 hours
- Refresh token expiration: 7 days

### Database Configuration

For production, switch to PostgreSQL by updating `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/wellnessdb
    username: your_username
    password: your_password
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
```

## Authentication

API endpoints (except `/auth/login`) require JWT authentication.

Include the token in the Authorization header:
```
Authorization: Bearer <your-jwt-token>
```

## Architecture Highlights

### Layered Architecture
- **Controller Layer**: Handles HTTP requests/responses
- **Service Layer**: Business logic implementation
- **Repository Layer**: Data access abstraction
- **Entity Layer**: Database models

### Security
- JWT-based stateless authentication
- Password encryption using BCrypt
- CORS configuration for cross-origin requests
- Role-based access control

### Exception Handling
- Global exception handler with standardized error responses
- Validation error handling with field-level details
- Logging for debugging and monitoring

## Development

### Adding a New Endpoint

1. Create/update DTO in `dto/` package
2. Add repository method in `repository/` package
3. Implement service logic in `service/` package
4. Create controller endpoint in `controller/` package

### Code Style

- Use Lombok annotations to reduce boilerplate
- Follow Java naming conventions
- Add Javadoc comments for public APIs
- Use `@Slf4j` for logging

## Testing

Run tests with:
```bash
mvn test
```

## Building for Production

Create executable JAR:
```bash
mvn clean package
```

Run the JAR:
```bash
java -jar target/wellness-backend-1.0.0.jar
```

## License

This project is part of the Wellness application suite.


主要特性
1. 清晰的分层架构
Controller层：处理HTTP请求/响应
Service层：业务逻辑实现
Repository层：数据访问抽象
Entity层：数据库模型
2. JWT认证
无状态认证机制
Token过期时间：24小时
Refresh Token过期时间：7天
3. 完整的API端点
/auth/login - 用户登录
/auth/forgot-password - 忘记密码
/dashboard - 仪表板数据
/stats - 统计数据
/notifications - 通知管理
/user/profile - 用户资料
4. 数据初始化
自动创建测试数据：

用户：user@example.com / password123
习惯、指标、目标、通知和成就示例数据
5. 规范的代码
使用Lombok减少样板代码
完整的Javadoc注释
统一的异常处理
日志记录
运行方式

cd backend
mvn spring-boot:run
访问地址：http://localhost:8080/api/v1

H2控制台：http://localhost:8080/api/v1/h2-console

项目包含47个Java文件，代码结构清晰，注释完整，完全符合企业级Spring Boot项目的最佳实践。

方法三：创建运行配置（可重复使用）
创建配置

点击顶部工具栏的 Add Configuration...
点击 + → 选择 Spring Boot
配置参数

Name: Wellness Backend
Main class: com.studyai.wellness.WellnessBackendApplication
Working directory: $ProjectFileDir$
Use classpath of module: wellness-backend
保存并运行

点击 OK 保存
点击顶部工具栏的运行按钮 ▶️
启动成功标志
看到以下日志表示启动成功：


Started WellnessBackendApplication in X.XXX seconds
访问应用
API 地址: http://localhost:8080/api/v1
H2 控制台: http://localhost:8080/api/v1/h2-console
测试登录
使用 curl 或 Postman 测试：

```
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123"
  }'
```


现在重新运行项目。配置已更新：

主要修改：

✅ 添加了 DB_CLOSE_DELAY=-1 保持数据库连接
✅ 添加了 MODE=MYSQL 使用 MySQL 兼容模式
✅ 改为 create-drop 确保每次启动时重新创建表
✅ 添加了 defer-datasource-initialization: true 延迟数据初始化
✅ 添加了全局标识符引用配置
在 IDEA 中重新运行：

停止当前运行（如果还在运行）
点击刷新按钮 或按 Ctrl+F5 / Cmd+F5
重新运行项目
如果还有问题，可以尝试清理并重新构建：



根据当前配置，你使用的是 H2 内存数据库，它的特点是：

当前数据库位置

jdbc:h2:mem:wellnessdb
类型：内存数据库（In-Memory Database）
位置：存储在 JVM 内存中，不占用磁盘空间
生命周期：
✅ 应用运行时：数据存在
❌ 应用停止后：所有数据都会丢失
如何访问数据库
方法一：H2 控制台（最简单）
启动应用后，在浏览器打开：


http://localhost:8080/api/v1/h2-console
连接信息：

JDBC URL: jdbc:h2:mem:wellnessdb
User Name: sa
Password: (留空)
点击 Connect 即可查看表和数据

方法二：查看日志
应用启动时，控制台会显示所有 SQL 语句（因为配置了 show-sql: true），可以看到：

表创建语句
数据插入语句
数据库配置说明

spring:
  datasource:
    url: jdbc:h2:mem:wellnessdb;DB_CLOSE_DELAY=-1;...
    #         ^^^
    #      内存数据库标识
切换到文件数据库（可选）
如果希望数据持久化到磁盘，可以修改配置：

Read application.yml (lines 1-15)
如果想让数据持久化保存到文件，只需把第 5 行改为：


url: jdbc:h2:file:./data/wellnessdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;MODE=MYSQL
#             ^^^^
#          文件数据库
这样数据会保存在项目根目录的 data/ 文件夹下。

不同数据库模式对比
模式	URL 示例	数据位置	适用场景
内存模式	jdbc:h2:mem:wellnessdb	JVM 内存	开发测试、快速重启
文件模式	jdbc:h2:file:./data/wellnessdb	项目文件夹	需要保留测试数据
PostgreSQL	jdbc:postgresql://localhost:5432/wellnessdb	远程/本地服务器	生产环境
当前使用的是内存模式，非常适合开发和测试，因为每次重启都会清空数据，确保测试环境干净。