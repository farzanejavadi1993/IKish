package ikish.aftab.ws.android.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import ikish.aftab.ws.android.databinding.FragmentMyDiscountCodeBinding

class MyDiscountCodeFragment : Fragment() {

    private var binding: FragmentMyDiscountCodeBinding? = null
    private var viewRoot: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMyDiscountCodeBinding.inflate(layoutInflater)
        viewRoot = binding!!.root



        return viewRoot


    }


}