import SwiftUI

// MARK: - Root Content View
struct ContentView: View {
    var body: some View {
        if #available(iOS 18.0, *) {
            AppNavigation()
        } else {
            AppNavigationLegacy()
        }
    }
}

#Preview {
    ContentView()
}
