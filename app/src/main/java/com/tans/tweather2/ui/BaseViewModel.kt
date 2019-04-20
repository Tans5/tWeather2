package com.tans.tweather2.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

abstract class BaseViewModel<OutputState, Input>(defaultState: OutputState) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val inputCompositeDisposable: CompositeDisposable = CompositeDisposable()

    private val output: BehaviorSubject<OutputState> = BehaviorSubject.createDefault<OutputState>(defaultState)

    var input: Input? = null
    set(value) {
        clearInput()
        field = value
        inputUpdate(value)
    }

    abstract fun inputUpdate(input: Input?)

    abstract fun outputStateInitLoad()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        clearInput()
    }

    fun <T> Single<T>.bindViewModelLife() {
        compositeDisposable.add(this.subscribe({ Log.d(this@BaseViewModel::class.java.simpleName, it.toString()) },
                { Log.e(this@BaseViewModel::class.java.simpleName, it.toString()) }))
    }

    fun <T> Observable<T>.bindViewModelLife() {
        compositeDisposable.add(this.subscribe({ Log.d(this@BaseViewModel::class.java.simpleName, "Next: ${it.toString()}") },
                {  Log.e(this@BaseViewModel::class.java.simpleName, it.toString()) },
                { Log.d(this@BaseViewModel::class.java.simpleName, "Complete") }))
    }

    fun <T> Maybe<T>.bindViewModelLife() {
        compositeDisposable.add(this.subscribe ({ Log.d(this@BaseViewModel::class.java.simpleName, "Success: ${it.toString()}")  },
                { Log.e(this@BaseViewModel::class.java.simpleName, "Error: ${it.toString()}") },
                { Log.d(this@BaseViewModel::class.java.simpleName, "Complete") }))
    }

    fun <T> Observable<T>.bindInputLifecycle() {
        inputCompositeDisposable.add(this.subscribe({ Log.d(this@BaseViewModel::class.java.simpleName, "Next: ${it.toString()}") },
                {  Log.e(this@BaseViewModel::class.java.simpleName, it.toString()) },
                { Log.d(this@BaseViewModel::class.java.simpleName, "Complete") }))
    }

    fun bindOutputState(): Observable<OutputState> = output

    protected fun updateOutputState(dealer: (OutputState) -> OutputState): Completable = Completable.fromAction {
        output.onNext(dealer(output.value!!))
    }

    private fun clearInput() {
        inputCompositeDisposable.clear()
    }

}