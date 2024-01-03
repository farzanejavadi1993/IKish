package ikish.aftab.ws.android.ui.fragments.residence

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener
import ikish.aftab.ws.android.ui.fragments.CalendarFragmentArgs
import ikish.aftab.ws.android.databinding.FragmentFilterResidenceBinding
import java.text.DecimalFormat


class FilterResidenceFragment : Fragment() {
    private val formatter = DecimalFormat("#,###,###,###")
    private var binding: FragmentFilterResidenceBinding? = null
    private var viewRoot: View? = null
    private var countRoom :Int=0
    private var type:String?="1"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterResidenceBinding.inflate(layoutInflater)
        viewRoot = binding!!.root


        type= CalendarFragmentArgs.fromBundle(requireArguments()).type

        if (type.equals("3")){
            binding!!.tvRangePrice.setText("بازه قیمت برای هر روز")
            binding!!.tvAvragePrice.setText("میانگین اجاره ها برای هر روز ۶۲۰،۰۰۰ تومان است.")
            binding!!.tvFindResidence.setText("۱۳ خودرو در این بازه قیمت در دسترس است.")
            binding!!.tvPossibility.setText("امکانات خودرو")
            binding!!.tvBestPossibility.setText("محبوبترین امکانات در بین کاربران")
            binding!!.lnrCarPosibility.visibility=View.VISIBLE
            binding!!.tvTypeResidence.setText("نوع خودرو")
            binding!!.lnrTypeCar.visibility=View.VISIBLE
            binding!!.btnShowAll.visibility=View.VISIBLE
            binding!!.tvBrand.visibility=View.VISIBLE
            binding!!.tvBestBrand.visibility=View.VISIBLE
            binding!!.lnrBrand.visibility=View.VISIBLE
            binding!!.viewb.visibility=View.GONE

        }else{
            binding!!.rlRoom.visibility=View.VISIBLE
            binding!!.lnrApartmentPosibility.visibility=View.VISIBLE
            binding!!.lnrTypeResidence.visibility=View.VISIBLE
            binding!!.tvTypeResidence.visibility=View.VISIBLE
            binding!!.lnrTypeResidence.visibility=View.VISIBLE
            binding!!.tvOtherProperties.visibility=View.VISIBLE
            binding!!.lnrOtherProperties.visibility=View.VISIBLE
        }





        binding!!.btnSeeAll.setOnClickListener {
            if (binding!!.morePosibility.visibility==View.VISIBLE){
                binding!!.btnSeeAll.setText("نمایش همه امکانات")
                if ( type.equals("3"))
                binding!!.morePosibilityCar.visibility=View.GONE
                else
                binding!!.morePosibility.visibility=View.GONE
            }else{
                binding!!.btnSeeAll.setText("بستن لیست")
               if ( type.equals("3"))
                binding!!.morePosibilityCar.visibility=View.VISIBLE
                else
                   binding!!.morePosibility.visibility=View.VISIBLE

            }
        }



        binding!!.rangeSeekbar5.setOnRangeSeekbarChangeListener(OnRangeSeekbarChangeListener { minValue, maxValue ->
            binding!!.tvMinPrice.setText(formatter.format(minValue) + " تومان")
            binding!!.tvMaxPrice.setText(formatter.format(maxValue) + " تومان")
            binding!!.tvFindResidence.setText(" 9اقامتگاه در این بازه قیمت در دسترس است.")
        })




        binding!!.rangeSeekbar5.setOnRangeSeekbarFinalValueListener(OnRangeSeekbarFinalValueListener { minValue, maxValue ->
            Log.d(
                "CRS=>",
                "$minValue : $maxValue"
            )
        })

        binding!!.ivPlus.setOnClickListener {
            countRoom=countRoom+1
            binding!!.tvCountRoom.setText(countRoom.toString())
        }
        binding!!.ivMinus.setOnClickListener {
            if (countRoom>=1){
                countRoom=countRoom-1
                binding!!.tvCountRoom.setText(countRoom.toString())
            }

        }

        binding!!.btnDoFilter.setOnClickListener {
            requireFragmentManager().popBackStack()
       /*     val budle = Bundle()
            budle.putString("key", "1")
            val frg: Fragment? =
                fragmentManager!!.findFragmentByTag("ResidanceFragment")
            val ft: FragmentTransaction = fragmentManager!!.beginTransaction()

            frg!!.arguments=budle
            assert(frg != null)
            ft.detach(frg!!)
            ft.attach(frg)
            ft.commit()*/
        }


        binding!!.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        return viewRoot


    }


}