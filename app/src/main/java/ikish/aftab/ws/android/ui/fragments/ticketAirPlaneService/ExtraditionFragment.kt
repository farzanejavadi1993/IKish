package ikish.aftab.ws.android.ui.fragments.ticketAirPlaneService

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint

import ikish.aftab.ws.android.adapters.OrderDetailPassengerAdapter
import ikish.aftab.ws.android.fetchData.MyViewModel
import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.classes.calendarLibrary.ExtraditionDialog

import ikish.aftab.ws.android.db.PassengerFlight
import ikish.aftab.ws.android.databinding.FragmentExtraditionBinding

import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.collections4.Predicate
import java.util.*
import kotlin.collections.ArrayList
@AndroidEntryPoint
class ExtraditionFragment : Fragment() {

    private var binding: FragmentExtraditionBinding? = null
    private var viewRoot: View? = null
    private var adapter: OrderDetailPassengerAdapter? = null
    private var ticketList: ArrayList<String>?= ArrayList()
    private var clickButton: Boolean ?= false

    private var list: List<PassengerFlight> ?= null

    private val viewModel: MyViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentExtraditionBinding.inflate(layoutInflater)
        viewRoot = binding!!.root



        var numberOrder: String?= ExtraditionFragmentArgs.fromBundle(requireArguments()).n


        adapter = OrderDetailPassengerAdapter()

        binding!!.recyclerExtraditionTicket.setHasFixedSize(true)
        binding!!.recyclerExtraditionTicket.setLayoutManager(LinearLayoutManager(activity))
        binding!!.recyclerExtraditionTicket.setItemAnimator(DefaultItemAnimator())
        binding!!.recyclerExtraditionTicket.adapter = adapter
        adapter!!.addType("2")
        adapter!!.addRes(requireActivity().resources.getDrawable(R.drawable.item_passenger_radus))

        adapter!!.OnItemDeleteListener(object : OrderDetailPassengerAdapter.CheckItem {
            override fun onCheck(N: String, isCheck: Boolean) {
               if (isCheck){
                   if (!ticketList!!.contains(N))
                       ticketList!!.add(N)
               }else{
                   var result :ArrayList<String> = ArrayList()
                   result.addAll(ticketList!!)
                   CollectionUtils.filter(
                       result,
                       Predicate<String> { r: String -> r.equals(N) })
                   var index: Int =ticketList!!.indexOf(result.get(0))

                  ticketList!!.removeAt(index)
               }
                if (ticketList!!.size>0){
                    clickButton=true
                    binding!!.btnContinue.setBackgroundColor(resources.getColor(R.color.topaz))
                }else{
                    clickButton=false
                    binding!!.btnContinue.setBackgroundColor(resources.getColor(R.color.very_light_pink))
                }


            }

        })

        object : AsyncTask<Any?, Any?, Any?>() {
            override fun onPreExecute() {
                super.onPreExecute()
            }


            override fun onPostExecute(o: Any?) {


                adapter!!.addData(list!!)
                adapter!!.notifyDataSetChanged()

                super.onPostExecute(o)
            }

            override fun doInBackground(params: Array<Any?>): Any? {
                try {

                    list = viewModel.getAllPassengerFlight(numberOrder!!)!!

                } catch (e: Exception) {
                    val y = 0
                }
                return null
            }
        }.execute(0)

        /*viewModel!!.getAllPassengerFlight(numberOrder!!)!!.observe(requireActivity(), object :
            Observer<List<PassengerFlight>> {
            override fun onChanged(t: List<PassengerFlight>?) {
                if (p.equals("")) {
                    var list = t

                } } })*/

        binding!!.ivBackFrament.setOnClickListener {
            findNavController().popBackStack()
        }

        binding!!.btnContinue.setOnClickListener {
            if (clickButton!!){
                val dialog = ExtraditionDialog(activity, requireActivity().getString(R.string.title_extradition_dialog),
                    object : ExtraditionDialog.DialogCallback {
                        override fun onClickSelected(name:String) {

                            var passenger: PassengerFlight =list!!.get(0)!!

                            if (passenger!=null){
                                viewModel.deletePassengerFlight(numberOrder!!)
                            }

                            OrderDetailFragment.arrayListExtradition!!.addAll(ticketList!!)
                            findNavController()!!.popBackStack()

//                          viewModel!!.getAllPassengerFlight(numberOrder!!)!!.observe(requireActivity(), object :
//                              Observer<List<PassengerFlight>> {
//                                override fun onChanged(t: List<PassengerFlight>?) {
//                                    if (p.equals("")){
//                                        var passenger: PassengerFlight =t!!.get(0)!!
//
//                                        if (passenger!=null){
//                                            viewModel.deletePassengerFlight(numberOrder)
//                                        }
//                                        p="complete"
//                                        OrderDetailFragment.arrayListExtradition!!.addAll(ticketList!!)
//                                        findNavController()!!.popBackStack()
//                                    }
//                                }
//                                })




                        }
                        override fun onCancel() {

                        }
                    })
                dialog.showDialog()
            }else{
                Toast.makeText(activity,"بلیط مورد نظر را انتخاب کنید" ,Toast.LENGTH_SHORT).show()
            }
        }

        return viewRoot


    }


}