package com.xapptree.wheather.presentation.modules.iot

import android.net.wifi.ScanResult
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.xapptree.wheather.R
import com.xapptree.wheather.platform.room.City
import kotlinx.android.synthetic.main.city_listitem.view.*

class WifiNetworkAdapter(
    var cityList: ArrayList<ScanResult>,
    private var listener: IAdapterClickListener
) :
    RecyclerView.Adapter<WifiNetworkAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title = view.name as TextView
        var container = view.container as ConstraintLayout
        var deleteIcon = view.ib_delete as ImageButton

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.wifi_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return cityList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = (cityList[position].SSID)
        holder.title.setOnClickListener {
            listener.onItemClick(cityList[position])
        }
        holder.deleteIcon.setOnClickListener {
            listener.onDeleteIconClicked(cityList[position])
        }
    }

    interface IAdapterClickListener {
        fun onItemClick(data: ScanResult)
        fun onDeleteIconClicked(data: ScanResult)
    }
}