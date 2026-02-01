import Foundation

// MARK: - User Models
struct User: Codable, Identifiable {
    let id: String
    let email: String
    let name: String
    let avatar: String?
    let createdAt: String
    let preferences: UserPreferences

    static let mock = User(
        id: "1",
        email: "user@example.com",
        name: "Sarah",
        avatar: nil,
        createdAt: "2024-01-01T00:00:00Z",
        preferences: UserPreferences()
    )
}

struct UserPreferences: Codable {
    var notificationsEnabled: Bool
    var darkMode: Bool
    var language: String

    init(
        notificationsEnabled: Bool = true,
        darkMode: Bool = false,
        language: String = "en"
    ) {
        self.notificationsEnabled = notificationsEnabled
        self.darkMode = darkMode
        self.language = language
    }
}

struct LoginRequest: Codable {
    let email: String
    let password: String
}

struct LoginResponse: Codable {
    let user: User
    let token: String
    let refreshToken: String
}

struct ForgotPasswordRequest: Codable {
    let email: String
}

struct ResetPasswordRequest: Codable {
    let token: String
    let newPassword: String
}
