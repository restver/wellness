import SwiftUI

// MARK: - App Navigation
@available(iOS 18.0, *)
struct AppNavigation: View {
    @State private var isLoggedIn: Bool = false
    @State private var selectedTab: AppTab = .dashboard
    @State private var notificationsViewModel: NotificationsViewModel?

    var body: some View {
        Group {
            if isLoggedIn {
                MainTabView(selectedTab: $selectedTab)
            } else {
                LoginView(onLoginSuccess: {
                    withAnimation {
                        isLoggedIn = true
                    }
                })
            }
        }
        .task {
            // Check if user is logged in
            if KeychainService.shared.getToken() != nil {
                isLoggedIn = true
            }
            // Initialize view model on main actor
            await MainActor.run {
                notificationsViewModel = NotificationsViewModel()
                notificationsViewModel?.loadNotifications()
            }
        }
    }
}

// MARK: - Main Tab View (iOS 18+ Tab with TabSection)
@available(iOS 18.0, *)
struct MainTabView: View {
    @Binding var selectedTab: AppTab

    var body: some View {
        TabView(selection: $selectedTab) {
            DashboardView()
                .tabItem {
                    Label("Dashboard", systemImage: "house.fill")
                }
                .tag(AppTab.dashboard)

            CalendarView()
                .tabItem {
                    Label("Calendar", systemImage: "calendar")
                }
                .tag(AppTab.calendar)

            StatsView()
                .tabItem {
                    Label("Stats", systemImage: "chart.bar.fill")
                }
                .tag(AppTab.stats)

            NotificationsView()
                .tabItem {
                    Label("Notifications", systemImage: "bell")
                }
                .tag(AppTab.notifications)

            ProfileView()
                .tabItem {
                    Label("Profile", systemImage: "person.fill")
                }
                .tag(AppTab.profile)

            SettingsView()
                .tabItem {
                    Label("Settings", systemImage: "gearshape.fill")
                }
                .tag(AppTab.settings)
        }
        .tint(AppColors.primaryGreen)
    }
}

// MARK: - Navigation Stack Helper
struct NavigationDestinationHelper: ViewModifier {
    let destination: Any?
    @Binding var isPresented: Bool

    func body(content: Content) -> some View {
        content
    }
}

#Preview {
    if #available(iOS 18.0, *) {
        AppNavigation()
    } else {
        Text("iOS 18+ required")
    }
}
