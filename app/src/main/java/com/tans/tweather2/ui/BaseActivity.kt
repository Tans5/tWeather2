package com.tans.tweather2.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.tans.tweather2.R
import com.tans.tweather2.databinding.ActivityMainBinding
import com.tans.tweather2.viewmodel.TWeatherViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

abstract class BaseActivity<VM : BaseViewModel<OutputState, Input>, VDB : ViewDataBinding, OutputState, Input>(viewModelClazz: Class<VM>)

    : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: TWeatherViewModelFactory

    val viewModel: VM by lazy { ViewModelProviders.of(this, viewModelFactory).get(viewModelClazz) }

    private val outputDisposable: CompositeDisposable = CompositeDisposable()

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