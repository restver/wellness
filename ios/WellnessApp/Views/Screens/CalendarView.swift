import SwiftUI

// MARK: - Calendar View
struct CalendarView: View {
    @State private var viewModel: CalendarViewModel?
    @State private var selectedDate = Date()

    var body: some View {
        ZStack {
            if let viewModel = viewModel {
                if viewModel.isLoading && viewModel.events.isEmpty {
                    FullScreenLoading()
                } else if let errorMessage = viewModel.errorMessage, viewModel.events.isEmpty {
                    ErrorState(message: errorMessage) {
                        viewModel.loadEvents()
                    }
                } else if !viewModel.events.isEmpty {
                    VStack(spacing: 0) {
                        ScrollView {
                            VStack(spacing: 20) {
                                // Header
                                HStack {
                                    Text("Calendar")
                                        .font(.appTitle1)

                                    Spacer()
                                }
                                .padding(.horizontal, 24)

                                // Calendar
                                DatePicker(
                                    "",
                                    selection: $selectedDate,
                                    displayedComponents: .date
                                )
                                .datePickerStyle(.graphical)
                                .padding(.horizontal, 24)
                                .background(AppTheme.shared.colorScheme.cardBackground)
                                .cornerRadius(16)

                                // Events for selected date
                                VStack(alignment: .leading, spacing: 12) {
                                    Text("Events")
                                        .font(.appHeadline)
                                        .foregroundColor(AppTheme.shared.colorScheme.textPrimary)
                                        .padding(.horizontal, 24)

                                    let todaysEvents = viewModel.eventsForDate(selectedDate)

                                    if todaysEvents.isEmpty {
                                        Text("No events scheduled")
                                            .font(.appSubheadline)
                                            .foregroundColor(AppTheme.shared.colorScheme.textSecondary)
                                            .frame(maxWidth: .infinity)
                                            .padding(.vertical, 40)
                                    } else {
                                        ForEach(todaysEvents) { event in
                                            EventCard(event: event) {
                                                viewModel.toggleEventCompletion(event)
                                            }
                                            .padding(.horizontal, 24)
                                        }
                                    }
                                }

                                Spacer().frame(height: 100)
                            }
                            .padding(.top, 20)
                        }

                        AppTabBar(selectedTab: .constant(.calendar))
                    }
                }
            }
        }
        .task {
            if viewModel == nil {
                viewModel = CalendarViewModel()
            }
            viewModel?.loadEvents()
        }
    }
}

// MARK: - Event Card
struct EventCard: View {
    let event: CalendarEvent
    let onTap: () -> Void

    var body: some View {
        HStack(spacing: 12) {
            Text(event.icon)
                .font(.system(size: 32))

            VStack(alignment: .leading, spacing: 4) {
                Text(event.title)
                    .font(.appBodyMedium)
                    .foregroundColor(AppTheme.shared.colorScheme.textPrimary)

                if let description = event.description {
                    Text(description)
                        .font(.appFootnote)
                        .foregroundColor(AppTheme.shared.colorScheme.textSecondary)
                }

                Text(eventTime)
                    .font(.appFootnote)
                    .foregroundColor(AppTheme.shared.colorScheme.textTertiary)
            }

            Spacer()

            Button(action: onTap) {
                Image(systemName: event.isCompleted ? "checkmark.circle.fill" : "circle")
                    .font(.system(size: 24))
                    .foregroundColor(event.isCompleted ? AppColors.success : AppTheme.shared.colorScheme.border)
            }
        }
        .padding(16)
        .background(AppTheme.shared.colorScheme.cardBackground)
        .cornerRadius(16)
    }

    private var eventTime: String {
        let formatter = DateFormatter()
        formatter.timeStyle = .short
        let startTime = formatter.string(from: event.startDate)
        let endTime = formatter.string(from: event.endDate)
        return "\(startTime) - \(endTime)"
    }
}

#Preview {
    NavigationStack {
        CalendarView()
    }
}
