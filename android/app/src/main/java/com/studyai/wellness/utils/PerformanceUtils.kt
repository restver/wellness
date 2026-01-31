package com.studyai.wellness.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

// Throttle utility for button clicks and scroll events
@Composable
fun throttle(
    throttleMillis: Long = 1000L,
    onClick: () -> Unit
): () -> Unit {
    var lastClickTime by remember { mutableStateOf(0L) }

    return remember {
        {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime >= throttleMillis) {
                lastClickTime = currentTime
                onClick()
            }
        }
    }
}

// Memoization utility for expensive computations
@Composable
fun <T> rememberWithExpensiveComputation(
    key: Any?,
    computation: () -> T
): T {
    return remember(key) { computation() }
}

// Lazy loading modifier for images
fun Modifier.lazyLoadPlaceholder(): Modifier = this

// Performance monitoring
object PerformanceMonitor {
    fun measureTime(tag: String, block: () -> Unit) {
        val startTime = System.nanoTime()
        block()
        val endTime = System.nanoTime()
        val duration = (endTime - startTime) / 1_000_000
        android.util.Log.d("PerformanceMonitor", "$tag took ${duration}ms")
    }

    inline fun <T> measureTimeResult(tag: String, crossinline block: () -> T): T {
        val startTime = System.nanoTime()
        val result = block()
        val endTime = System.nanoTime()
        val duration = (endTime - startTime) / 1_000_000
        android.util.Log.d("PerformanceMonitor", "$tag took ${duration}ms")
        return result
    }
}

// Cache management
object CacheManager {
    private val cache = mutableMapOf<String, Any>()
    private val timestamps = mutableMapOf<String, Long>()

    fun <T> get(key: String, maxAgeMillis: Long = 5 * 60 * 1000): T? {
        val timestamp = timestamps[key] ?: return null
        if (System.currentTimeMillis() - timestamp > maxAgeMillis) {
            remove(key)
            return null
        }
        @Suppress("UNCHECKED_CAST")
        return cache[key] as? T
    }

    fun put(key: String, value: Any) {
        cache[key] = value
        timestamps[key] = System.currentTimeMillis()
    }

    fun remove(key: String) {
        cache.remove(key)
        timestamps.remove(key)
    }

    fun clear() {
        cache.clear()
        timestamps.clear()
    }
}

// List pagination helper
class ListPaginator<T>(
    private val pageSize: Int = 20,
    private val onLoadMore: suspend (Int) -> List<T>
) {
    private var currentPage = 0
    private val allItems = mutableListOf<T>()
    private var hasMore = true
    private var isLoading = false

    suspend fun loadMore(): List<T> {
        if (isLoading || !hasMore) return allItems

        isLoading = true
        val newItems = onLoadMore(currentPage)
        allItems.addAll(newItems)
        hasMore = newItems.size >= pageSize
        currentPage++
        isLoading = false

        return allItems
    }

    fun reset() {
        currentPage = 0
        allItems.clear()
        hasMore = true
        isLoading = false
    }

    fun getItems() = allItems.toList()

    fun isLoadingMore() = isLoading

    fun canLoadMore() = hasMore
}
