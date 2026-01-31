package com.studyai.wellness.utils

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

// Lifecycle-aware effects
@Composable
fun OnLifecycleEvent(
    onEvent: (Lifecycle.Event) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val observer = remember {
        LifecycleEventObserver { _, event -> onEvent(event) }
    }

    DisposableEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

// One-time event handler for Navigation and Snackbars
class OneTimeEvent<T> {
    private val _channel = Channel<T>(Channel.BUFFERED)
    val channel = _channel

    fun send(event: T) {
        _channel.trySend(event)
    }

    fun asFlow(): Flow<T> = _channel.receiveAsFlow()
}

@Composable
fun <T> rememberOneTimeEvent() = remember { OneTimeEvent<T>() }

// State management utilities
@Composable
fun <T> rememberMutableState(
    initialValue: T
): androidx.compose.runtime.MutableState<T> {
    return remember { mutableStateOf(initialValue) }
}

// Responsive utilities
sealed class ScreenSize {
    data object Phone : ScreenSize()
    data object Tablet : ScreenSize()
    data object Desktop : ScreenSize()
}

@Composable
fun rememberScreenSize(): ScreenSize {
    val configuration = androidx.compose.ui.platform.LocalConfiguration.current
    return remember(configuration.screenWidthDp) {
        when {
            configuration.screenWidthDp < 600 -> ScreenSize.Phone
            configuration.screenWidthDp < 840 -> ScreenSize.Tablet
            else -> ScreenSize.Desktop
        }
    }
}
