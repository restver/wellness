import Foundation
import Combine

// MARK: - Dashboard View Model
@MainActor
@Observable
class DashboardViewModel {
    private let repository: DashboardRepositoryProtocol
    private var cancellables = Set<AnyCancellable>()

    var dashboard: Dashboard?
    var isLoading: Bool = false
    var errorMessage: String?

    init(repository: DashboardRepositoryProtocol = DashboardRepository()) {
        self.repository = repository
    }

    func loadDashboard() {
        isLoading = true
        errorMessage = nil

        repository.getDashboard()
            .receive(on: DispatchQueue.main)
            .sink { [weak self] completion in
                self?.isLoading = false
                if case .failure(let error) = completion {
                    self?.errorMessage = error.localizedDescription
                }
            } receiveValue: { [weak self] dashboard in
                self?.dashboard = dashboard
            }
            .store(in: &cancellables)
    }

    func toggleHabit(_ habit: Habit) {
        guard var updatedDashboard = dashboard else { return }

        if let index = updatedDashboard.habits.firstIndex(where: { $0.id == habit.id }) {
            var updatedHabit = updatedDashboard.habits[index]
            updatedHabit.completed.toggle()
            updatedDashboard.habits[index] = updatedHabit
            dashboard = updatedDashboard
        }
    }

    func retry() {
        loadDashboard()
    }
}
