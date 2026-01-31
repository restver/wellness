package com.studyai.wellness.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.studyai.wellness.ui.screens.ForgotPasswordScreen
import com.studyai.wellness.ui.screens.LoginScreen
import com.studyai.wellness.viewmodels.LoginViewModel

// 认证相关的路由
sealed class AuthScreen(val route: String) {
    object Login : AuthScreen("login")
    object ForgotPassword : AuthScreen("forgot_password")
}

// 主应用 Tab 路由
sealed class TabRoute(val route: String, val title: String) {
    object Dashboard : TabRoute("dashboard_tab", "Home")
    object Calendar : TabRoute("calendar_tab", "Calendar")
    object Stats : TabRoute("stats_tab", "Stats")
    object Profile : TabRoute("profile_tab", "Profile")
    object Settings : TabRoute("settings_tab", "Settings")

    companion object {
        val tabs = listOf(Dashboard, Calendar, Stats, Profile, Settings)
    }
}

// Dashboard Tab 内的子路由
sealed class DashboardRoute(val route: String) {
    object Home : DashboardRoute("dashboard_home")
    object Detail : DashboardRoute("dashboard_detail/{id}") {
        fun createRoute(id: String) = "dashboard_detail/$id"
    }
}

// Calendar Tab 内的子路由
sealed class CalendarRoute(val route: String) {
    object Home : CalendarRoute("calendar_home")
    object EventDetail : CalendarRoute("calendar_event_detail/{eventId}") {
        fun createRoute(eventId: String) = "calendar_event_detail/$eventId"
    }
}

// Stats Tab 内的子路由
sealed class StatsRoute(val route: String) {
    object Home : StatsRoute("stats_home")
    object Details : StatsRoute("stats_details")
}

// Profile Tab 内的子路由
sealed class ProfileRoute(val route: String) {
    object Home : ProfileRoute("profile_home")
    object Edit : ProfileRoute("profile_edit")
}

// Settings Tab 内的子路由
sealed class SettingsRoute(val route: String) {
    object Home : SettingsRoute("settings_home")
    object About : SettingsRoute("settings_about")
}

/**
 * 主应用导航入口
 * 处理登录相关流程，登录成功后进入 MainScreen（带 TabBar 的主界面）
 */
@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String = AuthScreen.Login.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // 登录页面
        composable(AuthScreen.Login.route) {
            LoginScreen(
                viewModel = hiltViewModel(),
                onNavigateToDashboard = {
                    // 登录成功，清空返回栈并进入主界面
                    navController.navigate("main") {
                        popUpTo(AuthScreen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToForgotPassword = {
                    navController.navigate(AuthScreen.ForgotPassword.route)
                },
                onNavigateToSignUp = {
                    android.widget.Toast.makeText(
                        navController.context,
                        "Sign up feature coming soon!",
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }

        // 忘记密码页面
        composable(AuthScreen.ForgotPassword.route) {
            ForgotPasswordScreen(
                viewModel = hiltViewModel(),
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // 主应用界面（包含 TabBar 和所有 Tab 的子导航）
        composable("main") {
            MainScreen(
                onLogout = {
                    navController.navigate(AuthScreen.Login.route) {
                        popUpTo("main") { inclusive = true }
                    }
                }
            )
        }
    }
}
