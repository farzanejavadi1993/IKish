package ikish.aftab.ws.android.ui.fragments.violationCar

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.databinding.FragmentCarViolationBinding


class CarViolationFragment : Fragment() {

    private var binding: FragmentCarViolationBinding? = null
    private var viewRoot: View? = null
    private var click: Boolean? = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCarViolationBinding.inflate(layoutInflater)
        viewRoot = binding!!.root

        binding!!.edtNumberCar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s!!.isEmpty()){
                    click=false
                    binding!!.btnPaymentViolet.isEnabled=false
                    binding!!.btnPaymentViolet.setBackgroundColor(resources.getColor(R.color.very_light_color))
                    binding!!.layoutEdtNumberCar.setBackgroundDrawable(resources.getDrawable(R.drawable.de_active_invalid_name_border))



                }else
                {

                    click=true
                    binding!!.btnPaymentViolet.isEnabled=true
                    binding!!.btnPaymentViolet.setBackgroundColor(resources.getColor(R.color.topaz))
                    binding!!.layoutEdtNumberCar.setBackgroundDrawable(resources.getDrawable(R.drawable.active_invalid_name_border))

                }
            }
        })

        binding!!.btnPaymentViolet.setOnClickListener {
            if (click!!) {
                val action=CarViolationFragmentDirections.actionGoToCheckVioletResultFragment()
                findNavController().navigate(action)
            }
        }

        binding!!.ivBackFrament.setOnClickListener {
           findNavController().popBackStack()
        }


        return viewRoot


    }


}