package com.dfarm.superheroes.RequestHandling

suspend fun <T: Any> handleRequest(requestFunc: suspend () -> T): Result<T> {
    return try {
        Result.success(requestFunc.invoke())
    } catch (he: Exception) {
        Result.failure(he)
    }
}