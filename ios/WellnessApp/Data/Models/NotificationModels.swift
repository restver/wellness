import Foundation

// MARK: - Notification Models
struct AppNotification: Codable, Identifiable {
    let id: String
    let title: String
    let message: String
    let type: NotificationType
    var isRead: Bool
    let timestamp: Date

    enum NotificationType: String, Codable {
        case achievement = "achievement"
        case reminder = "reminder"
        case progress = "progress"
        case system = "system"
    }

    var timeAgo: String {
        let formatter = RelativeDateTimeFormatter()
        formatter.unitsStyle = .abbreviated
        return formatter.localizedString(for: timestamp, relativeTo: Date())
    }

    static let mocks: [AppNotification] = [
        AppNotification(
            id: "1",
            title: "Achievement Unlocked!",
            message: "You've completed 7 days of meditation streak!",
            type: .achievement,
            isRead: false,
            timestamp: Date().addingTimeInterval(-3600)
        ),
        AppNotification(
            id: "2",
            title: "Daily Reminder",
            message: "Don't forget to log your water intake today.",
            type: .reminder,
            isRead: false,
            timestamp: Date().addingTimeInterval(-7200)
        ),
        AppNotification(
            id: "3",
            title: "Progress Update",
            message: "You're 80% towards your weekly goal!",
            type: .progress,
            isRead: true,
            timestamp: Date().addingTimeInterval(-86400)
        ),
        AppNotification(
            id: "4",
            title: "Welcome",
            message: "Thanks for joining Wellness! Let's start your journey.",
            type: .system,
            isRead: true,
            timestamp: Date().addingTimeInterval(-172800)
        )
    ]
}
