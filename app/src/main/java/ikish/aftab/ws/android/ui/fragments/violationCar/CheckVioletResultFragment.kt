package ikish.aftab.ws.android.ui.fragments.violationCar



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import ikish.aftab.ws.android.adapters.ReserveResidenceAdapter
import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.databinding.FragmentCheckVioletResultBinding


class CheckVioletResultFragment : Fragment() {

    private var binding: FragmentCheckVioletResultBinding? = null
    private var viewRoot: View? = null
    lateinit var adapter: ReserveResidenceAdapter
    private var click1:Boolean?=false
    private var click2:Boolean?=false
    private var click3:Boolean?=false
    private var click4:Boolean?=false
    private var check1:Boolean?=false
    private var check2:Boolean?=false
    private var check3:Boolean?=false
    private var check4:Boolean?=false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCheckVioletResultBinding.inflate(layoutInflater)
        viewRoot = binding!!.root



        binding!!.ivChange1.setOnClickListener {
            if (click1!!){
                binding!!.ivChange1.rotation= 0F
                binding!!.layutDetailViolet.visibility=View.GONE
                click1=false
            }else{

                binding!!.ivChange1.rotation= 270F
                binding!!.layutDetailViolet.visibility=View.VISIBLE
                click1=true
            }
        }


        binding!!.ivChange2.setOnClickListener {
            if (click2!!){
                binding!!.ivChange2.rotation= 0F
                binding!!.layutDetailViolet2.visibility=View.GONE
                click2=false
            }else{

                binding!!.ivChange2.rotation= 270F
                binding!!.layutDetailViolet2.visibility=View.VISIBLE
                click2=true
            }
        }

        binding!!.ivChange3.setOnClickListener {
            if (click3!!){
                binding!!.ivChange3.rotation= 0F
                binding!!.layutDetailViolet3.visibility=View.GONE
                click3=false
            }else{

                binding!!.ivChange3.rotation= 270F
                binding!!.layutDetailViolet3.visibility=View.VISIBLE
                click3=true
            }
        }

        binding!!.ivChange4.setOnClickListener {
            checkBackground()
            if (click4!!){
                binding!!.ivChange4.rotation= 0F
                binding!!.layutDetailViolet4.visibility=View.GONE
                click4=false
            }else{

                binding!!.ivChange4.rotation= 270F
                binding!!.layutDetailViolet4.visibility=View.VISIBLE
                click4=true
            }
        }

        binding!!.checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            check1=isChecked
            checkBackground()

        }

        binding!!.checkbox2.setOnCheckedChangeListener { buttonView, isChecked ->
            check2=isChecked
            checkBackground()

        }
        binding!!.checkbox3.setOnCheckedChangeListener { buttonView, isChecked ->
            check3=isChecked
            checkBackground()

        }
        binding!!.checkbox4.setOnCheckedChangeListener { buttonView, isChecked ->
            check4=isChecked
            checkBackground()

        }
        binding!!.ivBackFrament.setOnClickListener {
        findNavController().popBackStack()
        }
        return viewRoot


    }

    private fun checkBackground() {
        if (check1!! ||check2!! || check3!! || check4!!){
            binding!!.btnPaymentViolet.setBackgroundColor(resources.getColor(R.color.topaz))
        }else{
            binding!!.btnPaymentViolet.setBackgroundColor(resources.getColor(R.color.very_light_color))
        }
    }


}