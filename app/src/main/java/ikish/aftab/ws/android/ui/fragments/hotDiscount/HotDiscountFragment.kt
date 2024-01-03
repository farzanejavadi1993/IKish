package ikish.aftab.ws.android.ui.fragments.hotDiscount

import ikish.aftab.ws.android.databinding.FragmentHotDiscountBinding



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import ikish.aftab.ws.android.adapters.HotDiscountAdapter
import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.model.HotDiscountModel

class HotDiscountFragment : Fragment() {

    private var binding: FragmentHotDiscountBinding? = null
    private var viewRoot: View? = null
    private var adapter: HotDiscountAdapter? = null
    private var list:MutableList<HotDiscountModel> ?=ArrayList()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHotDiscountBinding.inflate(layoutInflater)
        if (viewRoot==null)
            viewRoot = binding!!.root



        adapter = HotDiscountAdapter()
        binding!!.recyclerBestDiscount.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding!!.recyclerBestDiscount.setLayoutManager(mLayoutManager)
        binding!!.recyclerBestDiscount.setItemAnimator(DefaultItemAnimator())
        binding!!.recyclerBestDiscount.adapter = adapter


        binding!!.recyclerSuggestDiscount.setHasFixedSize(true)
        val mLayoutManager1 = LinearLayoutManager(context)
        mLayoutManager1.orientation = LinearLayoutManager.HORIZONTAL
        binding!!.recyclerSuggestDiscount.setLayoutManager(mLayoutManager1)
        binding!!.recyclerSuggestDiscount.setItemAnimator(DefaultItemAnimator())
        binding!!.recyclerSuggestDiscount.adapter = adapter


        list!!.clear()
        createDiscount()
        adapter!!.addData(list!!)
        adapter!!.notifyDataSetChanged()
        binding!!.recyclerBestDiscount?.post { mLayoutManager?.scrollToPosition(2) }
        binding!!.recyclerSuggestDiscount?.post { mLayoutManager1?.scrollToPosition(2) }


        adapter!!.OnItemClickListener(object : HotDiscountAdapter.ClickItem {
            override fun onClick() {
                val action=HotDiscountFragmentDirections.actionGoToDetailHotDiscountFragment()
                findNavController().navigate(action)
            }


        })

        binding!!.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        return viewRoot


    }

        private fun createDiscount() {


            val discount1 = HotDiscountModel("مال هزار جزیره", "تا 40% تخفیف", R.drawable.kishpasaj)
            val discount2 = HotDiscountModel("شهریار کیش", "تا 40% تخفیف", R.drawable.pasaj1)
            val discount3 = HotDiscountModel("کوروش بهاری", "تا 40% تخفیف", R.drawable.pasaj2)

            list!!.add(discount1)
            list!!.add(discount2)
            list!!.add(discount3)


        }
}