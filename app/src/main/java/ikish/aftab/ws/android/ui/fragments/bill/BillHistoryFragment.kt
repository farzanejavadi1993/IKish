package ikish.aftab.ws.android.ui.fragments.bill




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
import ikish.aftab.ws.android.adapters.HistoryBillAdapter
import ikish.aftab.ws.android.fetchData.MyViewModel
import ikish.aftab.ws.android.databinding.FragmentHistoryBillsBinding
import ikish.aftab.ws.android.db.Bill
import java.util.ArrayList

@AndroidEntryPoint
class BillHistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBillsBinding
    private lateinit var viewRoot: View
    private lateinit var historyBillAdapter: HistoryBillAdapter
    private var list: List<Bill>? = ArrayList()
    private val viewModel: MyViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHistoryBillsBinding.inflate(layoutInflater)
        viewRoot = binding!!.root


        binding!!.recyclerViewHistoryBill.setHasFixedSize(true);
        binding!!.recyclerViewHistoryBill.setLayoutManager(LinearLayoutManager(activity))
        binding!!.recyclerViewHistoryBill.setItemAnimator(DefaultItemAnimator())
        historyBillAdapter = HistoryBillAdapter()
        binding!!.recyclerViewHistoryBill.adapter = historyBillAdapter


        viewModel.getAllBill()!!.observe(requireActivity(), object :
            Observer<List<Bill>> {

            override fun onChanged(t: List<Bill>?) {

                if(t!!.size>0){
                    binding!!.ivIconEmpty.visibility=View.GONE
                    binding!!.tvNotFind.visibility=View.GONE
                    binding!!.tvNotFindDescription.visibility=View.GONE
                }
                historyBillAdapter.addData(t!!)
                historyBillAdapter.notifyDataSetChanged()
            }

        })



        historyBillAdapter!!.OnClickListener(object : HistoryBillAdapter.ClickItem {
            override fun onClick() {
                val action=BillHistoryFragmentDirections.actionGoToDetailPayBillsFragment()
                action.type="2"
                findNavController().navigate(action)
            }


        })


        binding!!.ivBackFrament.setOnClickListener {
            findNavController().popBackStack()
        }


        return viewRoot


    }



}