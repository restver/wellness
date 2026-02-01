import Foundation
import Combine

// MARK: - Calendar View Model
@MainActor
@Observable
class CalendarViewModel {
    private let repository: CalendarRepositoryProtocol
    private var cancellables = Set<AnyCancellable>()

    var events: [CalendarEvent] = []
    var isLoading: Bool = false
    var errorMessage: String?

    init(repository: CalendarRepositoryProtocol = CalendarRepository()) {
        self.repository = repository
    }

    func loadEvents() {
        isLoading = true
        errorMessage = nil

        repository.getEvents()
            .receive(on: DispatchQueue.main)
            .sink { [weak self] completion in
                self?.isLoading = false
                if case .failure(let error) = completion {
                    self?.errorMessage = error.localizedDescription
                }
            } receiveValue: { [weak self] events in
                self?.events = events
            }
            .store(in: &cancellables)
    }

    func toggleEventCompletion(_ event: CalendarEvent) {
        if let index = events.firstIndex(where: { $0.id == event.id }) {
            events[index].isCompleted.toggle()
        }
    }

    func eventsForDate(_ date: Date) -> [CalendarEvent] {
        let calendar = Calendar.current
        return events.filter { event in
            calendar.isDate(date, inSameDayAs: event.startDate)
        }
    }

    var todayEvents: [CalendarEvent] {
        eventsForDate(Date())
    }

    func retry() {
        loadEvents()
    }
}
