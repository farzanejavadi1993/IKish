package ikish.aftab.ws.android.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import ikish.aftab.ws.android.databinding.FragmentAnnouncementsBinding


class AnnouncementsFragment : Fragment() {

    private var binding: FragmentAnnouncementsBinding? = null
    private var viewRoot: View? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAnnouncementsBinding.inflate(layoutInflater)
        viewRoot = binding!!.root


        return viewRoot


    }


}