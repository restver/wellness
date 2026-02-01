import Foundation

// MARK: - Stats Models
struct Stats: Codable, Identifiable {
    let id: String
    let title: String
    let value: String
    let change: String
    let isPositive: Bool
    let chartData: [ChartDataPoint]

    static let mocks: [Stats] = [
        Stats(
            id: "1",
            title: "Weekly Activity",
            value: "5.2 hrs",
            change: "+12%",
            isPositive: true,
            chartData: ChartDataPoint.mockWeeklyActivity
        ),
        Stats(
            id: "2",
            title: "Calories Burned",
            value: "12,450",
            change: "+8%",
            isPositive: true,
            chartData: ChartDataPoint.mockCalories
        ),
        Stats(
            id: "3",
            title: "Sleep Average",
            value: "7.2 hrs",
            change: "-5%",
            isPositive: false,
            chartData: ChartDataPoint.mockSleep
        ),
        Stats(
            id: "4",
            title: "Heart Rate",
            value: "72 bpm",
            change: "+2%",
            isPositive: true,
            chartData: ChartDataPoint.mockHeartRate
        )
    ]
}

struct ChartDataPoint: Codable, Identifiable {
    let id: String
    let label: String
    let value: Double

    init(id: String = UUID().uuidString, label: String, value: Double) {
        self.id = id
        self.label = label
        self.value = value
    }

    static let mockWeeklyActivity: [ChartDataPoint] = [
        ChartDataPoint(label: "Mon", value: 0.6),
        ChartDataPoint(label: "Tue", value: 0.8),
        ChartDataPoint(label: "Wed", value: 0.5),
        ChartDataPoint(label: "Thu", value: 0.9),
        ChartDataPoint(label: "Fri", value: 0.7),
        ChartDataPoint(label: "Sat", value: 1.0),
        ChartDataPoint(label: "Sun", value: 0.4)
    ]

    static let mockCalories: [ChartDataPoint] = [
        ChartDataPoint(label: "Mon", value: 1800),
        ChartDataPoint(label: "Tue", value: 2100),
        ChartDataPoint(label: "Wed", value: 1600),
        ChartDataPoint(label: "Thu", value: 2400),
        ChartDataPoint(label: "Fri", value: 1900),
        ChartDataPoint(label: "Sat", value: 2200),
        ChartDataPoint(label: "Sun", value: 1450)
    ]

    static let mockSleep: [ChartDataPoint] = [
        ChartDataPoint(label: "Mon", value: 7.5),
        ChartDataPoint(label: "Tue", value: 6.8),
        ChartDataPoint(label: "Wed", value: 8.0),
        ChartDataPoint(label: "Thu", value: 7.2),
        ChartDataPoint(label: "Fri", value: 6.5),
        ChartDataPoint(label: "Sat", value: 8.5),
        ChartDataPoint(label: "Sun", value: 7.0)
    ]

    static let mockHeartRate: [ChartDataPoint] = [
        ChartDataPoint(label: "Mon", value: 70),
        ChartDataPoint(label: "Tue", value: 72),
        ChartDataPoint(label: "Wed", value: 68),
        ChartDataPoint(label: "Thu", value: 74),
        ChartDataPoint(label: "Fri", value: 71),
        ChartDataPoint(label: "Sat", value: 69),
        ChartDataPoint(label: "Sun", value: 70)
    ]
}
