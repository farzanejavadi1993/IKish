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
import ikish.aftab.ws.android.adapters.EventAdapter
import ikish.aftab.ws.android.adapters.TimesAdapter
import ikish.aftab.ws.android.fetchData.MyViewModel
import ikish.aftab.ws.android.databinding.FragmentEventBinding
import ikish.aftab.ws.android.model.EventModel
import ikish.aftab.ws.android.model.FlightTimeModel
import java.util.*

@AndroidEntryPoint
class EventFragment : Fragment() {

    private lateinit var binding: FragmentEventBinding
    private lateinit var viewRoot: View
    lateinit var timesAdapter: TimesAdapter
    private var timer = Timer()
    lateinit var eventAdapter: EventAdapter
    val viewModel: MyViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventBinding.inflate(layoutInflater)
        viewRoot = binding!!.root

        return viewRoot
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.recyclerTimeEvent.setHasFixedSize(true);
        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding!!.recyclerTimeEvent.setLayoutManager(mLayoutManager)
        binding!!.recyclerTimeEvent.setItemAnimator(DefaultItemAnimator())
        timesAdapter = TimesAdapter()
        binding!!.recyclerTimeEvent.adapter = timesAdapter

        viewModel.getAllEventTime()!!.observe(requireActivity(), object :
            Observer<List<FlightTimeModel>> {

            override fun onChanged(t: List<FlightTimeModel>?) {

                timesAdapter.addData(t!!)
                timesAdapter.addContext(activity!!)
                timesAdapter.notifyDataSetChanged()
            }

        })

        binding!!.ivBackFrament.setOnClickListener {
            findNavController().popBackStack()
        }



        binding!!.recyclerEvent.setHasFixedSize(true);
        binding!!.recyclerEvent.setLayoutManager(LinearLayoutManager(activity))
        binding!!.recyclerEvent.setItemAnimator(DefaultItemAnimator())
        eventAdapter = EventAdapter()
        binding!!.recyclerEvent.adapter = eventAdapter

        viewModel.getAllEvent()!!.observe(requireActivity(), object :
            Observer<List<EventModel>> {

            override fun onChanged(t: List<EventModel>?) {

                eventAdapter.addData(t!!)
                eventAdapter.notifyDataSetChanged()
            }

        })


        timesAdapter.OnItemClickListener(object : TimesAdapter.ClickItem {
            override fun onClick(N: String) {
                binding!!.recyclerEvent.visibility=View.GONE
                timerAction(1000)
            }

        })

    }
    private fun timerAction( timee: Long) {
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                activity!!.runOnUiThread(Runnable {
                   binding!!.recyclerEvent.visibility=View.VISIBLE
                    timer.cancel()

                })
            }
        }, timee)
        val oneMin = 1 * 60 * 1000 // 1 minute in milli seconds
    }


}