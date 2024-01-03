package ikish.aftab.ws.android.ui.fragments.bill

import ikish.aftab.ws.android.databinding.FragmentDetailPayBillsBinding


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ikish.aftab.ws.android.fetchData.MyViewModel
import ikish.aftab.ws.android.db.Bill
import kotlin.random.Random

@AndroidEntryPoint
class DetailPayBillsFragment : Fragment() {

    private lateinit var binding: FragmentDetailPayBillsBinding
    private lateinit var viewRoot: View
    private val viewModel: MyViewModel by viewModels()
    private var type:String?=""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailPayBillsBinding.inflate(layoutInflater)
        viewRoot = binding!!.root


        type= DetailPayBillsFragmentArgs.fromBundle(requireArguments()).type

        if (type.equals("2")){
            binding!!.btnPayBills.visibility=View.GONE
        }

        var id: Int? = Random(System.nanoTime()).nextInt(100000) + 0
        binding!!.btnPayBills.setOnClickListener {
            val bill = Bill(
                id!!,
                "۱۴۰۰/۱۲/۰۳",
                "۳۲،۰۰۰ تومان"

            )
            viewModel.insertBill(bill)

            findNavController().popBackStack()

        }

        binding!!.ivBackFrament.setOnClickListener {
            findNavController().popBackStack()
        }


        return viewRoot


    }


}