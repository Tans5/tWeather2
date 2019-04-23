package com.tans.tweather2.ui

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<D, VH: RecyclerView.ViewHolder>(differCallBack: DiffUtil.ItemCallback<D>) :
        ListAdapter<D,VH>(AsyncDifferConfig.Builder<D>(differCallBack)
                .build())