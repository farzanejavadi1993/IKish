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

import ikish.aftab.ws.android.adapters.TicketAirPlaneAdapter
import ikish.aftab.ws.android.fetchData.MyViewModel
import ikish.aftab.ws.android.db.DetailFlight
import ikish.aftab.ws.android.databinding.FragmentMyTicketAirPlaneBinding

@AndroidEntryPoint
class MyTicketAirPlaneFragment : Fragment() {

    private var binding:FragmentMyTicketAirPlaneBinding? = null
    private var viewRoot: View? = null
    private var adapter: TicketAirPlaneAdapter? = null
    private val viewModel: MyViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMyTicketAirPlaneBinding.inflate(layoutInflater)

            viewRoot = binding!!.root


        viewModel!!.getAllDetailFlight()!!.observe(requireActivity(), object :
            Observer<List<DetailFlight>> {

            override fun onChanged(t: List<DetailFlight>?) {

                var list= t

                if(list!!.size>0){
                    binding!!.tvNotFind.visibility=View.GONE
                    binding!!.tvNotFindDescription.visibility=View.GONE
                    binding!!.ivIconEmpty.visibility=View.GONE

                    adapter!!.addData(list)
                    adapter!!.notifyDataSetChanged()
                }
                else{
                    binding!!.tvNotFind.visibility=View.VISIBLE
                    binding!!.tvNotFindDescription.visibility=View.VISIBLE
                    binding!!.ivIconEmpty.visibility=View.VISIBLE

                }




            }

        })


        adapter = TicketAirPlaneAdapter()
        binding!!.recyclerTicketAirplane.setHasFixedSize(true)
        binding!!.recyclerTicketAirplane.setLayoutManager(LinearLayoutManager(activity))
        binding!!.recyclerTicketAirplane.setItemAnimator(DefaultItemAnimator())
        binding!!.recyclerTicketAirplane.adapter = adapter



        adapter!!.OnItemClickListener(object : TicketAirPlaneAdapter.ClickItem {
            override fun onClick(N: String) {
                val action=MyTicketAirPlaneFragmentDirections.actionGoToDetailOrderFragment()
                action.n=N
                findNavController().navigate(action)

            }


        })


        binding!!.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        return viewRoot


    }


}