import SwiftUI

// MARK: - Primary Button
struct PrimaryButton: View {
    let title: String
    let action: () -> Void
    var isLoading: Bool = false

    var body: some View {
        Button(action: action) {
            ZStack {
                if isLoading {
                    ProgressView()
                        .progressViewStyle(CircularProgressViewStyle(tint: AppTheme.shared.colorScheme.buttonPrimaryContent))
                } else {
                    Text(title)
                        .font(.appBodyMedium)
                }
            }
            .frame(maxWidth: .infinity)
            .frame(height: 56)
            .background(AppTheme.shared.colorScheme.buttonPrimary)
            .foregroundColor(AppTheme.shared.colorScheme.buttonPrimaryContent)
            .cornerRadius(12)
        }
        .disabled(isLoading)
    }
}

// MARK: - Secondary Button
struct SecondaryButton: View {
    let title: String
    let action: () -> Void
    var isLoading: Bool = false

    var body: some View {
        Button(action: action) {
            ZStack {
                if isLoading {
                    ProgressView()
                        .progressViewStyle(CircularProgressViewStyle(tint: AppTheme.shared.colorScheme.buttonSecondaryContent))
                } else {
                    Text(title)
                        .font(.appBodyMedium)
                }
            }
            .frame(maxWidth: .infinity)
            .frame(height: 56)
            .background(AppTheme.shared.colorScheme.buttonSecondary)
            .foregroundColor(AppTheme.shared.colorScheme.buttonSecondaryContent)
            .cornerRadius(12)
        }
        .disabled(isLoading)
    }
}

// MARK: - Text Button
struct TextButton: View {
    let title: String
    let action: () -> Void

    var body: some View {
        Button(action: action) {
            Text(title)
                .font(.appSubheadline)
                .foregroundColor(AppTheme.shared.colorScheme.textPrimary)
        }
    }
}

// MARK: - Icon Button
struct IconButton: View {
    let icon: String
    let action: () -> Void

    var body: some View {
        Button(action: action) {
            Image(systemName: icon)
                .font(.appBody)
                .foregroundColor(AppTheme.shared.colorScheme.textPrimary)
                .frame(width: 44, height: 44)
        }
    }
}

#Preview {
    VStack(spacing: 16) {
        PrimaryButton(title: "Sign In", action: {})
        SecondaryButton(title: "Create Account", action: {})
        TextButton(title: "Forgot Password?", action: {})
        IconButton(icon: "gear") {}
    }
    .padding()
}
