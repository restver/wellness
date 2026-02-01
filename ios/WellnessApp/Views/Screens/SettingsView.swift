import SwiftUI

// MARK: - Settings View
struct SettingsView: View {
    @State private var viewModel = SettingsViewModel()
    @State private var showLanguagePicker: Bool = false

    var body: some View {
        VStack(spacing: 0) {
            ScrollView {
                VStack(spacing: 24) {
                    // Header
                    HStack {
                        Text("Settings")
                            .font(.appTitle1)

                        Spacer()
                    }
                    .padding(.horizontal, 24)
                    .padding(.top, 20)

                    // Appearance Section
                    VStack(alignment: .leading, spacing: 16) {
                        Text("Appearance")
                            .font(.appHeadline)
                            .foregroundColor(AppTheme.shared.colorScheme.textSecondary)
                            .padding(.horizontal, 24)

                        VStack(spacing: 0) {
                            SettingsRow(
                                icon: "moon.fill",
                                title: "Dark Mode",
                                value: viewModel.isDarkMode ? "On" : "Off"
                            ) {
                                viewModel.toggleTheme()
                            }
                            .disabled(false)

                            Divider()
                                .padding(.leading, 72)
                        }
                        .background(AppTheme.shared.colorScheme.cardBackground)
                        .cornerRadius(16)
                    }

                    // Notifications Section
                    VStack(alignment: .leading, spacing: 16) {
                        Text("Notifications")
                            .font(.appHeadline)
                            .foregroundColor(AppTheme.shared.colorScheme.textSecondary)
                            .padding(.horizontal, 24)

                        VStack(spacing: 0) {
                            SettingsRow(
                                icon: "bell.fill",
                                title: "Push Notifications",
                                value: viewModel.notificationsEnabled ? "On" : "Off"
                            ) {
                                viewModel.toggleNotifications()
                            }

                            Divider()
                                .padding(.leading, 72)

                            SettingsRow(
                                icon: "envelope.fill",
                                title: "Email Notifications",
                                value: "On",
                                action: {}
                            )
                        }
                        .background(AppTheme.shared.colorScheme.cardBackground)
                        .cornerRadius(16)
                    }

                    // Preferences Section
                    VStack(alignment: .leading, spacing: 16) {
                        Text("Preferences")
                            .font(.appHeadline)
                            .foregroundColor(AppTheme.shared.colorScheme.textSecondary)
                            .padding(.horizontal, 24)

                        VStack(spacing: 0) {
                            SettingsRow(
                                icon: "globe",
                                title: "Language",
                                value: viewModel.selectedLanguage
                            ) {
                                showLanguagePicker = true
                            }

                            Divider()
                                .padding(.leading, 72)

                            SettingsRow(
                                icon: "ruler",
                                title: "Units",
                                value: "Metric",
                                action: {}
                            )
                        }
                        .background(AppTheme.shared.colorScheme.cardBackground)
                        .cornerRadius(16)
                    }

                    // Support Section
                    VStack(alignment: .leading, spacing: 16) {
                        Text("Support")
                            .font(.appHeadline)
                            .foregroundColor(AppTheme.shared.colorScheme.textSecondary)
                            .padding(.horizontal, 24)

                        VStack(spacing: 0) {
                            SettingsRow(
                                icon: "questionmark.circle",
                                title: "Help Center",
                                value: nil,
                                action: {}
                            )

                            Divider()
                                .padding(.leading, 72)

                            SettingsRow(
                                icon: "info.circle",
                                title: "About",
                                value: nil,
                                action: {}
                            )
                        }
                        .background(AppTheme.shared.colorScheme.cardBackground)
                        .cornerRadius(16)
                    }

                    // App Version
                    Text("Version 1.0.0")
                        .font(.appFootnote)
                        .foregroundColor(AppTheme.shared.colorScheme.textTertiary)

                    Spacer().frame(height: 100)
                }
            }

            AppTabBar(selectedTab: .constant(.settings))
        }
        .sheet(isPresented: $showLanguagePicker) {
            LanguagePickerView(
                selectedLanguage: $viewModel.selectedLanguage,
                isPresented: $showLanguagePicker
            )
        }
    }
}

// MARK: - Language Picker View
struct LanguagePickerView: View {
    @Binding var selectedLanguage: String
    @Binding var isPresented: Bool

    private let languages = ["English", "Spanish", "French", "German", "Chinese"]

    var body: some View {
        NavigationStack {
            List(languages, id: \.self) { language in
                Button(action: {
                    selectedLanguage = language
                    isPresented = false
                }) {
                    HStack {
                        Text(language)
                            .foregroundColor(AppTheme.shared.colorScheme.textPrimary)

                        Spacer()

                        if selectedLanguage == language {
                            Image(systemName: "checkmark")
                                .foregroundColor(AppColors.primaryGreen)
                        }
                    }
                }
            }
            .navigationTitle("Select Language")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button("Done") {
                        isPresented = false
                    }
                }
            }
        }
    }
}

#Preview {
    NavigationStack {
        SettingsView()
    }
}
