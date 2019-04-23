package com.tans.tweather2.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.tans.tweather2.viewmodel.TWeatherViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import kotlin.random.Random

abstract class BaseActivity<VM : BaseViewModel<OutputState, Input>, VDB : ViewDataBinding, OutputState, Input>(viewModelClazz: Class<VM>)

    : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: TWeatherViewModelFactory

    val viewModel: VM by lazy { ViewModelProviders.of(this, viewModelFactory).get(viewModelClazz) }

    private val outputCompositeDisposable: CompositeDisposable = CompositeDisposable()

    private val inputCompositeDisposable: CompositeDisposable = CompositeDisposable()

    private val resultSubject = PublishSubject.create<ActivityResult>()

    lateinit var viewDataBinding: VDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId())
        viewModel.outputStateInitLoad()
        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        outputCompositeDisposable.dispose()
        clearInputStreams()
    }

    abstract fun layoutId(): Int

    abstract fun init()

    fun startActivityForResult(intent: Intent): Maybe<Intent> {
        val requestCode: Int = Random.nextInt(1, 100)
        startActivityForResult(intent, requestCode)
        return resultSubject.filter { it.first == requestCode }
                .firstElement()
                .map { it.second }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            resultSubject.onNext(requestCode to data)
        }
    }

    fun <T> subScribeState(mapper: (OutputState) -> T,
                           handle: (T) -> Unit) {
        outputCompositeDisposable.add(viewModel.bindOutputState()
                .map { mapper(it) }
                .distinctUntilChanged()
                .doOnNext { handle(it) }
                .subscribe({ Log.d(this::class.java.simpleName, "Output State: $it") },
                        {
                            Log.e(this::class.java.simpleName, "Output State Error: $it")
                        }, {}))
    }

    fun <T> Observable<T>.bindActivityLife() {
        outputCompositeDisposable.add(this.subscribe({ Log.d(this::class.java.simpleName, "Next: ${it.toString()}") },
                {  Log.e(this::class.java.simpleName, it.toString()) },
                { Log.d(this::class.java.simpleName, "Complete") }))
    }

    fun Completable.bindActivityLife() {
        outputCompositeDisposable.add(this.subscribe(
                { Log.d(this::class.java.simpleName, "Complete") },
                {  Log.e(this::class.java.simpleName, it.toString()) }))
    }

    fun <T> Observable<T>.bindInputLifecycle() {
        inputCompositeDisposable.add(this.subscribe({ Log.d(this::class.java.simpleName, "Next: ${it.toString()}") },
                {  Log.e(this::class.java.simpleName, it.toString()) },
                { Log.d(this::class.java.simpleName, "Complete") }))
    }

    fun Completable.bindInputLifecycle() {
        inputCompositeDisposable.add(this.subscribe(
                { Log.d(this::class.java.simpleName, "Complete") },
                {  Log.e(this::class.java.simpleName, it.toString()) }))
    }

    fun clearInputStreams() {
        inputCompositeDisposable.clear()
    }

}

private typealias ActivityResult = Pair<Int, Intent>