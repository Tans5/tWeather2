package com.tans.tweather2.ui

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

interface BindLife {

    val lifeCompositeDisposable: CompositeDisposable

    fun <T> Observable<T>.bindLife() {
        lifeCompositeDisposable.add(this.subscribe({
            Timber.d("Next: ${it.toString()}")
        }, {
            Timber.e(it)
        }, {
            Timber.d("Complete")
        }))
    }

    fun Completable.bindLife() {
        lifeCompositeDisposable.add(this.subscribe({
            Timber.d("Complete")
        }, {
            Timber.e(it)
        }))
    }

    fun <T> Single<T>.bindLife() {
        lifeCompositeDisposable.add(this.subscribe({
            Timber.d(it.toString())
        }, {
            Timber.e(it)
        }))
    }

    fun <T> Maybe<T>.bindLife() {
        lifeCompositeDisposable.add(this.subscribe ({
            Timber.d("Success: $it")
        }, {
            Timber.e(it)
        }, {
            Timber.d( "Complete")
        }))
    }

}