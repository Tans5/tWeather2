package com.tans.tweather2.ui.main

import android.arch.lifecycle.ViewModel
import com.tans.tweather2.repository.Repository
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel()