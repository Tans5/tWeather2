package com.tans.tweather2.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.tans.tweather2.viewmodel.TWeatherViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Maybe
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject
import kotlin.random.Random

abstract class BaseActivity<VM : BaseViewModel<OutputState, Input>, VDB : ViewDataBinding, OutputState, Input>(viewModelClazz: Class<VM>)

    : DaggerAppCompatActivity(),
        BindLife,
        ViewModelSubscriber,
        DialogOwner{

    @Inject
    lateinit var viewModelFactory: TWeatherViewModelFactory

    val viewModel: VM by lazy { ViewModelProviders.of(this, viewModelFactory).get(viewModelClazz) }

    override val outputCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override val inputCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override val lifeCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override val context: Context
        get() = this

    override val showingDialogs: MutableMap<String, AlertDialog> = HashMap()

    override val dialogEvents: Subject<DialogEvent> = PublishSubject.create<DialogEvent>().toSerialized()

    private val resultSubject = PublishSubject.create<ActivityResult>().toSerialized()

    lateinit var viewDataBinding: VDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId())
        viewModel.outputStateInitLoad()
        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        outputCompositeDisposable.clear()
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
                .doOnNext { handle(it) }
                .bindOutputLifecycle()
    }

}

private typealias ActivityResult = Pair<Int, Intent>