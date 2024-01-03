package ikish.aftab.ws.android.ui.fragments.ticketAirPlaneService


import android.os.AsyncTask
import android.os.Build
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint

import ikish.aftab.ws.android.adapters.*
import ikish.aftab.ws.android.fetchData.MyViewModel
import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.databinding.FragmentFlightsBinding
import ikish.aftab.ws.android.model.FlightModel
import ikish.aftab.ws.android.model.FlightTimeModel
import java.util.*

@AndroidEntryPoint
class FlightsFragment :Fragment() {

    private var binding: FragmentFlightsBinding? = null
    private var viewRoot: View? = null
    lateinit var flightsAdapter: FlightsAdapter
    lateinit var flightTimesAdapter: TimesAdapter
    private var timer = Timer()
    private val viewModel: MyViewModel by viewModels()
    private var flights: List<FlightModel>? = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFlightsBinding.inflate(layoutInflater)

            viewRoot = binding!!.root



        binding!!.timeFlightRecycler.setHasFixedSize(true);
        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding!!.timeFlightRecycler.setLayoutManager(mLayoutManager)
        binding!!.timeFlightRecycler.setItemAnimator(DefaultItemAnimator())
        flightTimesAdapter = TimesAdapter()
        binding!!.timeFlightRecycler.adapter = flightTimesAdapter

        viewModel.getAllFlightTime()!!.observe(requireActivity(), object : Observer<List<FlightTimeModel>> {

            override fun onChanged(t: List<FlightTimeModel>?) {

                flightTimesAdapter.addData(t!!)
                flightTimesAdapter.addContext(activity!!)
                flightTimesAdapter.notifyDataSetChanged()
            }

        })

        flightTimesAdapter.OnItemClickListener(object : TimesAdapter.ClickItem {
            override fun onClick(N: String) {
                binding!!.flightRecycler.visibility=View.GONE
                timerAction(1000 )
            }

        })





        binding!!.flightRecycler.setLayoutManager(LinearLayoutManager(activity))
        binding!!.flightRecycler.setItemAnimator(DefaultItemAnimator())
        flightsAdapter = FlightsAdapter()
         binding!!.flightRecycler.adapter = flightsAdapter
        flightsAdapter.OnItemClickListener(object : FlightsAdapter.ClickItem {
            override fun onClick(N: String) {
                val action=FlightsFragmentDirections.actionGoToDetailFlightFragment()
                action.n=N
                findNavController().navigate(action)
            }


        })
        binding!!.flightRecycler.showShimmerAdapter()
        timerAction(3000)



        binding!!.btnSort.setOnClickListener {


            val view = inflater.inflate(R.layout.popup_layout_sort, null)
            val popupWindow = PopupWindow(
                view,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popupWindow.elevation = 10.0F
            }
            popupWindow.isOutsideTouchable = true
            popupWindow.showAsDropDown(binding!!.btnSort)



            val btnLowPrice = view.findViewById<ConstraintLayout>(R.id.layout_low_price)
            val btnHighPrice = view.findViewById<ConstraintLayout>(R.id.layout_high_price)
            val btnTimeFlight = view.findViewById<ConstraintLayout>(R.id.layout_time_flight)

            btnLowPrice.setOnClickListener {
                popupWindow.dismiss()

            }



            btnHighPrice.setOnClickListener {
                popupWindow.dismiss()

            }
            btnTimeFlight.setOnClickListener {
                popupWindow.dismiss()

            }

        }



        binding!!.ivBackFrament.setOnClickListener {
            findNavController()!!.popBackStack()
        }

        return viewRoot
    }
    private fun timerAction( timee: Long) {
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                activity!!.runOnUiThread(Runnable {
                    object : AsyncTask<Any?, Any?, Any?>() {
                        override fun onPreExecute() {
                            super.onPreExecute()
                        }

                        override fun onPostExecute(o: Any?) {
                            flightsAdapter.addData(flights!!)
                            binding!!.flightRecycler.visibility=View.VISIBLE
                            binding!!.flightRecycler.hideShimmerAdapter()
                            binding!!.progress.visibility = View.GONE
                            binding!!.btnSort.visibility = View.VISIBLE
                            binding!!.tvTitle.setText("" + flights!!.size + "  پرواز پیدا شد ،مرتب شده بر اساس :")
                            flightsAdapter.notifyDataSetChanged()
                            super.onPostExecute(o)
                        }

                        override fun doInBackground(params: Array<Any?>): Any? {
                            try {
                                flights = viewModel.getAllFlight()

                            } catch (e: Exception) {
                                val y = 0
                            }
                            return null
                        }
                    }.execute(0)

                 /*   viewModel.getAllFlight()!!.observe(
                        activity!!,
                        object : Observer<List<FlightModel>> {

                            override fun onChanged(t: List<FlightModel>?) {


                            }

                        })*/
                    timer.cancel()

                })
            }
        }, timee)
        val oneMin = 1 * 60 * 1000 // 1 minute in milli seconds



       /* object : CountDownTimer(
            oneMin.toLong(), timee
        ) {
            override fun onTick(millisUntilFinished: Long) {

                //forward progress
                val finishedSeconds = oneMin - millisUntilFinished
                val total = (finishedSeconds.toFloat() / oneMin.toFloat() * 100.0).toInt()
                binding!!.progress.setProgress(total)


            }

            override fun onFinish() {

            }
        }.start()*/


    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
}