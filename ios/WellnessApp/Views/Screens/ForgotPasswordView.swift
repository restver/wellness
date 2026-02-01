import SwiftUI

// MARK: - Forgot Password View
struct ForgotPasswordView: View {
    @Environment(\.dismiss) private var dismiss
    @Binding var isPresented: Bool

    @State private var email: String = ""
    @State private var isLoading: Bool = false
    @State private var isSuccess: Bool = false
    @State private var errorMessage: String?

    var body: some View {
        NavigationStack {
            ScrollView {
                VStack(spacing: 32) {
                    VStack(spacing: 16) {
                        Image(systemName: "lock.shield")
                            .font(.system(size: 60))
                            .foregroundColor(AppColors.primaryGreen)

                        Text("Forgot Password?")
                            .font(.appTitle1)

                        Text("Enter your email address and we'll send you a link to reset your password.")
                            .font(.appSubheadline)
                            .foregroundColor(AppTheme.shared.colorScheme.textSecondary)
                            .multilineTextAlignment(.center)
                    }
                    .padding(.horizontal, 24)

                    if isSuccess {
                        VStack(spacing: 16) {
                            Image(systemName: "checkmark.circle.fill")
                                .font(.system(size: 60))
                                .foregroundColor(AppColors.success)

                            Text("Email Sent!")
                                .font(.appTitle3)

                            Text("Please check your email for instructions to reset your password.")
                                .font(.appSubheadline)
                                .foregroundColor(AppTheme.shared.colorScheme.textSecondary)
                                .multilineTextAlignment(.center)
                        }
                        .padding(.horizontal, 24)
                    } else {
                        VStack(spacing: 16) {
                            AppInputField(
                                text: $email,
                                title: "Email",
                                placeholder: "Enter your email",
                                isError: errorMessage != nil,
                                errorMessage: errorMessage,
                                keyboardType: .emailAddress,
                                autocapitalization: .none
                            )

                            PrimaryButton(
                                title: "Send Reset Link",
                                action: {
                                    sendResetLink()
                                },
                                isLoading: isLoading
                            )
                        }
                        .padding(.horizontal, 24)
                    }
                }
                .padding(.vertical, 40)
            }
            .background(AppTheme.shared.colorScheme.background.ignoresSafeArea())
            .navigationTitle("Forgot Password")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .navigationBarLeading) {
                    Button("Cancel") {
                        isPresented = false
                    }
                }
            }
        }
    }

    private func sendResetLink() {
        guard !email.isEmpty else {
            errorMessage = "Please enter your email"
            return
        }

        guard email.contains("@"), email.contains(".") else {
            errorMessage = "Invalid email format"
            return
        }

        isLoading = true
        errorMessage = nil

        // Mock API call
        DispatchQueue.main.asyncAfter(deadline: .now() + 1.5) {
            isLoading = false
            isSuccess = true
        }
    }
}

#Preview {
    ForgotPasswordView(isPresented: .constant(true))
}
