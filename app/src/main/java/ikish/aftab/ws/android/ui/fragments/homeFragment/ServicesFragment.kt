package ikish.aftab.ws.android.ui.fragments.homeFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ikish.aftab.ws.android.databinding.FragmentServiceBinding

class ServicesFragment : Fragment() {

    private var binding: FragmentServiceBinding? = null
    private var viewRoot: View? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentServiceBinding.inflate(layoutInflater)


        if(viewRoot==null)
        viewRoot = binding!!.root

        binding!!.AirPlane.setOnClickListener {
            val action=ServicesFragmentDirections.actionGoToAirPlaneFragment()
            findNavController().navigate(action)
        }

        binding!!.CardApartmentReserve.setOnClickListener {
            val action=ServicesFragmentDirections.actionGoToCalendarFragment()
            action.mode="2"
            action.type="2"
            action.flag="0"
            findNavController().navigate(action)

        }


        binding!!.cardRentCar.setOnClickListener {
            val action=ServicesFragmentDirections.actionGoToCalendarFragment()
            action.mode="2"
            action.type="3"
            action.flag="0"
            findNavController().navigate(action)
        }

        binding!!.cardVioletCar.setOnClickListener {
            val action=ServicesFragmentDirections.actionGoToCarVioletFragment()
            findNavController().navigate(action)
        }


        binding!!.eventBotton.setOnClickListener {
            val action=ServicesFragmentDirections.actionGoToEventFragment()
            findNavController().navigate(action)
        }
        binding!!.KishTourBotton.setOnClickListener {
            val action=ServicesFragmentDirections.actionGoToMapFragment()
            action.name="kish"
            action.title="map"
            findNavController().navigate(action)
        }


        binding!!.payBillsBotton.setOnClickListener {
            val action=ServicesFragmentDirections.actionGoToPayBillsFragment()
            findNavController().navigate(action)
        }


        binding!!.servicesRlTourPlace.setOnClickListener {
            val action=ServicesFragmentDirections.actionGoToHotDiscountFragment()
            findNavController().navigate(action)
        }

        return viewRoot


    }


}