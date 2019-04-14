package com.tans.tweather2.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun <T> Single<T>.bindViewModelLife() {
        compositeDisposable.add(this.subscribe({ Log.d(this@BaseViewModel::class.java.simpleName, it.toString()) },
                { Log.e(this@BaseViewModel::class.java.simpleName, it.toString()) }))
    }

    fun <T> Observable<T>.binViewModelLife() {
        compositeDisposable.add(this.subscribe({ Log.d(this@BaseViewModel::class.java.simpleName, "Next: ${it.toString()}") },
                {  Log.e(this@BaseViewModel::class.java.simpleName, it.toString()) },
                { Log.d(this@BaseViewModel::class.java.simpleName, "Complete") }))
    }

    fun <T> Maybe<T>.bindViewModelLife() {
        compositeDisposable.add(this.subscribe ({ Log.d(this@BaseViewModel::class.java.simpleName, "Success: ${it.toString()}")  },
                { Log.e(this@BaseViewModel::class.java.simpleName, "Error: ${it.toString()}") },
                { Log.d(this@BaseViewModel::class.java.simpleName, "Complete") }))
    }

}