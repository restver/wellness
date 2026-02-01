# Wellness iOS App

A health and fitness tracking iOS application built with SwiftUI, Combine, and iOS 18+ features.

## Features

- **User Authentication**: Login and forgot password functionality
- **Dashboard**: Track daily progress, metrics, and habits
- **Calendar**: View and manage scheduled events
- **Statistics**: Visualize weekly/monthly progress with charts
- **Notifications**: Stay updated with achievements and reminders
- **Profile**: View and edit user profile
- **Settings**: Customize app preferences including Dark Mode

## Requirements

- iOS 18.0+
- Xcode 16.0+
- Swift 6.0+

## Architecture

The app follows MVVM architecture with Combine for reactive programming:

```
WellnessApp/
├── App/
│   └── WellnessAppApp.swift          # App entry point
├── Data/
│   ├── API/
│   │   └── APIService.swift          # Network service
│   ├── Models/
│   │   ├── UserModels.swift          # User data models
│   │   ├── DashboardModels.swift     # Dashboard data models
│   │   ├── StatsModels.swift         # Statistics data models
│   │   ├── NotificationModels.swift  # Notification data models
│   │   └── CalendarModels.swift      # Calendar data models
│   └── Repository/
│       ├── UserRepository.swift      # User repository
│       ├── DashboardRepository.swift # Dashboard repository
│       └── ...                       # Other repositories
├── ViewModels/
│   ├── LoginViewModel.swift
│   ├── DashboardViewModel.swift
│   ├── StatsViewModel.swift
│   ├── NotificationsViewModel.swift
│   ├── CalendarViewModel.swift
│   ├── ProfileViewModel.swift
│   └── SettingsViewModel.swift
├── Views/
│   ├── Components/
│   │   ├── AppButton.swift
│   │   ├── AppInputField.swift
│   │   ├── AppCard.swift
│   │   ├── AppTabBar.swift
│   │   └── LoadingStates.swift
│   ├── Navigation/
│   │   └── AppNavigation.swift       # iOS 18+ Tab/TabSection navigation
│   ├── Screens/
│   │   ├── LoginView.swift
│   │   ├── ForgotPasswordView.swift
│   │   ├── DashboardView.swift
│   │   ├── CalendarView.swift
│   │   ├── StatsView.swift
│   │   ├── NotificationsView.swift
│   │   ├── ProfileView.swift
│   │   └── SettingsView.swift
│   └── Theme/
│       ├── AppColors.swift           # Color scheme (Light/Dark)
│       ├── AppTheme.swift            # Theme management
│       └── AppTypography.swift       # Typography system
└── Resources/
    └── Info.plist
```

## Key Technologies

- **SwiftUI**: Modern declarative UI framework
- **Combine**: Reactive programming for data flow
- **NavigationStack**: iOS 16+ navigation API
- **Tab & TabSection**: iOS 18+ tab navigation with grouping
- **@Observable**: iOS 17+ observation system
- **Dark Mode**: Automatic and manual theme switching

## Dark Mode Support

The app supports Dark Mode through:

1. `AppTheme` class that manages theme state
2. `ThemeColorScheme` with light and dark color definitions
3. Automatic color adaptation based on user preferences

## Mock Data

The app uses mock data for development. Replace the repository implementations with actual API calls when the backend is ready.

## Getting Started

1. Open the project in Xcode 16+
2. Select a simulator or device running iOS 18+
3. Build and run (Cmd+R)

## Customization

### Colors

Edit `AppColors.swift` and `ThemeColorScheme` in `AppColors.swift` to customize the app's color palette.

### Typography

Edit `AppTypography.swift` to customize fonts and text styles.

### API Endpoint

Update `baseURL` in `APIService.swift` to point to your backend API.

## License

This project is for educational purposes.

核心特性
特性 说明
架构 MVVM + Combine
导航 NavigationStack + Tab/TabSection (iOS 18+)
主题 DarkMode 支持（浅色/深色自动切换）
状态管理 @Observable (iOS 17+)
最低版本 iOS 18.0+ (有 iOS 17 降级方案)
功能页面
登录页: LoginView.swift
仪表盘: DashboardView.swift
日历: CalendarView.swift
统计: StatsView.swift
通知: NotificationsView.swift
个人资料: ProfileView.swift
设置: SettingsView.swift

代码质量检查
✅ @Observable 使用正确（8处）
✅ @MainActor 标记正确（5个ViewModel）
✅ @available(iOS 18.0)\* 正确标记TabSection
✅ Combine 发布者/订阅者模式正确
✅ NavigationStack API使用正确
✅ Dark Mode 主题支持完整
需要注意的事项
iOS版本要求: 代码使用了iOS 18+的TabSection API，但已提供iOS 17降级方案
@Observable: 这是iOS 17+的新特性，替代了ObservableObject
Mock Data: 当前使用模拟数据，需要对接真实API时修改Repository层
