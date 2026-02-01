import SwiftUI

// MARK: - Login View
struct LoginView: View {
    @State private var viewModel = LoginViewModel()
    @State private var showForgotPassword: Bool = false

    var onLoginSuccess: () -> Void = {}
    var onSignUp: () -> Void = {}

    var body: some View {
        ScrollView {
            VStack(spacing: 0) {
                Spacer().frame(height: 80)

                // Logo and welcome text
                VStack(spacing: 16) {
                    Text("🌿")
                        .font(.system(size: 64))

                    Text("Welcome Back")
                        .font(.appTitle1)

                    Text("Sign in to continue your wellness journey")
                        .font(.appSubheadline)
                        .foregroundColor(AppTheme.shared.colorScheme.textSecondary)
                        .multilineTextAlignment(.center)
                }
                .padding(.horizontal, 24)

                Spacer().frame(height: 32)

                // Login form
                VStack(spacing: 16) {
                    AppInputField(
                        text: $viewModel.email,
                        title: "Email",
                        placeholder: "Enter your email",
                        isError: viewModel.errorMessage != nil,
                        errorMessage: viewModel.errorMessage,
                        keyboardType: .emailAddress,
                        autocapitalization: .none
                    )

                    AppInputField(
                        text: $viewModel.password,
                        title: "Password",
                        placeholder: "Enter your password",
                        isSecure: true,
                        isError: viewModel.errorMessage != nil
                    )

                    HStack {
                        Spacer()
                        TextButton(title: "Forgot Password?") {
                            showForgotPassword = true
                        }
                    }

                    PrimaryButton(
                        title: "Sign In",
                        action: {
                            viewModel.clearError()
                            viewModel.login()
                        },
                        isLoading: viewModel.isLoading
                    )
                }
                .padding(.horizontal, 24)

                Spacer()

                // Sign up link
                HStack(spacing: 4) {
                    Text("Don't have an account? ")
                        .font(.appSubheadline)
                        .foregroundColor(AppTheme.shared.colorScheme.textSecondary)

                    TextButton(title: "Sign Up") {
                        onSignUp()
                    }
                }
                .padding(40)
            }
        }
        .background(AppTheme.shared.colorScheme.background.ignoresSafeArea())
        .onChange(of: viewModel.isLoggedIn) { _, newValue in
            if newValue {
                onLoginSuccess()
            }
        }
        .sheet(isPresented: $showForgotPassword) {
            ForgotPasswordView(isPresented: $showForgotPassword)
        }
    }
}

#Preview {
    NavigationStack {
        LoginView()
    }
}
