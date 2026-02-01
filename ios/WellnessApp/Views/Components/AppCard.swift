import SwiftUI

// MARK: - Metric Card
struct MetricCard: View {
    let title: String
    let value: String
    let subtitle: String?
    let trend: String?
    let color: String?

    var body: some View {
        VStack(alignment: .leading, spacing: 12) {
            HStack {
                Text(title)
                    .font(.appSubheadline)
                    .foregroundColor(AppTheme.shared.colorScheme.textSecondary)
                Spacer()
                if let trend = trend {
                    HStack(spacing: 4) {
                        Image(systemName: trend.hasPrefix("+") ? "arrow.up.right" : "arrow.down.right")
                            .font(.appCaption1)
                        Text(trend)
                            .font(.appCaption1)
                    }
                    .foregroundColor(trend.hasPrefix("+") ? AppColors.success : AppColors.error)
                }
            }

            Text(value)
                .font(.appTitle1)
                .foregroundColor(AppTheme.shared.colorScheme.textPrimary)

            if let subtitle = subtitle {
                Text(subtitle)
                    .font(.appFootnote)
                    .foregroundColor(AppTheme.shared.colorScheme.textTertiary)
            }
        }
        .padding(20)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(AppTheme.shared.colorScheme.cardBackground)
        .cornerRadius(16)
        .shadow(color: Color.black.opacity(0.05), radius: 10, x: 0, y: 2)
    }
}

// MARK: - Habit Card
struct HabitCard: View {
    let habit: Habit
    let onTap: () -> Void

    var body: some View {
        HStack(spacing: 12) {
            Text(habit.icon)
                .font(.system(size: 32))

            VStack(alignment: .leading, spacing: 4) {
                Text(habit.name)
                    .font(.appBodyMedium)
                    .foregroundColor(AppTheme.shared.colorScheme.textPrimary)

                Text("\(habit.streak) day streak")
                    .font(.appFootnote)
                    .foregroundColor(AppTheme.shared.colorScheme.textTertiary)
            }

            Spacer()

            Button(action: onTap) {
                Image(systemName: habit.completed ? "checkmark.circle.fill" : "circle")
                    .font(.system(size: 24))
                    .foregroundColor(habit.completed ? AppColors.success : AppTheme.shared.colorScheme.border)
            }
        }
        .padding(16)
        .background(AppTheme.shared.colorScheme.cardBackground)
        .cornerRadius(16)
    }
}

// MARK: - Settings Row
struct SettingsRow: View {
    let icon: String
    let title: String
    let value: String?
    let action: () -> Void
    var showChevron: Bool = true

    var body: some View {
        Button(action: action) {
            HStack(spacing: 16) {
                Image(systemName: icon)
                    .font(.appBody)
                    .foregroundColor(AppColors.primaryGreen)
                    .frame(width: 32)

                Text(title)
                    .font(.appBody)
                    .foregroundColor(AppTheme.shared.colorScheme.textPrimary)

                Spacer()

                if let value = value {
                    Text(value)
                        .font(.appSubheadline)
                        .foregroundColor(AppTheme.shared.colorScheme.textSecondary)
                }

                if showChevron {
                    Image(systemName: "chevron.right")
                        .font(.appCallout)
                        .foregroundColor(AppTheme.shared.colorScheme.textTertiary)
                }
            }
            .padding(.horizontal, 24)
            .padding(.vertical, 16)
            .background(AppTheme.shared.colorScheme.cardBackground)
        }
    }
}

// MARK: - Notification Card
struct NotificationCard: View {
    let notification: AppNotification
    let onTap: () -> Void

    var body: some View {
        HStack(spacing: 12) {
            ZStack {
                Circle()
                    .fill(iconColor.opacity(0.2))
                    .frame(width: 40, height: 40)

                Image(systemName: iconName)
                    .font(.appBody)
                    .foregroundColor(iconColor)
            }

            VStack(alignment: .leading, spacing: 4) {
                Text(notification.title)
                    .font(.appBodyMedium)
                    .foregroundColor(AppTheme.shared.colorScheme.textPrimary)

                Text(notification.message)
                    .font(.appSubheadline)
                    .foregroundColor(AppTheme.shared.colorScheme.textSecondary)
                    .lineLimit(2)

                Text(notification.timeAgo)
                    .font(.appFootnote)
                    .foregroundColor(AppTheme.shared.colorScheme.textTertiary)
            }

            Spacer()

            if !notification.isRead {
                Circle()
                    .fill(AppColors.primaryGreen)
                    .frame(width: 8, height: 8)
            }
        }
        .padding(16)
        .background(AppTheme.shared.colorScheme.cardBackground)
        .cornerRadius(12)
        .onTapGesture(perform: onTap)
    }

    private var iconName: String {
        switch notification.type {
        case .achievement: return "trophy.fill"
        case .reminder: return "bell.fill"
        case .progress: return "chart.line.uptrend.xyaxis"
        case .system: return "gear"
        }
    }

    private var iconColor: Color {
        switch notification.type {
        case .achievement: return AppColors.warning
        case .reminder: return AppColors.primaryGreen
        case .progress: return Color.blue
        case .system: return AppTheme.shared.colorScheme.textSecondary
        }
    }
}

#Preview {
    VStack(spacing: 16) {
        MetricCard(
            title: "Calories Burned",
            value: "1,245",
            subtitle: "of 2,000 goal",
            trend: "+15%",
            color: "#3D8A5A"
        )

        HabitCard(habit: .mocks[0]) {
            print("Tapped")
        }

        SettingsRow(
            icon: "person.fill",
            title: "Edit Profile",
            value: nil,
            action: {}
        )

        NotificationCard(notification: .mocks[0]) {
            print("Tapped")
        }
    }
    .padding()
}
