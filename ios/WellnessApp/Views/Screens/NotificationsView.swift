import SwiftUI

// MARK: - Notifications View
struct NotificationsView: View {
    @State private var viewModel: NotificationsViewModel?

    var body: some View {
        ZStack {
            if let viewModel = viewModel {
                if viewModel.isLoading && viewModel.notifications.isEmpty {
                    FullScreenLoading()
                } else if let errorMessage = viewModel.errorMessage, viewModel.notifications.isEmpty {
                    ErrorState(message: errorMessage) {
                        viewModel.loadNotifications()
                    }
                } else {
                    ScrollView {
                        VStack(spacing: 20) {
                            // Header
                            HStack {
                                Text("Notifications")
                                    .font(.appTitle1)

                                if viewModel.unreadCount > 0 {
                                    Text("\(viewModel.unreadCount)")
                                        .font(.appSubheadline)
                                        .foregroundColor(.white)
                                        .padding(.horizontal, 8)
                                        .padding(.vertical, 4)
                                        .background(AppColors.error)
                                        .clipShape(Capsule())
                                }

                                Spacer()
                            }
                            .padding(.horizontal, 24)

                            // Notifications List
                            if viewModel.notifications.isEmpty {
                                EmptyState(
                                    icon: "bell.slash",
                                    title: "No Notifications",
                                    message: "You're all caught up!"
                                )
                            } else {
                                VStack(spacing: 12) {
                                    ForEach(viewModel.notifications) { notification in
                                        NotificationCard(notification: notification) {
                                            viewModel.markAsRead(notification)
                                        }
                                    }
                                }
                                .padding(.horizontal, 24)
                            }

                            Spacer().frame(height: 100)
                        }
                        .padding(.top, 20)
                    }
                }
            }
        }
        .task {
            if viewModel == nil {
                viewModel = NotificationsViewModel()
            }
            viewModel?.loadNotifications()
        }
    }
}

#Preview {
    NavigationStack {
        NotificationsView()
    }
}
