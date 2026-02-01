import Foundation
import Combine

// MARK: - Stats View Model
@MainActor
@Observable
class StatsViewModel {
    private let repository: StatsRepositoryProtocol
    private var cancellables = Set<AnyCancellable>()

    var stats: [Stats] = []
    var isLoading: Bool = false
    var errorMessage: String?

    init(repository: StatsRepositoryProtocol = StatsRepository()) {
        self.repository = repository
    }

    func loadStats() {
        isLoading = true
        errorMessage = nil

        repository.getStats()
            .receive(on: DispatchQueue.main)
            .sink { [weak self] completion in
                self?.isLoading = false
                if case .failure(let error) = completion {
                    self?.errorMessage = error.localizedDescription
                }
            } receiveValue: { [weak self] stats in
                self?.stats = stats
            }
            .store(in: &cancellables)
    }

    func retry() {
        loadStats()
    }
}
