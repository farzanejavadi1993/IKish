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
import ikish.aftab.ws.android.adapters.MyReserveResidenceAdapter
import ikish.aftab.ws.android.fetchData.MyViewModel
import ikish.aftab.ws.android.ui.fragments.residence.PaymentResidenceFragment
import ikish.aftab.ws.android.databinding.FragmentMyReserveBinding
import ikish.aftab.ws.android.db.ReserveResidence
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.collections4.Predicate

@AndroidEntryPoint
class MyReserveFragment : Fragment() {

    private var binding: FragmentMyReserveBinding? = null
    private var viewRoot: View? = null
    lateinit var adapter: MyReserveResidenceAdapter
    private val viewModel: MyViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMyReserveBinding.inflate(layoutInflater)

            viewRoot = binding!!.root

        binding!!.recyclerMyReserve.setLayoutManager(LinearLayoutManager(activity))
        binding!!.recyclerMyReserve.setItemAnimator(DefaultItemAnimator())
        adapter = MyReserveResidenceAdapter()
        binding!!.recyclerMyReserve.adapter = adapter


        viewModel!!.getALLReserveResidence()!!.observe(requireActivity(), object :
            Observer<List<ReserveResidence>> {

            override fun onChanged(t: List<ReserveResidence>?) {


                var list = t

                CollectionUtils.filter(
                    list,
                    Predicate<ReserveResidence> { r: ReserveResidence -> r.T.equals(TYPE) })
                if (list!!.size>0){
                    binding!!.ivIconEmpty.visibility=View.GONE
                    binding!!.tvNotFind.visibility=View.GONE
                    binding!!.tvNotFindDescription.visibility=View.GONE
                    adapter.addData(list!!)
                    adapter.notifyDataSetChanged()
                }



            }

        })


        adapter!!.OnItemClickListener(object : MyReserveResidenceAdapter.ClickItem {
            override fun onClick(N: String ,T:String,D :String) {
                PaymentResidenceFragment.date=D
               val action =TabFragmentDirections.actionGoToPaymentFragment()
                action.img=0
                action.id=N
                action.type=T
                action.readonly="1"
               findNavController().navigate(action)
            }


        })
        return viewRoot


    }
    companion object {

        var TYPE: String? = ""
    }

    override fun onDestroy() {
        super.onDestroy()
        TYPE=""
    }

}