package com.tans.tweather2.ui

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.tans.tweather2.core.BindLife
import com.tans.tweather2.core.InputOwner
import com.tans.tweather2.viewmodel.TWeatherViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import kotlin.random.Random

abstract class BaseActivity<VM : BaseViewModel<OutputState, Input>, VDB : ViewDataBinding, OutputState, Input>(viewModelClazz: Class<VM>)

    : DaggerAppCompatActivity(),
        BindLife,
        InputOwner {

    @Inject
    lateinit var viewModelFactory: TWeatherViewModelFactory

    val viewModel: VM by lazy {
        ViewModelProvider(this, viewModelFactory).get(viewModelClazz)
    }

    override val inputCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override val lifeCompositeDisposable: CompositeDisposable = CompositeDisposable()

    private val resultSubject = PublishSubject.create<ActivityResult>().toSerialized()

    lateinit var viewDataBinding: VDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId())
        viewModel.init()
        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        inputCompositeDisposable.clear()
        lifeCompositeDisposable.clear()
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
        viewModel.bindOutputState()
                .map { mapper(it) }
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { handle(it) }
                .bindLife()
    }

}

private typealias ActivityResult = Pair<Int, Intent>