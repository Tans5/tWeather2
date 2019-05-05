package com.tans.tweather2.ui.main

import android.os.Bundle
import com.tans.tweather2.R
import com.tans.tweather2.databinding.ActivityMainBinding
import com.tans.tweather2.ui.BaseActivity

class MainActivity
    : BaseActivity<MainViewModel, ActivityMainBinding,
        MainOutputState, MainInput>(MainViewModel::class.java) {

    override fun layoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(0, 0)
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        subScribeState({ it.msg }) {
            viewDataBinding.msgTv.text = it
        }
    }


}
