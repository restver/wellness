import Foundation
import Combine

// MARK: - Settings View Model
@Observable
class SettingsViewModel {
    @ObservationIgnored
    private let appTheme = AppTheme.shared

    var notificationsEnabled: Bool {
        didSet {
            UserDefaults.standard.set(notificationsEnabled, forKey: "notificationsEnabled")
        }
    }

    var selectedLanguage: String {
        didSet {
            UserDefaults.standard.set(selectedLanguage, forKey: "language")
        }
    }

    var isDarkMode: Bool {
        get { appTheme.isDarkMode }
        set { appTheme.isDarkMode = newValue }
    }

    let languages = ["English", "Spanish", "French", "German", "Chinese"]

    init() {
        self.notificationsEnabled = UserDefaults.standard.bool(forKey: "notificationsEnabled")
        self.selectedLanguage = UserDefaults.standard.string(forKey: "language") ?? "English"
    }

    func toggleTheme() {
        appTheme.toggleTheme()
    }

    func toggleNotifications() {
        notificationsEnabled.toggle()
    }

    func changeLanguage(_ language: String) {
        selectedLanguage = language
    }
}
