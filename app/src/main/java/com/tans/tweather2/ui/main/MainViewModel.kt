package com.tans.tweather2.ui.main

import android.arch.lifecycle.ViewModel
import com.tans.tweather2.entites.Atmosphere
import com.tans.tweather2.repository.Repository
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    fun fetchWeatherAtmosphere(city: String) {
        repository.getWeatherAtmosphere(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
               // .subscribe (Consumer{ println(it) })
                .subscribe(object : SingleObserver<Atmosphere> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onError(e: Throwable) {
                        println("onError: $e")
                    }

                    override fun onSuccess(v: Atmosphere) {
                        println("onSuccess: $v")
                    }
                })
    }
}
