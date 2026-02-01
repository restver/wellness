import Foundation
import Combine

// MARK: - Notifications View Model
@MainActor
@Observable
class NotificationsViewModel {
    private let repository: NotificationRepositoryProtocol
    private var cancellables = Set<AnyCancellable>()

    var notifications: [AppNotification] = []
    var isLoading: Bool = false
    var errorMessage: String?

    init(repository: NotificationRepositoryProtocol = NotificationRepository()) {
        self.repository = repository
    }

    func loadNotifications() {
        isLoading = true
        errorMessage = nil

        repository.getNotifications()
            .receive(on: DispatchQueue.main)
            .sink { [weak self] completion in
                self?.isLoading = false
                if case .failure(let error) = completion {
                    self?.errorMessage = error.localizedDescription
                }
            } receiveValue: { [weak self] notifications in
                self?.notifications = notifications
            }
            .store(in: &cancellables)
    }

    func markAsRead(_ notification: AppNotification) {
        repository.markAsRead(id: notification.id)
            .receive(on: DispatchQueue.main)
            .sink { [weak self] completion in
                if case .failure(let error) = completion {
                    self?.errorMessage = error.localizedDescription
                }
            } receiveValue: { [weak self] in
                if let index = self?.notifications.firstIndex(where: { $0.id == notification.id }) {
                    self?.notifications[index].isRead = true
                }
            }
            .store(in: &cancellables)
    }

    var unreadCount: Int {
        notifications.filter { !$0.isRead }.count
    }

    func retry() {
        loadNotifications()
    }
}
