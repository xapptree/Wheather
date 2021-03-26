package com.xapptree.wheather.presentation.modules.iot

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.net.wifi.ScanResult
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Bundle
import android.text.format.Formatter
import android.util.Log
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.xapptree.wheather.R
import kotlinx.android.synthetic.main.activity_smart_home_config.*


@Suppress("DEPRECATION")
class SmartHomeConfigActivity : AppCompatActivity(), ScanDeviceFragment.ScanDeviceFragmentListener,
    ConfigWifiFragment.ConfigWifiFragmentListener {
    private lateinit var viewModel: SmartHomeViewModel
    lateinit var wifiManager: WifiManager

    enum class Fragments(var title: String) {
        SCAN_FRAGMENT("ScanDeviceFragment"),
        CONFIG_WIFI_FRAGMENT("ConfigWifiFragment")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smart_home_config)

        viewModel = ViewModelProvider(this).get(SmartHomeViewModel::class.java)
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        setUpListener()
        startScan()
        setUpWebView()
        addFragment(ScanDeviceFragment(), R.id.fragment_container, Fragments.SCAN_FRAGMENT.title)
    }

    private fun setUpWebView() {
        wv_web.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                Log.i("TTT-shouldOverride", request?.url.toString())
                return false
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                Log.i("TTTPS", url!!)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                Log.i("TTTPF", url!!)
                //view?.loadUrl("javascript:console.log(document.body.getElementsByTagName('pre')[0].innerHTML);");
                view?.loadUrl("javascript:HtmlViewer.showHTML"+"(document.getElementsByTagName('pre')[0].innerHTML);")
            }
        }
        wv_web.settings.javaScriptEnabled = true
        wv_web.addJavascriptInterface(MyJavaScriptInterface(this), "HtmlViewer")
    }

    private fun startScan() {
        val success = wifiManager.startScan()
        if (!success) {
            // scan failure handling
            scanFailure()
        }
    }

    private fun setUpListener() {
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

        val wifiStateReceiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                val wifiInfo: WifiInfo = wifiManager.connectionInfo
                val ipAddress = wifiInfo.ipAddress
                val ip: String = Formatter.formatIpAddress(wifiManager.connectionInfo.ipAddress)
//                val ip = String.format(
//                    "%d.%d.%d.%d",
//                    ipAddress and 0xff,
//                    ipAddress shr 8 and 0xff,
//                    ipAddress shr 16 and 0xff,
//                    ipAddress shr 24 and 0xff
//                )
                Log.i("TTT", ip)

            }

        }

        val intentFilter2 = IntentFilter()
        intentFilter2.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION)
        this.registerReceiver(wifiStateReceiver, intentFilter2)
    }

    private fun scanSuccess() {
        val results = wifiManager.scanResults
        Log.i("TTT", results.toString())
        viewModel.addSSIDs(results)
    }

    private fun scanFailure() {
        // handle failure: new scan did NOT succeed
        // consider using old scan results: these are the OLD results!
        val results = wifiManager.scanResults
        Log.i("TTT", results.toString())
        viewModel.addSSIDs(results)
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

    override fun connectToNetwork(data: ScanResult) {
        val conf = WifiConfiguration()
        conf.SSID = "\"" + data.SSID.toString() + "\""
        conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        val networkId = wifiManager.addNetwork(conf)
        wifiManager.disconnect()
        wifiManager.enableNetwork(networkId, true)
        wifiManager.reconnect()
     //   val ip: String = "192.168.4.1"//Formatter.formatIpAddress(wifiManager.connectionInfo.ipAddress)

        startScan()
        navigateToSetup()
    }

    private fun navigateToSetup() {
        addFragment(
            ConfigWifiFragment(),
            R.id.fragment_container,
            Fragments.CONFIG_WIFI_FRAGMENT.title
        )
    }

    override fun setUpDevice(ssid: String, password: String) {
        Log.i("TTT", password)
        wv_web.loadUrl("http://192.168.4.1/setting?ssid=${ssid}&pass=${password}")
//        Handler().postDelayed( Runnable {
//            wv_web.loadUrl("http://192.168.4.1/setting?ssid=${ssid}&pass=${password}")
//        },2000)
    }
}

internal class MyJavaScriptInterface(private val ctx: Context) {
    @JavascriptInterface
    fun showHTML(html: String?) {
        println(html)
    }
}

interface SmartHomeListener {
    fun configureWifi()
}
