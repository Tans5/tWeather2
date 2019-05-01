package com.tans.tweather2.ui

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

interface ViewModelSubscriber {

    val outputCompositeDisposable: CompositeDisposable

    val inputCompositeDisposable: CompositeDisposable

    fun <T> Observable<T>.bindOutputLifecycle() {
        outputCompositeDisposable.add(this.subscribe({
            Timber.d("Next: $it")
        }, {
            Timber.e(it)
        }, {
            Timber.d("Complete")
        }))
    }

    fun Completable.bindOutputLifecycle() {
        outputCompositeDisposable.add(this.subscribe({
            Timber.d("Complete")
        }, {
            Timber.e(it)
        }))
    }

    fun <T> Observable<T>.bindInputLifecycle() {
        inputCompositeDisposable.add(this.subscribe({
            Timber.d("Next: ${it.toString()}")
        }, {
            Timber.e(it)
        }, {
            Timber.d("Complete")
        }))
    }

    fun Completable.bindInputLifecycle() {
        inputCompositeDisposable.add(this.subscribe({
            Timber.d("Complete")
        }, {
            Timber.e(it)
        }))
    }
}