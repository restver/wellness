import SwiftUI

// MARK: - Loading Spinner
struct LoadingSpinner: View {
    var body: some View {
        ProgressView()
            .progressViewStyle(CircularProgressViewStyle(tint: AppColors.primaryGreen))
            .scaleEffect(1.5)
    }
}

// MARK: - Full Screen Loading
struct FullScreenLoading: View {
    var body: some View {
        ZStack {
            AppTheme.shared.colorScheme.background.ignoresSafeArea()

            VStack(spacing: 16) {
                LoadingSpinner()
                Text("Loading...")
                    .font(.appSubheadline)
                    .foregroundColor(AppTheme.shared.colorScheme.textSecondary)
            }
        }
    }
}

// MARK: - Empty State
struct EmptyState: View {
    let icon: String
    let title: String
    let message: String
    let actionTitle: String?
    let action: (() -> Void)?

    init(
        icon: String,
        title: String,
        message: String,
        actionTitle: String? = nil,
        action: (() -> Void)? = nil
    ) {
        self.icon = icon
        self.title = title
        self.message = message
        self.actionTitle = actionTitle
        self.action = action
    }

    var body: some View {
        VStack(spacing: 20) {
            Image(systemName: icon)
                .font(.system(size: 60))
                .foregroundColor(AppTheme.shared.colorScheme.textTertiary)

            VStack(spacing: 8) {
                Text(title)
                    .font(.appTitle3)
                    .foregroundColor(AppTheme.shared.colorScheme.textPrimary)

                Text(message)
                    .font(.appSubheadline)
                    .foregroundColor(AppTheme.shared.colorScheme.textSecondary)
                    .multilineTextAlignment(.center)
            }

            if let actionTitle = actionTitle, let action = action {
                PrimaryButton(title: actionTitle, action: action)
                    .padding(.top, 8)
            }
        }
        .padding(40)
    }
}

// MARK: - Error State
struct ErrorState: View {
    let message: String
    let retry: () -> Void

    var body: some View {
        EmptyState(
            icon: "exclamationmark.triangle",
            title: "Something went wrong",
            message: message,
            actionTitle: "Try Again",
            action: retry
        )
    }
}

#Preview {
    VStack(spacing: 40) {
        FullScreenLoading()
        EmptyState(
            icon: "tray",
            title: "No Data",
            message: "There's nothing here yet"
        )
        ErrorState(message: "Network error occurred") {}
    }
}
