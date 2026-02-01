import SwiftUI

// MARK: - Dashboard View
struct DashboardView: View {
    @State private var viewModel: DashboardViewModel?
    @State private var selectedTab: AppTab = .dashboard

    var body: some View {
        ZStack {
            if let viewModel = viewModel {
                if viewModel.isLoading && viewModel.dashboard == nil {
                    FullScreenLoading()
                } else if let errorMessage = viewModel.errorMessage, viewModel.dashboard == nil {
                    ErrorState(message: errorMessage) {
                        viewModel.loadDashboard()
                    }
                } else if let dashboard = viewModel.dashboard {
                    VStack(spacing: 0) {
                        ScrollView {
                            VStack(spacing: 20) {
                                // Header
                                headerSection(user: dashboard.user)

                                // Metrics
                                VStack(alignment: .leading, spacing: 12) {
                                    Text("Today's Progress")
                                        .font(.appHeadline)
                                        .foregroundColor(AppTheme.shared.colorScheme.textPrimary)
                                        .padding(.horizontal, 24)

                                    ForEach(dashboard.metrics.prefix(3)) { metric in
                                        MetricCard(
                                            title: metric.title,
                                            value: metric.value,
                                            subtitle: metric.subtitle,
                                            trend: metric.trend,
                                            color: metric.color
                                        )
                                        .padding(.horizontal, 24)
                                    }
                                }

                                // Habits
                                VStack(alignment: .leading, spacing: 12) {
                                    Text("Daily Habits")
                                        .font(.appHeadline)
                                        .foregroundColor(AppTheme.shared.colorScheme.textPrimary)
                                        .padding(.horizontal, 24)

                                    ForEach(dashboard.habits) { habit in
                                        HabitCard(habit: habit) {
                                            viewModel.toggleHabit(habit)
                                        }
                                        .padding(.horizontal, 24)
                                    }
                                }

                                // Weekly Progress
                                VStack(alignment: .leading, spacing: 12) {
                                    Text("This Week")
                                        .font(.appHeadline)
                                        .foregroundColor(AppTheme.shared.colorScheme.textPrimary)
                                        .padding(.horizontal, 24)

                                    WeeklyProgressChart(days: dashboard.weeklyProgress.days)
                                        .padding(.horizontal, 24)
                                }

                                Spacer().frame(height: 100)
                            }
                            .padding(.top, 20)
                        }

                        AppTabBar(selectedTab: $selectedTab)
                    }
                }
            }
        }
        .task {
            if viewModel == nil {
                viewModel = DashboardViewModel()
            }
            viewModel?.loadDashboard()
        }
    }

    private func headerSection(user: User) -> some View {
        HStack {
            VStack(alignment: .leading, spacing: 4) {
                Text("Hello, \(user.name)")
                    .font(.appTitle2)

                Text("Let's check your progress")
                    .font(.appSubheadline)
                    .foregroundColor(AppTheme.shared.colorScheme.textSecondary)
            }

            Spacer()

            Circle()
                .fill(AppColors.primaryGreen)
                .frame(width: 48, height: 48)
                .overlay {
                    Text(String(user.name.prefix(1)))
                        .font(.appTitle3)
                        .foregroundColor(.white)
                }
        }
        .padding(.horizontal, 24)
    }
}

// MARK: - Weekly Progress Chart
struct WeeklyProgressChart: View {
    let days: [DayProgress]

    var body: some View {
        HStack(spacing: 12) {
            ForEach(days) { day in
                VStack(spacing: 8) {
                    VStack(spacing: 0) {
                        Spacer()

                        Rectangle()
                            .fill(day.completed ? AppColors.primaryGreen : AppTheme.shared.colorScheme.border)
                            .frame(width: 32, height: CGFloat(day.value) * 60)
                            .cornerRadius(8)
                    }
                    .frame(height: 60)

                    Text(day.day.prefix(1))
                        .font(.appCaption1)
                        .foregroundColor(AppTheme.shared.colorScheme.textTertiary)
                }
            }
        }
        .padding(16)
        .background(AppTheme.shared.colorScheme.cardBackground)
        .cornerRadius(16)
        .padding(.horizontal, 24)
    }
}

#Preview {
    NavigationStack {
        DashboardView()
    }
}
