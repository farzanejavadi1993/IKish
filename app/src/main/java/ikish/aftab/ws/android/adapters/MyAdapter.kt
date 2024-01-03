package ikish.aftab.ws.android.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ikish.aftab.ws.android.ui.fragments.*
import ikish.aftab.ws.android.ui.fragments.myResidence.MyResidenceFragment


internal class MyAdapter(
    var context: Context,
    var location: String,//1 اعلانات      2اجاره ها و خریدها   3 اقامگاه های من
    var type :String ,//3 خودرو  , 2ویلا
    fm: FragmentManager,
    var totalTabs: Int
) : FragmentPagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                if (location.equals("1")) {
                    AnnouncementsFragment()
                }
                else if (location.equals("2")) {
                    RentFragment()
                }  else   {
                    MyResidenceFragment.TYPE=type
                    MyResidenceFragment()



                }

            }

            1 -> {
              if (location.equals("1")) {
                    AnnouncementsFragment()
                }
                else if (location.equals("2")) {
                    MyDiscountCodeFragment()
                }
                else  {
                  MyReserveFragment.TYPE=type
                    MyReserveFragment()
                }


            }
           2 -> {
                AnnouncementsFragment()

            }
            else ->{
                getItem(position)
            }
        }
    }
    override fun getCount(): Int {
        return totalTabs

    }


}