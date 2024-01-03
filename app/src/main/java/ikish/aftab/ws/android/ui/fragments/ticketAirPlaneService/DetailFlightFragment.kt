package ikish.aftab.ws.android.ui.fragments.ticketAirPlaneService

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ikish.aftab.ws.android.adapters.FlightPassengerAdapter
import ikish.aftab.ws.android.fetchData.MyViewModel
import ikish.aftab.ws.android.ui.fragments.passenger.PassengerListFragment
import ikish.aftab.ws.android.db.Passenger

import ikish.aftab.ws.android.db.DetailFlight


import ikish.aftab.ws.android.databinding.FragmentDetailFlightBinding
import ikish.aftab.ws.android.db.PassengerFlight

import ikish.aftab.ws.android.model.FlightModel

import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.collections4.Predicate
import kotlin.random.Random
@AndroidEntryPoint
class DetailFlightFragment : Fragment() {

    private var binding: FragmentDetailFlightBinding? = null
    private var viewRoot: View? = null
    private var GUID: String? = null

    private var adapter: FlightPassengerAdapter? = null
    private var passengerList: ArrayList<PassengerFlight>? = ArrayList()
    private var sumPrice: Int? = 0
    private var numberOrder: String? = ""
    private val viewModel: MyViewModel by viewModels()
    private var flights: List<FlightModel>? = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailFlightBinding.inflate(layoutInflater)
        viewRoot = binding!!.root


        GUID = DetailFlightFragmentArgs.fromBundle(requireArguments()).n




        adapter = FlightPassengerAdapter()
        binding!!.recyclerPassenger.setHasFixedSize(true)
        binding!!.recyclerPassenger.setLayoutManager(LinearLayoutManager(activity))
        binding!!.recyclerPassenger.setItemAnimator(DefaultItemAnimator())
        binding!!.recyclerPassenger.adapter = adapter
        adapter!!.OnItemDeleteListener(object : FlightPassengerAdapter.DeleteItem {
            override fun onClick(F: String, N: String) {


                val listResult: MutableList<PassengerFlight> = mutableListOf()
                listResult.addAll(passengerList!!)
                CollectionUtils.filter(
                    listResult,
                    Predicate<PassengerFlight> { r: PassengerFlight -> r.F.equals(F) && r.N.equals(N) })
                if (listResult.size > 0) {
                    passengerList!!.removeAt(passengerList!!.indexOf(listResult.get(0)))
                    adapter!!.notifyDataSetChanged()
                }

                if (passengerList!!.size == 0) {
                    binding!!.layoutPriceItem.visibility = View.VISIBLE
                    binding!!.btnChooseFlight.setText("انتخاب این پرواز")
                    TYPE = "1"

                }


            }


        })


        object : AsyncTask<Any?, Any?, Any?>() {
            override fun onPreExecute() {
                super.onPreExecute()
            }

