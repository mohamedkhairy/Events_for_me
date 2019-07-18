package khairy.com.eventsforme.extensions

import arrow.core.Either
import arrow.core.Try
import kotlinx.coroutines.Deferred

suspend fun <T> callAPI(f: suspend () -> Deferred<T?>): Either<Result.Error, Result.Success<T?>> =
    Try { f().await() }.fold({ t ->
        t.printStackTrace()
        return@fold Either.left(Result.Error(Errors.UnknownError))

    }, { r: T? ->
        return@fold if (r.isNotNull()) Either.right(Result.Success(r))
        else Either.left(Result.Error(Errors.UnknownError))
    })


sealed class Result {
    data class Success<T>(val t: T) : Result()
    data class Error(val error: Errors = Errors.UnknownError) : Result()
}

sealed class Errors {
    object UnknownError : Errors()
}

fun Any?.isNotNull(): Boolean =
    this != null

fun Any?.isNull(): Boolean =
    this == null

fun List<*>?.isNotNullOrEmpty(): Boolean =
    this != null && this.isNotEmpty()


