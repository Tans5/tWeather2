package com.tans.tweather2.utils

import arrow.core.Either
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

fun <T> Single<T>.switchThread(): Single<T> = this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.switchThread(): Observable<T> = this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Maybe<T>.switchThread(): Maybe<T> = this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.toEither()
        : Single<Either<Throwable, T>> = this.map<Either<Throwable, T>> { Either.right(it) }
        .onErrorResumeNext { Single.just(Either.left(it)) }

fun <T> callToObservable(): Pair<Observable<T>, (T) -> Unit> {
    val obs = PublishSubject.create<T>().toSerialized()
    val call: (T) -> Unit = { obs.onNext(it) }
    return obs to call
}