import Foundation
import Combine

// MARK: - User Repository
protocol UserRepositoryProtocol {
    func login(email: String, password: String) -> AnyPublisher<LoginResponse, Error>
    func forgotPassword(email: String) -> AnyPublisher<Void, Error>
    func logout() -> AnyPublisher<Void, Error>
    func getCurrentUser() -> AnyPublisher<User, Error>
}

class UserRepository: UserRepositoryProtocol {
    private let apiService: APIServiceProtocol
    private let keychainService: KeychainServiceProtocol

    init(
        apiService: APIServiceProtocol = APIService.shared,
        keychainService: KeychainServiceProtocol = KeychainService.shared
    ) {
        self.apiService = apiService
        self.keychainService = keychainService
    }

    func login(email: String, password: String) -> AnyPublisher<LoginResponse, Error> {
        // Mock login - replace with actual API call
        return Just(LoginResponse(
            user: .mock,
            token: "mock_token",
            refreshToken: "mock_refresh_token"
        ))
        .setFailureType(to: Error.self)
        .eraseToAnyPublisher()
    }

    func forgotPassword(email: String) -> AnyPublisher<Void, Error> {
        // Mock forgot password
        return Just(())
            .setFailureType(to: Error.self)
            .delay(for: .seconds(1), scheduler: DispatchQueue.main)
            .eraseToAnyPublisher()
    }

    func logout() -> AnyPublisher<Void, Error> {
        keychainService.deleteToken()
        return Just(())
            .setFailureType(to: Error.self)
            .eraseToAnyPublisher()
    }

    func getCurrentUser() -> AnyPublisher<User, Error> {
        return Just(User.mock)
            .setFailureType(to: Error.self)
            .eraseToAnyPublisher()
    }
}

// MARK: - Keychain Service
protocol KeychainServiceProtocol {
    func saveToken(_ token: String)
    func getToken() -> String?
    func deleteToken()
}

class KeychainService: KeychainServiceProtocol {
    static let shared = KeychainService()
    private let key = "auth_token"

    private init() {}

    func saveToken(_ token: String) {
        UserDefaults.standard.set(token, forKey: key)
    }

    func getToken() -> String? {
        return UserDefaults.standard.string(forKey: key)
    }

    func deleteToken() {
        UserDefaults.standard.removeObject(forKey: key)
    }
}
