package com.xapptree.wheather.presentation.modules.iot

import android.content.Context
import android.net.wifi.ScanResult
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xapptree.wheather.R
import kotlinx.android.synthetic.main.config_wifi_fragment.*


class ConfigWifiFragment : Fragment(), WifiNetworkAdapter.IAdapterClickListener,
    View.OnClickListener {
    private lateinit var adapter: WifiNetworkAdapter
    private lateinit var viewModel: SmartHomeViewModel
    private var listener: ConfigWifiFragmentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ConfigWifiFragmentListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement ConfigWifiFragmentListener")
        }

    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //   viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel = activity?.run {
            ViewModelProvider(this)[SmartHomeViewModel::class.java]
        } ?: throw Exception("Invalid Activity")


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.config_wifi_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_device_list?.layoutManager = LinearLayoutManager(activity)
        btn_setup.setOnClickListener(this)

        viewModel.getSSIDs()?.observe(activity!!, Observer {

            if (!it.isNullOrEmpty()) {
                tv_helper_text?.visibility = View.GONE
                val filterData = it.filter { s -> !s.SSID.contains("Smart") }
                loadAdapter(it as ArrayList<ScanResult>)
            } else {
                //No Data found
                tv_helper_text?.visibility = View.VISIBLE
                loadAdapter(it as ArrayList<ScanResult>)
            }
        })
    }

    private fun loadAdapter(cities: ArrayList<ScanResult>) {
        /*Bind data to adapter*/
        adapter = WifiNetworkAdapter(cities, this)
        rv_device_list?.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onItemClick(data: ScanResult) {
        ll_setup.visibility = View.VISIBLE
        tv_wifi_name.text = data.SSID
    }

    override fun onDeleteIconClicked(data: ScanResult) {

    }

    private fun showMessage(msg: String) {
        Toast.makeText(activity!!, msg, Toast.LENGTH_SHORT).show()
    }

    private fun showLoader() {
        pb_loader.visibility = View.VISIBLE
    }

    private fun hideLoader() {
        pb_loader.visibility = View.GONE
    }

    interface ConfigWifiFragmentListener {
        fun setUpDevice(ssid: String, password: String)
    }

    override fun onClick(p0: View?) {
        if (et_password.text.toString().trim().isNotEmpty()) {
            listener?.setUpDevice(tv_wifi_name.text.toString(), et_password.text.toString())
        }
    }
}