import Foundation

// MARK: - Calendar Models
struct CalendarEvent: Codable, Identifiable, Equatable {
    let id: String
    let title: String
    let description: String?
    let startDate: Date
    let endDate: Date
    let type: EventType
    var isCompleted: Bool

    enum EventType: String, Codable {
        case workout = "workout"
        case meal = "meal"
        case meditation = "meditation"
        case appointment = "appointment"
        case other = "other"
    }

    var icon: String {
        switch type {
        case .workout: return "🏃"
        case .meal: return "🍎"
        case .meditation: return "🧘"
        case .appointment: return "📅"
        case .other: return "📌"
        }
    }

    static let mocks: [CalendarEvent] = [
        CalendarEvent(
            id: "1",
            title: "Morning Workout",
            description: "30 min cardio session",
            startDate: Calendar.current.date(byAdding: .hour, value: 7, to: Date())!,
            endDate: Calendar.current.date(byAdding: .hour, value: 8, to: Date())!,
            type: .workout,
            isCompleted: true
        ),
        CalendarEvent(
            id: "2",
            title: "Healthy Lunch",
            description: "Grilled chicken salad",
            startDate: Calendar.current.date(byAdding: .hour, value: 12, to: Date())!,
            endDate: Calendar.current.date(byAdding: .hour, value: 13, to: Date())!,
            type: .meal,
            isCompleted: false
        ),
        CalendarEvent(
            id: "3",
            title: "Evening Meditation",
            description: "15 min mindfulness session",
            startDate: Calendar.current.date(byAdding: .hour, value: 19, to: Date())!,
            endDate: Calendar.current.date(byAdding: .hour, value: 19, to: Date())!.addingTimeInterval(900),
            type: .meditation,
            isCompleted: false
        )
    ]
}
