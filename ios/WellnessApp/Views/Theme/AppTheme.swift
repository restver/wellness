import SwiftUI

// MARK: - App Theme
@Observable
class AppTheme {
    static let shared = AppTheme()

    var isDarkMode: Bool {
        didSet {
            UserDefaults.standard.set(isDarkMode, forKey: "isDarkMode")
        }
    }

    var colorScheme: ThemeColorScheme {
        isDarkMode ? .dark : .light
    }

    private init() {
        self.isDarkMode = UserDefaults.standard.bool(forKey: "isDarkMode")
    }

    func toggleTheme() {
        isDarkMode.toggle()
    }
}

// MARK: - View Extension
extension View {
    func appTheme() -> some View {
        self.environment(\.colorScheme, AppTheme.shared.isDarkMode ? .dark : .light)
    }
}
