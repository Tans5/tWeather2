package com.tans.tweather2.core

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

interface InputOwner {

    val inputCompositeDisposable: CompositeDisposable

    fun <T> Observable<T>.bindInputLife() {
        inputCompositeDisposable.add(this.subscribe({
            Timber.d("Next: ${it.toString()}")
        }, {
            Timber.e(it)
        }, {
            Timber.d("Complete")
        }))
    }

    fun Completable.bindInputLife() {
        inputCompositeDisposable.add(this.subscribe({
            Timber.d("Complete")
        }, {
            Timber.e(it)
        }))
    }

    fun <T> Single<T>.bindInputLife() {
        inputCompositeDisposable.add(this.subscribe({
            Timber.d(it.toString())
        }, {
            Timber.e(it)
        }))
    }

    fun <T> Maybe<T>.bindInputLife() {
        inputCompositeDisposable.add(this.subscribe ({
            Timber.d("Success: $it")
        }, {
            Timber.e(it)
        }, {
            Timber.d( "Complete")
        }))
    }

    fun clear() {
        inputCompositeDisposable.clear()
    }

}