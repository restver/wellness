import Foundation
import Combine

// MARK: - Profile View Model
@MainActor
@Observable
class ProfileViewModel {
    private let userRepository: UserRepositoryProtocol
    private var cancellables = Set<AnyCancellable>()

    var user: User?
    var isLoading: Bool = false
    var errorMessage: String?
    var isLoggedOut: Bool = false

    init(userRepository: UserRepositoryProtocol = UserRepository()) {
        self.userRepository = userRepository
    }

    func loadUserProfile() {
        isLoading = true
        errorMessage = nil

        userRepository.getCurrentUser()
            .receive(on: DispatchQueue.main)
            .sink { [weak self] completion in
                self?.isLoading = false
                if case .failure(let error) = completion {
                    self?.errorMessage = error.localizedDescription
                }
            } receiveValue: { [weak self] user in
                self?.user = user
            }
            .store(in: &cancellables)
    }

    func logout() {
        userRepository.logout()
            .receive(on: DispatchQueue.main)
            .sink { [weak self] completion in
                if case .failure(let error) = completion {
                    self?.errorMessage = error.localizedDescription
                }
            } receiveValue: { [weak self] in
                self?.isLoggedOut = true
            }
            .store(in: &cancellables)
    }
}
