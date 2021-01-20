package com.xapptree.wheather.presentation.modules.weather

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xapptree.wheather.R
import com.xapptree.wheather.platform.room.City
import com.xapptree.wheather.presentation.sharedpref.SharedPrefConstants
import com.xapptree.wheather.presentation.sharedpref.SharedPrefHelper
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), CityAdapter.ICityAdapterClickListener {
    private lateinit var adapter: CityAdapter
    private lateinit var viewModel: MainViewModel
    private var listener: HomeFragmentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeFragmentListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement HomeFragmentListener")
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
            ViewModelProvider(this)[MainViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        app_toolbar.inflateMenu(R.menu.menu_weather)
        rv_city_list?.layoutManager = LinearLayoutManager(activity)

        app_toolbar?.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener,
            androidx.appcompat.widget.Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.add_location -> {
                        Toast.makeText(activity!!, "Clicked", Toast.LENGTH_SHORT).show()
                    }
                    R.id.metric -> {
                        SharedPrefHelper(activity!!).saveString(SharedPrefConstants.UNITS, "metric")
                        showMessage(getString(R.string.configuration_updated))
                    }
                    R.id.imperial -> {
                        SharedPrefHelper(activity!!).saveString(
                            SharedPrefConstants.UNITS,
                            "imperial"
                        )
                        showMessage(getString(R.string.configuration_updated))
                    }
                    R.id.standard -> {
                        SharedPrefHelper(activity!!).saveString(
                            SharedPrefConstants.UNITS,
                            "standard"
                        )
                        showMessage(getString(R.string.configuration_updated))
                    }
                    R.id.clear_locations -> {
                        viewModel.removeAllCities(activity!!)
                    }
                }
                return false
            }

        })
        viewModel.getCities(activity!!)?.observe(activity!!, Observer {

            if (!it.isNullOrEmpty()) {
                tv_helper_text?.visibility = View.GONE
                loadAdapter(it as ArrayList<City>)
            } else {
                //No Data found
                tv_helper_text?.visibility = View.VISIBLE
                loadAdapter(it as ArrayList<City>)
            }
        })

        viewModel.onTodayForecastAPISuccess()?.observe(activity!!, {
            //Api Response here
            hideLoader()
            listener?.navigateToCityScreen()
        })
        viewModel.onTodayForecastAPIError()?.observe(activity!!, {
            //Api Error here
            hideLoader()
            showMessage("Api Error")
        })
        viewModel.onDomainError()?.observe(activity!!, {
            //Domain Error here
            hideLoader()
            /*Show Toast or dialog
            * Based on error code dispplay error message configured in app */
            showMessage(getString(R.string.domain_error))

        })
    }

    private fun loadAdapter(cities: ArrayList<City>) {
        /*Bind data to adapter*/
        adapter = CityAdapter(cities, this)
        rv_city_list?.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onItemClick(data: City) {
        showLoader()
        viewModel.getTodayForecast(activity!!, data)
    }

    override fun onDeleteIconClicked(data: City) {
        viewModel.removeCity(activity!!, data)
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

    interface HomeFragmentListener {
        fun navigateToCityScreen()
    }

}