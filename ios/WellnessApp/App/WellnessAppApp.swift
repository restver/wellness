import SwiftUI

@main
struct WellnessAppApp: App {
    @State private var appTheme = AppTheme.shared

    var body: some Scene {
        WindowGroup {
            if #available(iOS 18.0, *) {
                AppNavigation()
                    .appTheme()
            } else {
                // Fallback for iOS 17
                AppNavigationLegacy()
                    .appTheme()
            }
        }
    }
}

// MARK: - Legacy Navigation for iOS 17
struct AppNavigationLegacy: View {
    @State private var isLoggedIn: Bool = false
    @State private var selectedTab: AppTab = .dashboard

    var body: some View {
        Group {
            if isLoggedIn {
                TabView(selection: $selectedTab) {
                    NavigationStack {
                        DashboardView()
                    }
                    .tabItem {
                        Label("Dashboard", systemImage: "house.fill")
                    }
                    .tag(AppTab.dashboard)

                    NavigationStack {
                        CalendarView()
                    }
                    .tabItem {
                        Label("Calendar", systemImage: "calendar")
                    }
                    .tag(AppTab.calendar)

                    NavigationStack {
                        StatsView()
                    }
                    .tabItem {
                        Label("Stats", systemImage: "chart.bar.fill")
                    }
                    .tag(AppTab.stats)

                    NavigationStack {
                        NotificationsView()
                    }
                    .tabItem {
                        Label("Notifications", systemImage: "bell")
                    }
                    .tag(AppTab.notifications)

                    NavigationStack {
                        ProfileView()
                    }
                    .tabItem {
                        Label("Profile", systemImage: "person.fill")
                    }
                    .tag(AppTab.profile)

                    NavigationStack {
                        SettingsView()
                    }
                    .tabItem {
                        Label("Settings", systemImage: "gearshape.fill")
                    }
                    .tag(AppTab.settings)
                }
                .tint(AppColors.primaryGreen)
            } else {
                NavigationStack {
                    LoginView(onLoginSuccess: {
                        withAnimation {
                            isLoggedIn = true
                        }
                    })
                }
            }
        }
        .task {
            if KeychainService.shared.getToken() != nil {
                isLoggedIn = true
            }
        }
    }
}