            override fun onPostExecute(o: Any?) {
                CollectionUtils.filter(
                    flights,
                    Predicate<FlightModel> { r: FlightModel -> r.NumberFlight.equals(GUID) })
                if (flights!!.size > 0) {
                    binding!!.tvTimeOrigin.setText(flights!!.get(0).OriginTime)
                    binding!!.tvTimeDestination.setText(flights!!.get(0).DestinationTime)
                    binding!!.tvNameOrogin.setText(flights!!.get(0).OriginName)
                    binding!!.tvNameDestination.setText(flights!!.get(0).DestinationName)
                    binding!!.tvOriginAirport.setText(flights!!.get(0).AirportOrigin)
                    binding!!.tvDestinationAirport.setText(flights!!.get(0).AirportDestination)
                    binding!!.tvTimeFlight.setText(flights!!.get(0).Time)
                    binding!!.tvNumberFlight.setText(flights!!.get(0).NumberFlight)
                    binding!!.tvNameFlightCompany.setText(flights!!.get(0).CompanyName)
                    binding!!.tvOldPrice.setText(flights!!.get(0).OldPrice + "تومان")
                    binding!!.tvYoungPrice.setText(flights!!.get(0).YoungPrice + "تومان")
                    binding!!.tvBabyPrice.setText(flights!!.get(0).BabyPrice + "تومان")
                    numberOrder = "" + Random(System.nanoTime()).nextInt(100000) + 0 + " "
                    passengerList!!.clear()
                    var RA: String = ""
                    var P: String = ""
                    for (i in 0 until array!!.size) {
                        var age: String?="1398"
                        try {
                            age = array!!.get(i).BRD!!.split("/")[0]!!
                        }catch (e :Exception){}

                        if (age!!.toInt() >= 1398) {
                            RA = "خردسال"
                            P = flights!!.get(0).BabyPrice + "تومان"
                            sumPrice = sumPrice!! + flights!!.get(0).BabyPrice.toInt()
                        } else if (age!!.toInt() < 1398 && age!!.toInt() > 1388) {
                            RA = "کودک"
                            P = flights!!.get(0).YoungPrice + "تومان"
                            sumPrice = sumPrice!! + flights!!.get(0).BabyPrice.toInt()
                        } else {
                            RA = "بزرگسال"
                            P = flights!!.get(0).OldPrice + "تومان"
                            sumPrice = sumPrice!! + flights!!.get(0).BabyPrice.toInt()
                        }
                        val PassengerFlight = PassengerFlight(
                            Random(System.nanoTime()).nextInt(100000) + 0,
                            array!!.get(i).CN!!,
                            "",
                            P,
                            numberOrder.toString(),
                        )
                        passengerList!!.add(PassengerFlight)


                    }
                    adapter!!.addData(passengerList!!)
                    adapter!!.notifyDataSetChanged()
                }
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


        /* viewModel.getAllFlight()!!.observe(requireActivity(), object : Observer<List<FlightModel>> {

             override fun onChanged(t: List<FlightModel>?) {
                 CollectionUtils.filter(
                     t, Predicate<FlightModel> { r: FlightModel -> r.NumberFlight.equals(GUID) })
                 if (t!!.size > 0) {
                     binding!!.tvTimeOrigin.setText(t.get(0).OriginTime)
                     binding!!.tvTimeDestination.setText(t.get(0).DestinationTime)
                     binding!!.tvNameOrogin.setText(t.get(0).OriginName)
                     binding!!.tvNameDestination.setText(t.get(0).DestinationName)
                     binding!!.tvOriginAirport.setText(t.get(0).AirportOrigin)
                     binding!!.tvDestinationAirport.setText(t.get(0).AirportDestination)
                     binding!!.tvTimeFlight.setText(t.get(0).Time)
                     binding!!.tvNumberFlight.setText(t.get(0).NumberFlight)
                     binding!!.tvNameFlightCompany.setText(t.get(0).CompanyName)
                     binding!!.tvOldPrice.setText(t.get(0).OldPrice + "تومان")
                     binding!!.tvYoungPrice.setText(t.get(0).YoungPrice + "تومان")
                     binding!!.tvBabyPrice.setText(t.get(0).BabyPrice + "تومان")
                     numberOrder =""+Random(System.nanoTime()).nextInt(100000) + 0 + " "
                     passengerList!!.clear()
                     var RA:String=""
                     var P:String=""
                     for (i in 0 until array!!.size) {
                         var age: String? = array!!.get(i).BRD!!.split("/")[0]
                         if (age!!.toInt() >= 1398) {
                             RA = "خردسال"
                             P = t.get(0).BabyPrice + "تومان"
                             sumPrice = sumPrice!! + t.get(0).BabyPrice.toInt()
                         }
                         else if (age!!.toInt() < 1398 && age!!.toInt() > 1388) {
                             RA = "کودک"
                             P = t.get(0).YoungPrice + "تومان"
                             sumPrice = sumPrice!! + t.get(0).BabyPrice.toInt()
                         }
                         else {
                             RA = "بزرگسال"
                             P = t.get(0).OldPrice + "تومان"
                             sumPrice = sumPrice!! + t.get(0).BabyPrice.toInt()
                         }
                         val PassengerFlight = PassengerFlight(
                             Random(System.nanoTime()).nextInt(100000) + 0,
                             array!!.get(i).CN!!,
                             "",
                             P,
                             numberOrder.toString(),
                             )
                         passengerList!!.add(PassengerFlight)


                     }
                     adapter!!.addData(passengerList!!)
                     adapter!!.notifyDataSetChanged()
                 }
             }

         })*/




        if (TYPE.equals("2")) {
            binding!!.layoutDate.visibility = View.VISIBLE
            binding!!.layoutPriceItem.visibility = View.GONE
            binding!!.rolesCancel.visibility = View.GONE
            binding!!.layoutAddPassenger.visibility = View.VISIBLE
            binding!!.recyclerPassenger.visibility = View.VISIBLE
            binding!!.btnChooseFlight.setText("پرداخت " + sumPrice + "تومان")
        }



        binding!!.btnChooseFlight.setOnClickListener {
            if (TYPE.equals("1")) {
                PassengerListFragment.TYPE = "2"
                val action = DetailFlightFragmentDirections.actionGoToPassengerListFragment()
                findNavController().navigate(action)


            } else {


                val detailFlight = DetailFlight(
                    Random(System.nanoTime()).nextInt(100000) + 0,
                    numberOrder.toString(),
                    "12 اردیبهشت",
                    binding!!.tvNameFlightCompany.text.toString(),
                    binding!!.tvNumberFlight.text.toString(),
                    binding!!.tvTimeOrigin.text.toString(),
                    binding!!.tvTimeDestination.text.toString(),
                    binding!!.tvNameOrogin.text.toString(),
                    binding!!.tvNameDestination.text.toString(),
                    binding!!.tvTimeFlight.text.toString(),
                    binding!!.tvOriginAirport.text.toString(),
                    binding!!.tvDestinationAirport.text.toString(),
                    " نهایی شده",
                    "داخلی",
                    sumPrice.toString(),
                    binding!!.tvNameOrogin.text.toString() + " به " + binding!!.tvNameDestination.text.toString(),
                    "" + passengerList!!.size + " نفر "

                )
                viewModel.insertDetailFlight(detailFlight)


                /* viewModel!!.getAllPassengerFlight(numberOrder!!)!!.observe(requireActivity(), object :
                Observer<List<PassengerFlight>> {
                override fun onChanged(t: List<PassengerFlight>?) {
                    var list =t

                    for (i in 0 until list!!.size) {

                       var passengerFlight :PassengerFlight ?= list!!.get(i)

                        viewModel.deletePassengerFlight(passengerFlight!!.F)

                    }

                }


            })*/


                for (i in 0 until passengerList!!.size) {
                    viewModel.insertPassengerFlight(passengerList!!.get(i))

                }


                val action =
                    DetailFlightFragmentDirections.actionGoToOrderDetailFragment()
                action.n=numberOrder!!
                findNavController().navigate(action)


            }

        }

        binding!!.layoutAddPassenger.setOnClickListener {
            PassengerListFragment.TYPE = "2"
            val action = DetailFlightFragmentDirections.actionGoToPassengerListFragment()
            findNavController().navigate(action)
        }

        binding!!.ivBackFrament.setOnClickListener {
            findNavController().popBackStack()
        }


        return viewRoot
    }

    companion object {
        var TYPE = "1"
        var array: ArrayList<Passenger>? = ArrayList()


    }


    override fun onDestroy() {
        super.onDestroy()
        TYPE = "1"
        array!!.clear()

    }

}