package com.tans.tweather2.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.tans.tweather2.viewmodel.TWeatherViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Maybe
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import kotlin.random.Random

abstract class BaseActivity<VM : BaseViewModel<OutputState, Input>, VDB : ViewDataBinding, OutputState, Input>(viewModelClazz: Class<VM>)

    : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: TWeatherViewModelFactory

    val viewModel: VM by lazy { ViewModelProviders.of(this, viewModelFactory).get(viewModelClazz) }

    private val outputDisposable: CompositeDisposable = CompositeDisposable()

    private val resultSubject = PublishSubject.create<Intent>()

    lateinit var viewDataBinding: VDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId())
        viewModel.outputStateInitLoad()
        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        outputDisposable.dispose()
    }

    abstract fun layoutId(): Int

    abstract fun init()

    fun startActivityForResult(intent: Intent): Maybe<Intent> {
        val requestCode: Int = Random.nextInt(1, 100)
        startActivityForResult(intent, requestCode)
        return resultSubject.firstElement()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            resultSubject.onNext(data)
        }
    }

    fun <T> subScribeState(mapper: (OutputState) -> T,
                           handle: (T) -> Unit) {
        outputDisposable.add(viewModel.bindOutputState()
                .map { mapper(it) }
                .distinctUntilChanged()
                .doOnNext { handle(it) }
                .subscribe({ Log.d(this::class.java.simpleName, "Output State: $it") },
                        {
                            Log.e(this::class.java.simpleName, "Output State Error: $it")
                        }, {}))
    }

}