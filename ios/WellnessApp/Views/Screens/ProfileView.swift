import SwiftUI

// MARK: - Profile View
struct ProfileView: View {
    @State private var viewModel: ProfileViewModel?
    @State private var showLogoutAlert: Bool = false

    var body: some View {
        ZStack {
            if let viewModel = viewModel {
                if viewModel.isLoading && viewModel.user == nil {
                    FullScreenLoading()
                } else if let user = viewModel.user {
                    VStack(spacing: 0) {
                        ScrollView {
                            VStack(spacing: 24) {
                                // Header
                                VStack(spacing: 16) {
                                    Circle()
                                        .fill(AppColors.primaryGreen)
                                        .frame(width: 80, height: 80)
                                        .overlay {
                                            Text(String(user.name.prefix(1)))
                                                .font(.system(size: 32, weight: .bold))
                                                .foregroundColor(.white)
                                        }

                                    Text(user.name)
                                        .font(.appTitle1)

                                    Text(user.email)
                                        .font(.appSubheadline)
                                        .foregroundColor(AppTheme.shared.colorScheme.textSecondary)
                                }
                                .padding(.top, 20)

                                // Info Cards
                                VStack(spacing: 0) {
                                    ProfileInfoRow(icon: "person.fill", title: "Name", value: user.name)
                                    Divider()
                                        .padding(.leading, 72)
                                    ProfileInfoRow(icon: "envelope.fill", title: "Email", value: user.email)
                                    Divider()
                                        .padding(.leading, 72)
                                    ProfileInfoRow(
                                        icon: "calendar",
                                        title: "Member Since",
                                        value: formatDate(user.createdAt)
                                    )
                                }
                                .background(AppTheme.shared.colorScheme.cardBackground)
                                .cornerRadius(16)

                                // Logout Button
                                SecondaryButton(title: "Log Out", action: {
                                    showLogoutAlert = true
                                })
                                .padding(.horizontal, 24)

                                Spacer().frame(height: 100)
                            }
                            .padding(.horizontal, 24)
                        }

                        AppTabBar(selectedTab: .constant(.profile))
                    }
                }
            }
        }
        .task {
            if viewModel == nil {
                viewModel = ProfileViewModel()
            }
            viewModel?.loadUserProfile()
        }
        .alert("Log Out", isPresented: $showLogoutAlert) {
            Button("Cancel", role: .cancel) { }
            Button("Log Out", role: .destructive) {
                viewModel?.logout()
            }
        } message: {
            Text("Are you sure you want to log out?")
        }
        .onChange(of: viewModel?.isLoggedOut ?? false) { _, newValue in
            if newValue {
                // Handle logout - navigate to login
            }
        }
    }

    private func formatDate(_ dateString: String) -> String {
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ssZ"

        if let date = formatter.date(from: dateString) {
            let displayFormatter = DateFormatter()
            displayFormatter.dateStyle = .medium
            return displayFormatter.string(from: date)
        }

        return dateString
    }
}

// MARK: - Profile Info Row
struct ProfileInfoRow: View {
    let icon: String
    let title: String
    let value: String

    var body: some View {
        HStack(spacing: 16) {
            Image(systemName: icon)
                .font(.appBody)
                .foregroundColor(AppColors.primaryGreen)
                .frame(width: 24)

            Text(title)
                .font(.appBody)
                .foregroundColor(AppTheme.shared.colorScheme.textSecondary)
                .frame(width: 100, alignment: .leading)

            Text(value)
                .font(.appBody)
                .foregroundColor(AppTheme.shared.colorScheme.textPrimary)

            Spacer()
        }
        .padding(.horizontal, 24)
        .padding(.vertical, 16)
    }
}

#Preview {
    NavigationStack {
        ProfileView()
    }
}
