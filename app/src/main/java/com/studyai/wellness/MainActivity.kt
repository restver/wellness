package com.studyai.wellness

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.studyai.wellness.data.repository.UserRepository
import com.studyai.wellness.navigation.AppNavigation
import com.studyai.wellness.navigation.AuthScreen
import com.studyai.wellness.ui.theme.WellnessAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 让内容延伸到系统栏下方
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent {
            WellnessApp {
                AppRoot(userRepository = userRepository)
            }
        }
    }
}

@Composable
fun AppRoot(userRepository: UserRepository) {
    val navController = rememberNavController()
    val startDestination = remember { AuthScreen.Login.route }

    AppNavigation(
        navController = navController,
        startDestination = startDestination
    )
}

@Composable
fun WellnessApp(content: @Composable () -> Unit) {
    WellnessAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            content()
        }
    }
}
