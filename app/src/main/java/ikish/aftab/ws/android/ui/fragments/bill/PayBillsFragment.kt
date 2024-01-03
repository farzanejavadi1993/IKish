package ikish.aftab.ws.android.ui.fragments.bill



import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.databinding.FragmentPayBillsBinding


class PayBillsFragment : Fragment() {

    private lateinit var binding: FragmentPayBillsBinding
    private lateinit var viewRoot: View
    private lateinit var textWatcher: TextWatcher
    private var clickButton: String ?="0"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPayBillsBinding.inflate(layoutInflater)
        viewRoot = binding!!.root


        textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                checkStatusButton()

            }


        }


        binding.edtpayId.addTextChangedListener(textWatcher)
        binding.edtBillsId.addTextChangedListener(textWatcher)





        binding!!.btnPaymentBills.setOnClickListener {
            if (clickButton.equals("1")) {
                val action=PayBillsFragmentDirections.actionGoToDetailPayBillsFragment()
                action.type="1"
                findNavController().navigate(action)
            }
        }


        binding!!.btnHistory.setOnClickListener {

                val action=PayBillsFragmentDirections.actionGoToHistoryBillsFragment()
                findNavController().navigate(action)

        }

        binding!!.ivBackFrament.setOnClickListener {
            findNavController().popBackStack()
        }


        return viewRoot


    }


    private fun checkStatusButton() {
        if (
            binding!!.edtBillsId!!.text.toString().equals("") ||
            binding!!.edtpayId!!.text.toString().equals("")

        ) {
            clickButton = "0"
            binding!!.btnPaymentBills.setBackgroundColor(resources.getColor(R.color.very_light_pink))

        } else {
            clickButton = "1"
            binding!!.btnPaymentBills.setBackgroundColor(resources.getColor(R.color.topaz))

        }

    }

    fun toEnglishNumber(input: String): String? {
        var input = input
        val persian = arrayOf("۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹")
        val arabic = arrayOf("٠", "١", "٢", "٣", "٤", "٥", "٦", "٧", "٨", "٩")
        for (j in persian.indices) {
            if (input.contains(persian[j])) input = input.replace(persian[j], j.toString())
        }
        for (j in arabic.indices) {
            if (input.contains(arabic[j])) input = input.replace(arabic[j], j.toString())
        }
        return input
    }

}