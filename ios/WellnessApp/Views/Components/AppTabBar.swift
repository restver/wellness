import SwiftUI

// MARK: - Tab Item
enum AppTab: String, CaseIterable {
    case dashboard = "Dashboard"
    case calendar = "Calendar"
    case stats = "Stats"
    case notifications = "Notifications"
    case profile = "Profile"
    case settings = "Settings"

    var icon: String {
        switch self {
        case .dashboard: return "house.fill"
        case .calendar: return "calendar"
        case .stats: return "chart.bar.fill"
        case .notifications: return "bell"
        case .profile: return "person.fill"
        case .settings: return "gearshape.fill"
        }
    }
}

// MARK: - Tab Bar Component
struct AppTabBar: View {
    @Binding var selectedTab: AppTab
    var unreadNotificationCount: Int = 0

    var body: some View {
        HStack(spacing: 0) {
            ForEach([AppTab.dashboard, .calendar, .stats, .notifications, .profile, .settings], id: \.self) { tab in
                TabButton(
                    tab: tab,
                    isSelected: selectedTab == tab,
                    unreadCount: tab == .notifications ? unreadNotificationCount : 0
                ) {
                    selectedTab = tab
                }
                if tab != .settings {
                    Spacer(minLength: 0)
                }
            }
        }
        .padding(.horizontal, 24)
        .padding(.vertical, 12)
        .background(AppTheme.shared.colorScheme.tabBarBackground)
    }
}

// MARK: - Tab Button
struct TabButton: View {
    let tab: AppTab
    let isSelected: Bool
    let unreadCount: Int
    let action: () -> Void

    var body: some View {
        Button(action: action) {
            VStack(spacing: 4) {
                ZStack {
                    Image(systemName: tab.icon)
                        .font(.system(size: 22))
                        .foregroundColor(isSelected ? AppColors.primaryGreen : AppTheme.shared.colorScheme.textTertiary)

                    if unreadCount > 0 {
                        VStack {
                            HStack {
                                Spacer()
                                Text("\(unreadCount)")
                                    .font(.appCaption2)
                                    .foregroundColor(.white)
                                    .padding(4)
                                    .background(AppColors.error)
                                    .clipShape(Circle())
                            }
                            Spacer()
                        }
                    }
                }

                Text(tab.rawValue)
                    .font(.appCaption2)
                    .foregroundColor(isSelected ? AppColors.primaryGreen : AppTheme.shared.colorScheme.textTertiary)
            }
        }
    }
}

#Preview {
    VStack {
        Spacer()
        AppTabBar(selectedTab: .constant(.dashboard), unreadNotificationCount: 3)
    }
    .background(AppTheme.shared.colorScheme.background)
}
