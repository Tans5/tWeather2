package com.tans.tweather2.ui

import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

abstract class BaseViewModel<OutputState, Input>(defaultState: OutputState) : ViewModel(), BindLife {

    private val output = BehaviorSubject.createDefault<OutputState>(defaultState).toSerialized()

    override val lifeCompositeDisposable: CompositeDisposable = CompositeDisposable()

    fun setInput(input: Input?, subscriber: ViewModelSubscriber) {
        subscriber.inputCompositeDisposable.clear()
        inputUpdate(input, subscriber)
        val outputInitWithDialog = if (subscriber is DialogOwner) {
            subscriber.outputStateInitWithDialog()
        } else {
            Completable.complete()
        }
        subscriber.apply {
            outputInitWithDialog.bindInputLifecycle()
        }
    }

    abstract fun inputUpdate(input: Input?, subscriber: ViewModelSubscriber)

    abstract fun outputStateInitLoad()

    open fun DialogOwner.outputStateInitWithDialog(): Completable = Completable.complete()

    override fun onCleared() {
        super.onCleared()
        lifeCompositeDisposable.clear()
    }

    fun bindOutputState(): Observable<OutputState> = output

    protected fun updateOutputState(dealer: (OutputState) -> OutputState): Completable = Completable.fromAction {
        output.onNext(dealer(output.blockingFirst()))
    }

}