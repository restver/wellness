import Foundation

// MARK: - Dashboard Models
struct Dashboard: Codable, Identifiable {
    let user: User
    let metrics: [Metric]
    var habits: [Habit]
    let weeklyProgress: WeeklyProgress

    var id: String {
        user.id
    }

    static let mock = Dashboard(
        user: .mock,
        metrics: Metric.mocks,
        habits: Habit.mocks,
        weeklyProgress: .mock
    )
}

struct Metric: Codable, Identifiable {
    let id: String
    let title: String
    let value: String
    let subtitle: String?
    let trend: String?
    let color: String?

    static let mocks: [Metric] = [
        Metric(
            id: "1",
            title: "Calories Burned",
            value: "1,245",
            subtitle: "of 2,000 goal",
            trend: "+15%",
            color: "#3D8A5A"
        ),
        Metric(
            id: "2",
            title: "Active Minutes",
            value: "45",
            subtitle: "of 60 goal",
            trend: "+8%",
            color: "#5CAD7A"
        ),
        Metric(
            id: "3",
            title: "Sleep Hours",
            value: "7.5",
            subtitle: "hours last night",
            trend: "+5%",
            color: "#3D8A5A"
        ),
        Metric(
            id: "4",
            title: "Water Intake",
            value: "1.5L",
            subtitle: "of 2L goal",
            trend: "-10%",
            color: "#F5A623"
        )
    ]
}

struct Habit: Codable, Identifiable {
    let id: String
    let name: String
    let icon: String
    var completed: Bool
    var streak: Int

    static let mocks: [Habit] = [
        Habit(id: "1", name: "Morning Meditation", icon: "🧘", completed: true, streak: 7),
        Habit(id: "2", name: "Drink Water", icon: "💧", completed: true, streak: 14),
        Habit(id: "3", name: "Exercise", icon: "🏃", completed: false, streak: 3),
        Habit(id: "4", name: "Read Book", icon: "📚", completed: true, streak: 21),
        Habit(id: "5", name: "No Sugar", icon: "🍬", completed: false, streak: 5)
    ]
}

struct WeeklyProgress: Codable {
    let days: [DayProgress]

    static let mock = WeeklyProgress(
        days: [
            DayProgress(day: "Mon", value: 0.8, completed: true),
            DayProgress(day: "Tue", value: 0.6, completed: true),
            DayProgress(day: "Wed", value: 1.0, completed: true),
            DayProgress(day: "Thu", value: 0.4, completed: false),
            DayProgress(day: "Fri", value: 0.9, completed: true),
            DayProgress(day: "Sat", value: 0.7, completed: true),
            DayProgress(day: "Sun", value: 0.5, completed: false)
        ]
    )
}

struct DayProgress: Codable, Identifiable {
    var id: String { day }
    let day: String
    let value: Double
    let completed: Bool
}
