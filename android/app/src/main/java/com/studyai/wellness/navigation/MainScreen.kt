package com.studyai.wellness.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.studyai.wellness.ui.components.AppBottomTabBar

/**
 * 主应用界面
 * 类似 iOS 的 UITabBarController，包含底部 TabBar 和多个独立的导航栈
 * 每个 Tab 都有自己的 NavHost，可以独立导航到子页面
 */
@Composable
fun MainScreen(
    onLogout: () -> Unit
) {
    // 保存当前选中的 Tab 状态
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

    // 每个 Tab 有自己的 NavController，实现独立的导航栈
    val dashboardNavController = rememberNavController()
    val calendarNavController = rememberNavController()
    val statsNavController = rememberNavController()
    val profileNavController = rememberNavController()
    val settingsNavController = rememberNavController()

    // 获取当前 Tab 的 NavController
    val currentNavController = when (selectedTabIndex) {
        0 -> dashboardNavController
        1 -> calendarNavController
        2 -> statsNavController
        3 -> profileNavController
        4 -> settingsNavController
        else -> dashboardNavController
    }

    // 当前 Tab 的路由（用于高亮显示）
    val currentRoute = when (selectedTabIndex) {
        0 -> "dashboard"
        1 -> "calendar"
        2 -> "stats"
        3 -> "profile"
        4 -> "settings"
        else -> "dashboard"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // 内容区域 - 根据选中的 Tab 显示对应的 NavHost
        // 添加 statusBarsPadding 避免内容被状态栏遮挡
        Box(modifier = Modifier.weight(1f)) {
            when (selectedTabIndex) {
                0 -> DashboardTabNavHost(
                    navController = dashboardNavController,
                    onLogout = onLogout
                )
                1 -> CalendarTabNavHost(
                    navController = calendarNavController
                )
                2 -> StatsTabNavHost(
                    navController = statsNavController
                )
                3 -> ProfileTabNavHost(
                    navController = profileNavController,
                    onLogout = onLogout
                )
                4 -> SettingsTabNavHost(
                    navController = settingsNavController
                )
            }
        }

        // 底部 TabBar - 始终显示
        AppBottomTabBar(
            currentRoute = currentRoute,
            onTabSelected = { route ->
                // 切换到对应的 Tab
                selectedTabIndex = when (route) {
                    "dashboard" -> 0
                    "calendar" -> 1
                    "stats" -> 2
                    "profile" -> 3
                    "settings" -> 4
                    else -> 0
                }
            }
        )
    }
}

/**
 * Dashboard Tab 的导航栈
 */
@Composable
private fun DashboardTabNavHost(
    navController: NavHostController,
    onLogout: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = DashboardRoute.Home.route
    ) {
        // Dashboard 主页
        composable(DashboardRoute.Home.route) {
            DashboardTabHomeScreen(
                navController = navController,
                onLogout = onLogout
            )
        }

        // Dashboard 详情页示例（可以导航到子页面）
        composable(
            route = DashboardRoute.Detail.route,
            arguments = listOf(androidx.navigation.navArgument("id") { type = androidx.navigation.NavType.StringType })
        ) {
            val id = it.arguments?.getString("id") ?: ""
            DashboardDetailScreen(
                itemId = id,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

/**
 * Calendar Tab 的导航栈
 */
@Composable
private fun CalendarTabNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = CalendarRoute.Home.route
    ) {
        composable(CalendarRoute.Home.route) {
            CalendarTabHomeScreen(
                navController = navController
            )
        }

        // 可以添加更多 Calendar 子页面
        composable(
            route = CalendarRoute.EventDetail.route,
            arguments = listOf(androidx.navigation.navArgument("eventId") { type = androidx.navigation.NavType.StringType })
        ) {
            CalendarEventDetailScreen(
                eventId = it.arguments?.getString("eventId") ?: "",
                onBack = { navController.popBackStack() }
            )
        }
    }
}

/**
 * Stats Tab 的导航栈
 */
@Composable
private fun StatsTabNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = StatsRoute.Home.route
    ) {
        composable(StatsRoute.Home.route) {
            StatsTabHomeScreen(
                navController = navController
            )
        }

        composable(StatsRoute.Details.route) {
            StatsDetailsScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}

/**
 * Profile Tab 的导航栈
 */
@Composable
private fun ProfileTabNavHost(
    navController: NavHostController,
    onLogout: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = ProfileRoute.Home.route
    ) {
        composable(ProfileRoute.Home.route) {
            ProfileTabHomeScreen(
                navController = navController,
                onLogout = onLogout
            )
        }

        composable(ProfileRoute.Edit.route) {
            ProfileEditScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}

/**
 * Settings Tab 的导航栈
 */
@Composable
private fun SettingsTabNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = SettingsRoute.Home.route
    ) {
        composable(SettingsRoute.Home.route) {
            SettingsTabHomeScreen(
                navController = navController
            )
        }

        composable(SettingsRoute.About.route) {
            AboutScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
