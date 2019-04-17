package com.tans.tweather2.ui.main

import android.os.Bundle
import com.tans.tweather2.R
import com.tans.tweather2.db.CityDao
import com.tans.tweather2.entites.City
import com.tans.tweather2.ui.BaseActivity
import com.tans.tweather2.utils.switchThread
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivity : BaseActivity<MainViewModel>(MainViewModel::class.java) {

    @Inject lateinit var cityDao: CityDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // viewModel.testCities()
        cityDao.queryRootCites()
                .switchThread()
                .doOnSuccess {
                    println(it)
                }
                .subscribe()
    }
}
