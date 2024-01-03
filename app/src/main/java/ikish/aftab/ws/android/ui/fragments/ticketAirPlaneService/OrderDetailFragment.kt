package ikish.aftab.ws.android.ui.fragments.ticketAirPlaneService

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


import ikish.aftab.ws.android.adapters.OrderDetailPassengerAdapter
import ikish.aftab.ws.android.fetchData.MyViewModel
import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.classes.CustomSnackbar
import ikish.aftab.ws.android.db.DetailFlight

import ikish.aftab.ws.android.db.PassengerFlight
import ikish.aftab.ws.android.databinding.FragmentOrderDetailBinding
@AndroidEntryPoint
class OrderDetailFragment : Fragment() {

    private var binding: FragmentOrderDetailBinding? = null
    private var viewRoot: View? = null
    private var adapter: OrderDetailPassengerAdapter? = null
    var passengerList: List<PassengerFlight>? = null
    private val viewModel: MyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOrderDetailBinding.inflate(layoutInflater)

            viewRoot = binding!!.root


        var numberOrder: String? = OrderDetailFragmentArgs.fromBundle(requireArguments()).n

        if (arrayListExtradition!!.size > 0) {
            var name: String? = ""
            for (i in 0 until arrayListExtradition!!.size) {
                name = name + " " + arrayListExtradition!!.get(i)

            }
            showSnackBar(name!!)
        }



        adapter = OrderDetailPassengerAdapter()

        binding!!.recyclerView.setHasFixedSize(true)
        binding!!.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        binding!!.recyclerView.setItemAnimator(DefaultItemAnimator())
        binding!!.recyclerView.adapter = adapter

        object : AsyncTask<Any?, Any?, Any?>() {
            override fun onPreExecute() {
                super.onPreExecute()
            }


            override fun onPostExecute(o: Any?) {

                adapter!!.addData(passengerList!!)
                adapter!!.addType("1")
                adapter!!.notifyDataSetChanged()

                super.onPostExecute(o)
            }

            override fun doInBackground(params: Array<Any?>): Any? {
                try {

                    passengerList = viewModel.getAllPassengerFlight(numberOrder!!)!!

                } catch (e: Exception) {
                    val y = 0
                }
                return null
            }
        }.execute(0)

       /* viewModel!!.getAllPassengerFlight(numberOrder!!)!!.observe(requireActivity(), object :
            Observer<List<PassengerFlight>> {
            override fun onChanged(t: List<PassengerFlight>?) {
                passengerList = t

                adapter!!.addData(passengerList!!)
                adapter!!.addType("1")
                adapter!!.notifyDataSetChanged()
            }
            })*/



        viewModel!!.getDetailFlight(numberOrder!!)!!.observe(requireActivity(), object :
            Observer<DetailFlight> {
            override fun onChanged(t: DetailFlight?) {
                var detailFlight:DetailFlight= t!!
                if (detailFlight != null) {
                    binding!!.tvTitleDetail.setText("سفارش " + numberOrder)
                    binding!!.tvNameFlightCompany.setText(detailFlight.CN)
                    binding!!.tvNumberFlight.setText(detailFlight.NF)
                    binding!!.tvTimeOrigin.setText(detailFlight.TOO)
                    binding!!.tvTimeDestination.setText(detailFlight.TD)
                    binding!!.tvTimeFlight.setText(detailFlight.T)
                    binding!!.tvOriginAirport.setText(detailFlight.AO)
                    binding!!.tvDestinationAirport.setText(detailFlight.AD)
                    binding!!.tvNameOrogin.setText(detailFlight.O)
                    binding!!.tvNameDestination.setText(detailFlight.D)
                    binding!!.status.setText(detailFlight.ST)
                    binding!!.typeFlight.setText(detailFlight.TF)
                    binding!!.sumprice.setText(detailFlight.SP)
                    binding!!.numberOrder.setText(detailFlight.ONN)
                }

            }

        })









        binding!!.ivExtradition.setOnClickListener {
            val action = OrderDetailFragmentDirections.actionGoToExtraditionFragment()
            action.n=numberOrder!!
            findNavController().navigate(action)
        }


        binding!!.ivBackFrament.setOnClickListener {
            findNavController().popBackStack()
        }

        return viewRoot
    }


    companion object {
        var arrayListExtradition: ArrayList<String>? = ArrayList()
    }


    private fun showSnackBar(message: String) {
        val sb = CustomSnackbar(requireActivity())
        sb.message("بلیط پرواز به نام " + message + " کنسل شد.")


        sb.padding(15)
        sb.cornerRadius(15f)
        sb.duration(Snackbar.LENGTH_LONG)
        sb.withAction(resources.getString(R.string.returned)) { snackbar: Snackbar ->
            snackbar.dismiss()
        }
        sb.show()


    }


    override fun onDestroy() {
        super.onDestroy()
        arrayListExtradition!!.clear()
    }


}