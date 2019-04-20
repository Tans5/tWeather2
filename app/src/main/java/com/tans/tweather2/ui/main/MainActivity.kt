package com.tans.tweather2.ui.main

import com.tans.tweather2.R
import com.tans.tweather2.databinding.ActivityMainBinding
import com.tans.tweather2.ui.BaseActivity

class MainActivity
    : BaseActivity<MainViewModel, ActivityMainBinding,
        MainOutputState, MainInput>(MainViewModel::class.java) {

    override fun layoutId(): Int = R.layout.activity_main

    override fun init() {
        subScribeState({ it.msg }) {
            viewDataBinding.msgTv.text = it
        }
    }


}
