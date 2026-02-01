import Foundation
import Combine

// MARK: - Login View Model
@Observable
class LoginViewModel {
    private let userRepository: UserRepositoryProtocol
    private var cancellables = Set<AnyCancellable>()

    var email: String = ""
    var password: String = ""
    var isLoading: Bool = false
    var errorMessage: String?
    var isLoggedIn: Bool = false

    init(userRepository: UserRepositoryProtocol = UserRepository()) {
        self.userRepository = userRepository
    }

    func login() {
        guard validateInput() else { return }

        isLoading = true
        errorMessage = nil

        userRepository.login(email: email, password: password)
            .receive(on: DispatchQueue.main)
            .sink { [weak self] completion in
                self?.isLoading = false
                if case .failure(let error) = completion {
                    self?.errorMessage = error.localizedDescription
                }
            } receiveValue: { [weak self] response in
                KeychainService.shared.saveToken(response.token)
                self?.isLoggedIn = true
            }
            .store(in: &cancellables)
    }

    private func validateInput() -> Bool {
        guard !email.isEmpty, !password.isEmpty else {
            errorMessage = "Please fill in all fields"
            return false
        }

        guard email.contains("@"), email.contains(".") else {
            errorMessage = "Invalid email format"
            return false
        }

        guard password.count >= 6 else {
            errorMessage = "Password must be at least 6 characters"
            return false
        }

        return true
    }

    func clearError() {
        errorMessage = nil
    }
}
