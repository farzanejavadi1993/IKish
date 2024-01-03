package ikish.aftab.ws.android.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import ikish.aftab.ws.android.adapters.MyAdapter
import ikish.aftab.ws.android.ui.fragments.myResidence.MyResidenceFragment
import ikish.aftab.ws.android.databinding.FragmentTabBinding
import java.util.*

class TabFragment : Fragment() {
    private lateinit var adapter1: EducationPagerAdapter
    lateinit var binding: FragmentTabBinding
    private var viewRoot: View? = null
    private var type: String? = ""
    private var name: String? = ""

    private var adapter: MyAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTabBinding.inflate(layoutInflater)

        viewRoot = binding.root

        type = TabFragmentArgs.fromBundle(requireArguments()).type
        name = TabFragmentArgs.fromBundle(requireArguments()).name

        /*if (type.equals("1")) {
            binding.tabLayout.addTab(binding!!.tabLayout.newTab().setText("همه پیام\u200Cها"))
            binding.tabLayout.addTab(binding!!.tabLayout.newTab().setText("اطلاع رسانی"))
            binding.tabLayout.addTab(binding!!.tabLayout.newTab().setText("سفارش\u200Cها"))
        } else if (type.equals("2")) {
            binding!!.tabLayout.addTab(binding!!.tabLayout.newTab().setText("اجاره ها"))
            binding!!.tabLayout.addTab(binding!!.tabLayout.newTab().setText("کدهای تخفیف من"))
            binding!!.tvTitleDetail.setText("خریدها و اجاره ها")

            binding!!.tvTitleTab.visibility = View.VISIBLE
            binding!!.tvTitleTab.setText("اجاره\u200Cهای فعال")

        } else {
            if (name.equals("2")){
                binding!!.tabLayout.addTab(binding!!.tabLayout.newTab().setText("اقامتگاه ها"))
                binding!!.tabLayout.addTab(binding!!.tabLayout.newTab().setText("رزرو"))
                binding!!.tvTitleDetail.setText("اقامتگاه\u200Cهای من ")
            }else
            {
                binding!!.tabLayout.addTab(binding!!.tabLayout.newTab().setText("خودروها"))
                binding!!.tabLayout.addTab(binding!!.tabLayout.newTab().setText("رزرو"))
                binding!!.tvTitleDetail.setText("خودرو\u200Cهای من ")
            }



            binding!!.tvTitleTab.visibility = View.GONE

        }*/


        binding!!.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }






        binding!!.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 1) {
                    binding!!.tvTitleTab.setText("کدهای تخفیف فعال")
                } else {
                    binding!!.tvTitleTab.setText("اجاره\u200Cهای فعال")
                }
                binding!!.viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}

        })

        binding!!.viewPager.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(
                binding!!.tabLayout
            )
        )


        return viewRoot
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter1 = EducationPagerAdapter(
            childFragmentManager,
            requireContext()
        )
        if (type.equals("1")) {
            adapter1.addFragment(AnnouncementsFragment(), "همه پیام\u200Cها")
            adapter1.addFragment(AnnouncementsFragment(), "اطلاع رسانی")
            adapter1.addFragment(AnnouncementsFragment(), "سفارش\u200Cها")
        }else if(type.equals("2")){
            adapter1.addFragment(RentFragment(), "اجاره ها")
            adapter1.addFragment(MyDiscountCodeFragment(), "کدهای تخفیف من")
            binding!!.tvTitleDetail.setText("خریدها و اجاره ها")
            binding!!.tvTitleTab.visibility = View.VISIBLE
            binding!!.tvTitleTab.setText("اجاره\u200Cهای فعال")

        }else{
            if (name.equals("2")){
                MyResidenceFragment.TYPE="2"
                MyReserveFragment.TYPE="2"
                adapter1.addFragment(MyResidenceFragment(), "اقامتگاه ها")
                adapter1.addFragment(MyReserveFragment(), "رزرو")
                binding!!.tvTitleDetail.setText("اقامتگاه\u200Cهای من ")
            }else
            {
                MyResidenceFragment.TYPE="3"
                MyReserveFragment.TYPE="3"
                adapter1.addFragment(MyResidenceFragment(), "خودروها")
                adapter1.addFragment(MyReserveFragment(), "رزرو")
                binding!!.tvTitleDetail.setText("خودرو\u200Cهای من ")
            }
            binding!!.tvTitleTab.visibility = View.GONE
        }
       /* adapter1.addFragment(MyResidenceFragment(), "ویدئو")
        adapter1.addFragment(MyReserveFragment(), "پادکست")*/
        binding.viewPager.adapter = adapter1
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }






    private class EducationPagerAdapter(fm: FragmentManager?, private val context: Context) :
        FragmentPagerAdapter(fm!!) {
        private val myFragments: MutableList<Fragment> = ArrayList<Fragment>()
        private val myFragmentTitles: MutableList<String> = ArrayList<String>()
        fun addFragment(fragment: Fragment?, title: String?) {
            if (fragment != null) {
                myFragments.add(fragment)
            }
            if (title != null) {
                myFragmentTitles.add(title)
            }
        }

        override fun getItem(position: Int): Fragment {
            return myFragments.get(position)
        }

        override fun getCount(): Int {
            return myFragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return myFragmentTitles.get(position)
        }
    }
}