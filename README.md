# WellnessApp - High Performance Android Management Platform

A modern, high-performance Android wellness management platform built with Kotlin, Coroutines, and Jetpack Compose.

## Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Concurrency**: Kotlin Coroutines & Flow
- **Dependency Injection**: Hilt
- **Networking**: Retrofit + OkHttp
- **Async Data**: Kotlin Serialization
- **Local Storage**: DataStore Preferences
- **Navigation**: Compose Navigation
- **Architecture**: MVVM with Clean Architecture

## Project Structure

```
app/src/main/java/com/studyai/wellness/
├── data/
│   ├── api/              # API services and endpoints
│   ├── model/            # DTOs and data models
│   └── repository/       # Repository pattern implementation
├── di/                   # Dependency injection modules
├── navigation/           # Navigation graph and routes
├── ui/
│   ├── components/       # Reusable UI components
│   ├── screens/          # Screen composables
│   └── theme/            # Theme, colors, typography
├── utils/                # Utility functions
├── viewmodels/           # ViewModels for state management
├── work/                 # Background workers
├── MainActivity.kt
└── WellnessApplication.kt
```

## Key Features

### High Performance Optimizations

1. **Coroutines & Flow**: Async operations using structured concurrency
2. **Lazy Loading**: Efficient list rendering with LazyColumn/LazyGrid
3. **State Management**: Efficient state hoisting and recomposition optimization
4. **Memory Management**: Proper disposable effects and lifecycle awareness
5. **Image Loading**: Coil for efficient image caching and loading
6. **Background Sync**: WorkManager for periodic data synchronization

### Architecture Patterns

- **Clean Architecture**: Separation of concerns with data, domain, and UI layers
- **Repository Pattern**: Single source of truth for data
- **MVVM**: Model-View-ViewModel for UI state management
- **Dependency Injection**: Hilt for scalable and testable code

### UI Components

- **Material 3**: Modern Material Design 3 components
- **Custom Components**: Reusable, theme-aware components
- **Responsive Design**: Adaptive layouts for different screen sizes

## Getting Started

### Prerequisites

- Android Studio Hedgehog | 2023.1.1 or later
- JDK 17
- Android SDK 34
- Minimum SDK 26

### Build the Project

```bash
# Clone the repository
git clone <repository-url>

# Open in Android Studio
# Sync Gradle files
# Build and run
```

### Dependencies

All dependencies are managed through Gradle:
- Compose BOM for UI
- Hilt for DI
- Retrofit for networking
- Coroutines for async operations

## Performance Best Practices

### 1. Coroutines Usage
```kotlin
viewModelScope.launch {
    repository.getData().collect { result ->
        result.fold(
            onSuccess = { data -> _uiState.value = UiState.Success(data) },
            onFailure = { error -> _uiState.value = UiState.Error(error) }
        )
    }
}
```

### 2. Compose Optimization
```kotlin
// Use remember for expensive computations
val expensiveValue = remember(key) { computeExpensiveValue(key) }

// Use stable types for params
@Composable
fun StableComponent(data: StableData) { ... }
```

### 3. Lazy Loading
```kotlin
LazyColumn {
    items(items, key = { it.id }) { item ->
        ItemComponent(item)
    }
}
```

### 4. Flow Operators
```kotlin
repository.getData()
    .cachedIn(viewModelScope)
    .collect { ... }
```

## API Integration

The app uses Retrofit for API communication:

```kotlin
@POST("auth/login")
suspend fun login(@Body request: LoginRequestDto): LoginResponseDto
```

## State Management

ViewModels use Kotlin Flow for reactive state management:

```kotlin
private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
val uiState: StateFlow<UiState> = _uiState.asStateFlow()
```

## Testing

```bash
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

## Build Variants

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease
```

## Performance Monitoring

Use the built-in performance utilities:

```kotlin
PerformanceMonitor.measureTime("operation_name") {
    // Code to measure
}
```

## Contributing

1. Follow Kotlin coding conventions
2. Write unit tests for new features
3. Use coroutines for async operations
4. Optimize Compose recomposition
5. Document public APIs

## License

Copyright © 2024 StudyAI. All rights reserved.


## Dark Mode

深色主题支持已经实现完成！以下是做的修改：

已完成的修改
1. DataStore 扩展 (DataStoreManager.kt)
添加了 DARK_MODE 布尔值键
添加了 darkMode: Flow<Boolean> 用于读取主题设置
添加了 saveDarkMode(enabled: Boolean) 用于保存主题设置
2. SettingsViewModel 更新 (SettingsViewModel.kt)
注入 DataStoreManager
在 init 块中从 DataStore 读取深色模式设置
toggleDarkMode 现在将设置保存到 DataStore
3. MainActivity 修改 (MainActivity.kt)
注入 DataStoreManager
WellnessApp 现在接收 dataStoreManager 参数
使用 collectAsStateLifecycleAware 监听深色模式状态
将 darkMode 状态传递给 WellnessAppTheme
4. UI 组件更新
更新了以下组件使用 MaterialTheme.colorScheme 而非硬编码颜色：

SettingsScreen.kt - 设置页面
DashboardScreen.kt - 仪表板页面
CalendarScreen.kt - 日历页面
BottomTabBar.kt - 底部导航栏
颜色映射
Color.White → MaterialTheme.colorScheme.surface
Background → MaterialTheme.colorScheme.background
TextPrimary → MaterialTheme.colorScheme.onBackground
TextSecondary → MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
PrimaryGreen → MaterialTheme.colorScheme.primary
使用方式
用户现在可以在设置页面切换深色模式：

进入 Settings 页面
在 Appearance 部分找到 Dark Mode 开关
切换开关，应用会立即更新主题
主题设置会持久化保存，应用重启后会保持用户的选择。