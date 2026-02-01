import SwiftUI

// MARK: - App Typography
struct AppTypography {
    // Font sizes
    static let largeTitle: CGFloat = 34
    static let title1: CGFloat = 28
    static let title2: CGFloat = 22
    static let title3: CGFloat = 20
    static let headline: CGFloat = 18
    static let body: CGFloat = 17
    static let callout: CGFloat = 16
    static let subheadline: CGFloat = 15
    static let footnote: CGFloat = 13
    static let caption1: CGFloat = 12
    static let caption2: CGFloat = 11

    // Font weights
    static let bold = Font.Weight.bold
    static let semibold = Font.Weight.semibold
    static let medium = Font.Weight.medium
    static let regular = Font.Weight.regular
    static let light = Font.Weight.light

    // Standard fonts
    static func font(size: CGFloat, weight: Font.Weight = .regular) -> Font {
        .system(size: size, weight: weight)
    }
}

// MARK: - Font Extensions
extension Font {
    static let appLargeTitle = AppTypography.font(size: AppTypography.largeTitle, weight: .bold)
    static let appTitle1 = AppTypography.font(size: AppTypography.title1, weight: .bold)
    static let appTitle2 = AppTypography.font(size: AppTypography.title2, weight: .bold)
    static let appTitle3 = AppTypography.font(size: AppTypography.title3, weight: .semibold)
    static let appHeadline = AppTypography.font(size: AppTypography.headline, weight: .semibold)
    static let appBody = AppTypography.font(size: AppTypography.body, weight: .regular)
    static let appBodyMedium = AppTypography.font(size: AppTypography.body, weight: .medium)
    static let appCallout = AppTypography.font(size: AppTypography.callout, weight: .regular)
    static let appSubheadline = AppTypography.font(size: AppTypography.subheadline, weight: .regular)
    static let appFootnote = AppTypography.font(size: AppTypography.footnote, weight: .regular)
    static let appCaption1 = AppTypography.font(size: AppTypography.caption1, weight: .regular)
    static let appCaption2 = AppTypography.font(size: AppTypography.caption2, weight: .regular)
}
