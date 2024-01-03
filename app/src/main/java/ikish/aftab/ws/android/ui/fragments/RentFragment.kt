package ikish.aftab.ws.android.ui.fragments

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
import dagger.hilt.android.AndroidEntryPoint


import ikish.aftab.ws.android.adapters.ReserveResidenceAdapter
import ikish.aftab.ws.android.fetchData.MyViewModel
import ikish.aftab.ws.android.ui.fragments.residence.PaymentResidenceFragment

import ikish.aftab.ws.android.db.ReserveResidence

import ikish.aftab.ws.android.databinding.FragmentRentBinding


@AndroidEntryPoint
class RentFragment : Fragment() {

    private var binding: FragmentRentBinding? = null
    private var viewRoot: View? = null
    lateinit var adapter: ReserveResidenceAdapter
    private val viewModel: MyViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRentBinding.inflate(layoutInflater)

        viewRoot = binding!!.root



        binding!!.recyclerReserve.setLayoutManager(LinearLayoutManager(activity))
        binding!!.recyclerReserve.setItemAnimator(DefaultItemAnimator())
        adapter = ReserveResidenceAdapter()
        binding!!.recyclerReserve.adapter = adapter

        adapter!!.OnItemClickListener(object : ReserveResidenceAdapter.ClickItem {
            override fun onClick(N: String, Img: Int,type: String,date: String) {

                PaymentResidenceFragment.date=date

                val action =TabFragmentDirections.actionGoToPaymentFragment()
                action.img=0
                action.id=N
                action.type=type
                action.readonly="1"
                findNavController().navigate(action)

            }

        })

        viewModel!!.getALLReserveResidence()!!.observe(requireActivity(), object :
            Observer<List<ReserveResidence>> {

            override fun onChanged(t: List<ReserveResidence>?) {


                var list = t
                adapter.addData(list!!)
                adapter.notifyDataSetChanged()

            }

        })




        return viewRoot


    }


}