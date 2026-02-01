import SwiftUI

// MARK: - Input Field
struct AppInputField: View {
    @Binding var text: String
    let title: String
    let placeholder: String
    var isSecure: Bool = false
    var isError: Bool = false
    var errorMessage: String? = nil
    var keyboardType: UIKeyboardType = .default
    var autocapitalization: UITextAutocapitalizationType = .sentences

    @State private var isSecureVisible: Bool = false
    @FocusState private var isFocused: Bool

    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text(title)
                .font(.appSubheadline)
                .foregroundColor(AppTheme.shared.colorScheme.textSecondary)

            HStack(spacing: 12) {
                Group {
                    if isSecure && !isSecureVisible {
                        SecureField(placeholder, text: $text)
                    } else {
                        TextField(placeholder, text: $text)
                            .keyboardType(keyboardType)
                            .autocapitalization(autocapitalization)
                    }
                }
                .font(.appBody)
                .foregroundColor(AppTheme.shared.colorScheme.textPrimary)
                .padding(.horizontal, 16)
                .frame(height: 56)
                .background(AppTheme.shared.colorScheme.inputBackground)
                .cornerRadius(12)
                .overlay(
                    RoundedRectangle(cornerRadius: 12)
                        .stroke(
                            isError ? AppColors.error :
                            (isFocused ? AppTheme.shared.colorScheme.inputBorderFocused : AppTheme.shared.colorScheme.inputBorder),
                            lineWidth: isError ? 2 : 1
                        )
                )
                .focused($isFocused)

                if isSecure {
                    Button(action: { isSecureVisible.toggle() }) {
                        Image(systemName: isSecureVisible ? "eye.slash.fill" : "eye.fill")
                            .foregroundColor(AppTheme.shared.colorScheme.textSecondary)
                    }
                    .offset(x: -32)
                }
            }

            if let errorMessage = errorMessage, isError {
                Text(errorMessage)
                    .font(.appFootnote)
                    .foregroundColor(AppColors.error)
            }
        }
    }
}

#Preview {
    VStack(spacing: 20) {
        AppInputField(
            text: .constant(""),
            title: "Email",
            placeholder: "Enter your email",
            isError: false
        )
        AppInputField(
            text: .constant(""),
            title: "Password",
            placeholder: "Enter your password",
            isSecure: true,
            isError: true,
            errorMessage: "Invalid password"
        )
    }
    .padding()
}
