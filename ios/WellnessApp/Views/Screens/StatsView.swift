import SwiftUI

// MARK: - Stats View
struct StatsView: View {
    @State private var viewModel: StatsViewModel?

    var body: some View {
        ZStack {
            if let viewModel = viewModel {
                if viewModel.isLoading && viewModel.stats.isEmpty {
                    FullScreenLoading()
                } else if let errorMessage = viewModel.errorMessage, viewModel.stats.isEmpty {
                    ErrorState(message: errorMessage) {
                        viewModel.loadStats()
                    }
                } else if !viewModel.stats.isEmpty {
                    ScrollView {
                        VStack(spacing: 20) {
                            // Header
                            HStack {
                                Text("Statistics")
                                    .font(.appTitle1)

                                Spacer()
                            }
                            .padding(.horizontal, 24)

                            // Stats Grid
                            ForEach(viewModel.stats) { stat in
                                StatCard(stat: stat)
                                    .padding(.horizontal, 24)
                            }

                            Spacer().frame(height: 100)
                        }
                        .padding(.top, 20)
                    }
                }
            }
        }
        .task {
            if viewModel == nil {
                viewModel = StatsViewModel()
            }
            viewModel?.loadStats()
        }
    }
}

// MARK: - Stat Card
struct StatCard: View {
    let stat: Stats

    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            HStack {
                Text(stat.title)
                    .font(.appBodyMedium)
                    .foregroundColor(AppTheme.shared.colorScheme.textSecondary)

                Spacer()

                HStack(spacing: 4) {
                    Image(systemName: stat.isPositive ? "arrow.up.right" : "arrow.down.right")
                        .font(.appCaption1)

                    Text(stat.change)
                        .font(.appCaption1)
                }
                .foregroundColor(stat.isPositive ? AppColors.success : AppColors.error)
            }

            Text(stat.value)
                .font(.system(size: 48, weight: .bold))
                .foregroundColor(AppTheme.shared.colorScheme.textPrimary)

            // Mini chart
            SimpleLineChart(dataPoints: stat.chartData)
        }
        .padding(20)
        .background(AppTheme.shared.colorScheme.cardBackground)
        .cornerRadius(16)
    }
}

// MARK: - Simple Line Chart
struct SimpleLineChart: View {
    let dataPoints: [ChartDataPoint]

    var body: some View {
        GeometryReader { geometry in
            ZStack {
                // Grid lines
                VStack {
                    ForEach(0..<4) { _ in
                        Divider()
                            .background(AppTheme.shared.colorScheme.divider)
                        Spacer()
                    }
                }

                // Line
                Path { path in
                    let width = geometry.size.width
                    let height = geometry.size.height
                    let step = width / CGFloat(max(dataPoints.count - 1, 1))

                    let maxValue = dataPoints.map(\.value).max() ?? 1
                    let minValue = dataPoints.map(\.value).min() ?? 0
                    let range = maxValue - minValue

                    for (index, point) in dataPoints.enumerated() {
                        let x = CGFloat(index) * step
                        let normalizedValue = range > 0 ? (point.value - minValue) / range : 0.5
                        let y = height - (CGFloat(normalizedValue) * height)

                        if index == 0 {
                            path.move(to: CGPoint(x: x, y: y))
                        } else {
                            path.addLine(to: CGPoint(x: x, y: y))
                        }
                    }
                }
                .stroke(AppColors.primaryGreen, style: StrokeStyle(lineWidth: 2, lineCap: .round, lineJoin: .round))
            }
        }
        .frame(height: 80)
    }
}

#Preview {
    NavigationStack {
        StatsView()
    }
}
