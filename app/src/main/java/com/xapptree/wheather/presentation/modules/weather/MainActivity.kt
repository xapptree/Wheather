package com.xapptree.wheather.presentation.modules.weather

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.xapptree.wheather.R
import com.xapptree.wheather.platform.room.City

class MainActivity : AppCompatActivity(), HomeFragment.HomeFragmentListener ,
    CityFragment.CityFragmentListener {
    enum class Fragments(var title: String){
        HOME_FRAGMENT("HomeFragment"),
        CITY_FRAGMENT("CityFragment")
    }
    private lateinit var viewModel: MainViewModel
    private lateinit var dummyLocations:ArrayList<City>
     lateinit var  wifiManager :WifiManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("TAG LOG-MainActivity", "OnCreate")
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        createDummyData()
        viewModel.addCities(this, dummyLocations)
        addFragment(HomeFragment(), R.id.fragment_container, Fragments.HOME_FRAGMENT.title)
    }

    override fun onStart() {
        super.onStart()
        Log.i("TAG LOG-MainActivity", "OnStart")
    }
    override fun onResume() {
        super.onResume()
        Log.i("TAG LOG-MainActivity", "OnResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("TAG LOG-MainActivity", "OnPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("TAG LOG-MainActivity", "OnStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("TAG LOG-MainActivity", "OnDestroy")
    }

    private fun createDummyData() {
        dummyLocations = ArrayList()
        dummyLocations.add(City("Hyderabad","",""))
        dummyLocations.add(City("Chennai","",""))
        dummyLocations.add(City("Bangalore","",""))
        dummyLocations.add(City("Mumbai","",""))
        dummyLocations.add(City("Delhi","",""))
        dummyLocations.add(City("Vizag","",""))
        dummyLocations.add(City("Shimla","",""))
        dummyLocations.add(City("Ooty","",""))
        dummyLocations.add(City("Vijayawada","",""))
        dummyLocations.add(City("Ongole","",""))


        val wifiScanReceiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                val success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
                if (success) {
                    scanSuccess()
                } else {
                    scanFailure()
                }
            }
        }

        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        this.registerReceiver(wifiScanReceiver, intentFilter)

        val success = wifiManager.startScan()
        if (!success) {
            // scan failure handling
            scanFailure()
        }
    }

    /*Below functions should need to place in base class*/
    private fun addFragment(
        fragment: Fragment,
        containerId: Int,
        name: String,
        replace: Boolean = false,
        hideCurrent: Boolean = false,
        addBackStack: Boolean = true
    ) {

        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        if (replace) {
            fragmentTransaction.replace(containerId, fragment, name)
        } else {
            if (hideCurrent) {
                val visibleFragment: Fragment? = getVisibleFragment()
                if (visibleFragment != null) {
                    fragmentTransaction.hide(visibleFragment)
                }
            }
            fragmentTransaction.add(containerId, fragment, name)
        }
        if (addBackStack) {
            fragmentTransaction.addToBackStack(name)
        }
        fragmentTransaction.commitAllowingStateLoss()
    }

    private fun getVisibleFragment(): Fragment? {
        val fragmentManager = supportFragmentManager
        val fragments = fragmentManager.fragments
        for (fragment in fragments) {
            if (fragment != null && fragment.isVisible) {
                return fragment
            }
        }
        return null
    }

    override fun navigateToCityScreen() {
        addFragment(CityFragment(), R.id.fragment_container, Fragments.CITY_FRAGMENT.title)
    }

    private fun handleBackPress() {
        if (supportFragmentManager.fragments.size == 1) {
            finish()
        } else if (supportFragmentManager.fragments.size > 0) {
            supportFragmentManager.popBackStack()
        }
    }

    override fun onBackPressed() {
        Log.i("TAG LOG-MainActivity", "OnBackPressed")
        handleBackPress()
    }

    override fun cityFragmentBackClicked() {
        handleBackPress()
    }

    private fun scanSuccess() {
        val results = wifiManager.scanResults
        Log.i("TTT", results.toString())
    }

    private fun scanFailure() {
        // handle failure: new scan did NOT succeed
        // consider using old scan results: these are the OLD results!
        val results = wifiManager.scanResults
        Log.i("TTT", results.toString())
    }
}