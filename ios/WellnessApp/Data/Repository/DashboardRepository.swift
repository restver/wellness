import Foundation
import Combine

// MARK: - Dashboard Repository
protocol DashboardRepositoryProtocol {
    func getDashboard() -> AnyPublisher<Dashboard, Error>
}

class DashboardRepository: DashboardRepositoryProtocol {
    private let apiService: APIServiceProtocol

    init(apiService: APIServiceProtocol = APIService.shared) {
        self.apiService = apiService
    }

    func getDashboard() -> AnyPublisher<Dashboard, Error> {
        // Mock dashboard data
        return Just(Dashboard.mock)
            .setFailureType(to: Error.self)
            .delay(for: .seconds(0.5), scheduler: DispatchQueue.main)
            .eraseToAnyPublisher()
    }
}

// MARK: - Stats Repository
protocol StatsRepositoryProtocol {
    func getStats() -> AnyPublisher<[Stats], Error>
}

class StatsRepository: StatsRepositoryProtocol {
    private let apiService: APIServiceProtocol

    init(apiService: APIServiceProtocol = APIService.shared) {
        self.apiService = apiService
    }

    func getStats() -> AnyPublisher<[Stats], Error> {
        return Just(Stats.mocks)
            .setFailureType(to: Error.self)
            .delay(for: .seconds(0.5), scheduler: DispatchQueue.main)
            .eraseToAnyPublisher()
    }
}

// MARK: - Notification Repository
protocol NotificationRepositoryProtocol {
    func getNotifications() -> AnyPublisher<[AppNotification], Error>
    func markAsRead(id: String) -> AnyPublisher<Void, Error>
}

class NotificationRepository: NotificationRepositoryProtocol {
    private let apiService: APIServiceProtocol

    init(apiService: APIServiceProtocol = APIService.shared) {
        self.apiService = apiService
    }

    func getNotifications() -> AnyPublisher<[AppNotification], Error> {
        return Just(AppNotification.mocks)
            .setFailureType(to: Error.self)
            .delay(for: .seconds(0.3), scheduler: DispatchQueue.main)
            .eraseToAnyPublisher()
    }

    func markAsRead(id: String) -> AnyPublisher<Void, Error> {
        return Just(())
            .setFailureType(to: Error.self)
            .eraseToAnyPublisher()
    }
}

// MARK: - Calendar Repository
protocol CalendarRepositoryProtocol {
    func getEvents() -> AnyPublisher<[CalendarEvent], Error>
}

class CalendarRepository: CalendarRepositoryProtocol {
    private let apiService: APIServiceProtocol

    init(apiService: APIServiceProtocol = APIService.shared) {
        self.apiService = apiService
    }

    func getEvents() -> AnyPublisher<[CalendarEvent], Error> {
        return Just(CalendarEvent.mocks)
            .setFailureType(to: Error.self)
            .delay(for: .seconds(0.3), scheduler: DispatchQueue.main)
            .eraseToAnyPublisher()
    }
}
