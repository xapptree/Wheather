package com.xapptree.wheather.presentation.modules.weather

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.xapptree.wheather.R
import com.xapptree.wheather.presentation.sharedpref.SharedPrefConstants
import com.xapptree.wheather.presentation.sharedpref.SharedPrefHelper
import kotlinx.android.synthetic.main.fragment_city.*

class CityFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private var listener: CityFragmentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CityFragmentListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement CityFragmentListener")
        }
        Log.i("TAG LOG-CityFragment", "onAttach")
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        Log.i("TAG LOG-CityFragment", "onDetach")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel = activity?.run {
            ViewModelProvider(this)[MainViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        Log.i("TAG LOG-CityFragment", "OnCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.i("TAG LOG-CityFragment", "OnCreateView")
        return inflater.inflate(R.layout.fragment_city, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar?.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar?.setNavigationOnClickListener {
            listener?.cityFragmentBackClicked()
        }
        val units =  SharedPrefHelper(activity!!).getUnits(SharedPrefConstants.UNITS)
        viewModel.onTodayForecastAPISuccess()?.observe(activity!!, {
            toolbar?.title = it?.name
            tv_main?.text = "main/${it?.weather?.get(0)?.main}"
            tv_unit?.text = "units/${units}"
            tv_temp?.text = "${it?.main?.temp}"
            tv_humd?.text = "${it?.main?.humidity}"
            tv_wind?.text = "Speed - ${it?.wind?.speed} , Degree -  ${it?.wind?.deg}"
            tv_rain?.text = "${it?.clouds?.all} %"

        })
        Log.i("TAG LOG-CityFragment", "onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.i("TAG LOG-CityFragment", "OnStart")
    }
    override fun onResume() {
        super.onResume()
        Log.i("TAG LOG-CityFragment", "OnResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("TAG LOG-CityFragment", "OnPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("TAG LOG-CityFragment", "OnStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("TAG LOG-CityFragment", "OnDestroy")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("TAG LOG-CityFragment", "OnDestroyView")
    }

    interface CityFragmentListener{
        fun cityFragmentBackClicked()
    }

}