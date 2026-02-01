import SwiftUI

// MARK: - App Colors
struct AppColors {
    // Primary colors
    static let primaryGreen = Color(hex: "3D8A5A")
    static let primaryGreenDark = Color(hex: "2E6B45")
    static let primaryGreenLight = Color(hex: "5CAD7A")

    // Text colors
    static let textPrimary = Color(hex: "1A1918")
    static let textSecondary = Color(hex: "6D6C6A")
    static let textTertiary = Color(hex: "9E9D9B")

    // UI Colors
    static let divider = Color(hex: "EDECEA")
    static let border = Color(hex: "E8E7E4")
    static let error = Color(hex: "DC362E")
    static let success = Color(hex: "3D8A5A")
    static let warning = Color(hex: "F5A623")

    // Input colors
    static let inputBorder = Color(hex: "E8E7E4")
    static let inputBorderFocused = Color(hex: "3D8A5A")
}

// MARK: - Color Extension
extension Color {
    init(hex: String) {
        let hex = hex.trimmingCharacters(in: CharacterSet.alphanumerics.inverted)
        var int: UInt64 = 0
        Scanner(string: hex).scanHexInt64(&int)
        let a, r, g, b: UInt64
        switch hex.count {
        case 3: // RGB (12-bit)
            (a, r, g, b) = (255, (int >> 8) * 17, (int >> 4 & 0xF) * 17, (int & 0xF) * 17)
        case 6: // RGB (24-bit)
            (a, r, g, b) = (255, int >> 16, int >> 8 & 0xFF, int & 0xFF)
        case 8: // ARGB (32-bit)
            (a, r, g, b) = (int >> 24, int >> 16 & 0xFF, int >> 8 & 0xFF, int & 0xFF)
        default:
            (a, r, g, b) = (255, 0, 0, 0)
        }
        self.init(
            .sRGB,
            red: Double(r) / 255,
            green: Double(g) / 255,
            blue: Double(b) / 255,
            opacity: Double(a) / 255
        )
    }
}

// MARK: - Theme Color Scheme
struct ThemeColorScheme {
    let background: Color
    let surface: Color
    let cardBackground: Color
    let tabBarBackground: Color
    let statusBarBackground: Color
    let textPrimary: Color
    let textSecondary: Color
    let textTertiary: Color
    let divider: Color
    let border: Color
    let inputBackground: Color
    let inputBorder: Color
    let inputBorderFocused: Color
    let buttonPrimary: Color
    let buttonPrimaryContent: Color
    let buttonSecondary: Color
    let buttonSecondaryContent: Color

    static let light = ThemeColorScheme(
        background: Color(hex: "F5F4F1"),
        surface: Color.white,
        cardBackground: Color.white,
        tabBarBackground: Color.white,
        statusBarBackground: Color.white,
        textPrimary: Color(hex: "1A1918"),
        textSecondary: Color(hex: "6D6C6A"),
        textTertiary: Color(hex: "9E9D9B"),
        divider: Color(hex: "EDECEA"),
        border: Color(hex: "E8E7E4"),
        inputBackground: Color.white,
        inputBorder: Color(hex: "E8E7E4"),
        inputBorderFocused: AppColors.primaryGreen,
        buttonPrimary: AppColors.primaryGreen,
        buttonPrimaryContent: Color.white,
        buttonSecondary: Color(hex: "F5F4F1"),
        buttonSecondaryContent: Color(hex: "1A1918")
    )

    static let dark = ThemeColorScheme(
        background: Color(hex: "121212"),
        surface: Color(hex: "1E1E1E"),
        cardBackground: Color(hex: "1E1E1E"),
        tabBarBackground: Color(hex: "1E1E1E"),
        statusBarBackground: Color(hex: "1E1E1E"),
        textPrimary: Color.white,
        textSecondary: Color(hex: "A0A0A0"),
        textTertiary: Color(hex: "707070"),
        divider: Color(hex: "2E2E2E"),
        border: Color(hex: "333333"),
        inputBackground: Color(hex: "1E1E1E"),
        inputBorder: Color(hex: "333333"),
        inputBorderFocused: AppColors.primaryGreenLight,
        buttonPrimary: AppColors.primaryGreenLight,
        buttonPrimaryContent: Color.white,
        buttonSecondary: Color(hex: "2E2E2E"),
        buttonSecondaryContent: Color.white
    )
}
