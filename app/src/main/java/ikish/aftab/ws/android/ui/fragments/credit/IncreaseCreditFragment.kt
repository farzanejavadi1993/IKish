package ikish.aftab.ws.android.ui.fragments.credit

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.databinding.FragmentIncreaseCreditBinding

class IncreaseCreditFragment : Fragment() {

    private var binding: FragmentIncreaseCreditBinding? = null
    private var viewRoot: View? = null
    private var rial: String? = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentIncreaseCreditBinding.inflate(layoutInflater)
        viewRoot = binding!!.root

        binding!!.edtPrice.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s!!.isEmpty()){
                    binding!!.btnPaymentCredit.isEnabled=false
                    binding!!.btnPaymentCredit.setBackgroundColor(resources.getColor(R.color.very_light_color))
                    binding!!.layoutEdtPrice.setBackgroundDrawable(resources.getDrawable(R.drawable.de_active_invalid_name_border))
                    binding!!.card5Handread.setBackgroundDrawable(resources.getDrawable(R.drawable.item_passenger_radus))
                    binding!!.card1Milion.setBackgroundDrawable(resources.getDrawable(R.drawable.item_passenger_radus))
                    binding!!.card2Milion.setBackgroundDrawable(resources.getDrawable(R.drawable.item_passenger_radus))
                    binding!!.tv5Handread.setTextColor(resources.getColor(R.color.medium_color))
                    binding!!.tv1Milion.setTextColor(resources.getColor(R.color.medium_color))
                    binding!!.tv2Milion.setTextColor(resources.getColor(R.color.medium_color))
                    binding!!.tvUnitPrice.visibility=View.GONE


                }else
                {
                    rial=s.toString()
                    binding!!.btnPaymentCredit.isEnabled=true
                    binding!!.btnPaymentCredit.setBackgroundColor(resources.getColor(R.color.topaz))
                    binding!!.layoutEdtPrice.setBackgroundDrawable(resources.getDrawable(R.drawable.active_invalid_name_border))

                }
            }
        })

        binding!!.card1Milion.setOnClickListener {
            binding!!.edtPrice.setText("1,000,000")
            binding!!.tvUnitPrice.visibility=View.VISIBLE
            rial="1,000,000"


            binding!!.tv1Milion.setTextColor(resources.getColor(R.color.topaz))
            binding!!.card1Milion.setBackgroundDrawable(resources.getDrawable(R.drawable.topaz_shape_layout))
            binding!!.tv5Handread.setTextColor(resources.getColor(R.color.medium_color))
            binding!!.card5Handread.setBackgroundDrawable(resources.getDrawable(R.drawable.item_passenger_radus))
            binding!!.tv2Milion.setTextColor(resources.getColor(R.color.medium_color))
            binding!!.card2Milion.setBackgroundDrawable(resources.getDrawable(R.drawable.item_passenger_radus))
        }
        binding!!.card5Handread.setOnClickListener {
            binding!!.edtPrice.setText("500,000")
            binding!!.tvUnitPrice.visibility=View.VISIBLE
            rial="500,000"


            binding!!.tv5Handread.setTextColor(resources.getColor(R.color.topaz))
            binding!!.card5Handread.setBackgroundDrawable(resources.getDrawable(R.drawable.topaz_shape_layout))
            binding!!.tv1Milion.setTextColor(resources.getColor(R.color.medium_color))
            binding!!.card1Milion.setBackgroundDrawable(resources.getDrawable(R.drawable.item_passenger_radus))
            binding!!.tv2Milion.setTextColor(resources.getColor(R.color.medium_color))
            binding!!.card2Milion.setBackgroundDrawable(resources.getDrawable(R.drawable.item_passenger_radus))
        }
        binding!!.card2Milion.setOnClickListener {

            binding!!.edtPrice.setText("2,000,000")
            binding!!.tvUnitPrice.visibility=View.VISIBLE
            rial="2,000,000"


            binding!!.tv2Milion.setTextColor(resources.getColor(R.color.topaz))
            binding!!.card2Milion.setBackgroundDrawable(resources.getDrawable(R.drawable.topaz_shape_layout))
            binding!!.tv5Handread.setTextColor(resources.getColor(R.color.medium_color))
            binding!!.card5Handread.setBackgroundDrawable(resources.getDrawable(R.drawable.item_passenger_radus))
            binding!!.tv1Milion.setTextColor(resources.getColor(R.color.medium_color))
            binding!!.card1Milion.setBackgroundDrawable(resources.getDrawable(R.drawable.item_passenger_radus))
        }


        binding!!.btnPaymentCredit.setOnClickListener {
            CreditFragment.Rial =rial + "تومان"
            requireFragmentManager().popBackStack()

        }
        binding!!.ivBackFrament.setOnClickListener {
            findNavController().popBackStack()
        }
        return viewRoot


    }


}