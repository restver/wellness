package com.studyai.wellness.data.model

sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val exception: Throwable, val message: String? = null) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}

inline fun <T> resourceFrom(block: () -> T): Resource<T> = try {
    Resource.Success(block())
} catch (e: Exception) {
    Resource.Error(e)
}
