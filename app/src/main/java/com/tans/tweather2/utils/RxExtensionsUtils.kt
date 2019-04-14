package com.tans.tweather2.utils

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.switchThread(): Single<T> = this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.switchThread(): Observable<T> = this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Maybe<T>.switchThread(): Maybe<T> = this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.toEither()
        : Single<Either<Throwable, T>> = this.map<Either<Throwable, T>> { Either.right(it) }
        .onErrorResumeNext { Single.just(Either.left(it)) }

sealed class Either<out L, out R>() {

    abstract fun isRight(): Boolean

    abstract fun isLeft(): Boolean

    class Right<out T>(val data: T) : Either<Nothing, T>() {

        override fun isRight(): Boolean = true

        override fun isLeft(): Boolean = false
    }

    class Left<out T>(val data: T) : Either<T, Nothing>() {
        override fun isRight(): Boolean = false

        override fun isLeft(): Boolean = true

    }

    companion object {
        fun <T> left(data: T) = Left(data)

        fun <T> right(data: T) = Right(data)
    }
}