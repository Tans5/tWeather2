package com.tans.tweather2.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun <T> Single<T>.bindViewModelLife() {
        compositeDisposable.add(this.subscribe({}, { Log.e(this@BaseViewModel::class.java.simpleName, it.toString())}))
    }

}