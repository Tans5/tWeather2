package com.tans.tweather2.ui.cities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tans.tweather2.R
import com.tans.tweather2.databinding.LayoutItemCityBinding
import com.tans.tweather2.entites.City
import com.tans.tweather2.ui.BaseRecyclerViewAdapter

class CitiesAdapter constructor(private val itemClickCall: (City) -> Unit)  : BaseRecyclerViewAdapter<City, CitiesViewHolder>(differCallBack = object : DiffUtil.ItemCallback<City>() {

    override fun areItemsTheSame(oldItem: City, newItem: City): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: City, newItem: City): Boolean = oldItem == newItem

}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : CitiesViewHolder  = CitiesViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.layout_item_city, parent, false))

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        holder.dataBinding.cityNameTv.text = getItem(position).cityName
        holder.dataBinding.cityItemRootLayout.setOnClickListener { itemClickCall(getItem(position)) }
    }

}

class CitiesViewHolder(val dataBinding: LayoutItemCityBinding) : RecyclerView.ViewHolder(dataBinding.root)