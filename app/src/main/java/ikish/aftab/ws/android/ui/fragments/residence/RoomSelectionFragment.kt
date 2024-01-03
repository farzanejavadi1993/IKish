package ikish.aftab.ws.android.ui.fragments.residence

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ikish.aftab.ws.android.R

import ikish.aftab.ws.android.databinding.FragmentRoomSelectionBinding


class RoomSelectionFragment : Fragment() {

    private var binding: FragmentRoomSelectionBinding? = null
    private var viewRoot: View? = null
    private var countRoom :Int ?=0
    private var countYoungPassenger :Int ?=0
    private var countBabyPassenger :Int ?=0
    private var countOldPassenger :Int ?=0
    private var clickButton :Boolean ?=false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRoomSelectionBinding.inflate(layoutInflater)
        viewRoot = binding!!.root

        var day= RoomSelectionFragmentArgs.fromBundle(requireArguments()).day
        binding!!.btnFindPlace.setOnClickListener {
            checkStatusButton()
            if (clickButton!!) {
                val action=RoomSelectionFragmentDirections.actionGoToResidenceFragment()
                action.day=day
                action.type="2"
                findNavController().navigate(action)

            }else{
                Toast.makeText(activity,"مهمان های خود را انتخاب کنید",Toast.LENGTH_SHORT).show()
            }
        }


        binding!!.tvCountRoom.setText(countRoom.toString())
        binding!!.tvCountYoungRoom.setText(countYoungPassenger.toString())
        binding!!.tvCountOldRoom.setText(countOldPassenger.toString())

        binding!!.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding!!.ivMaxRoom.setOnClickListener {
            countRoom= countRoom!!+1
            binding!!.tvCountRoom.setText(countRoom.toString())
            checkStatusButton()
        }
        binding!!.ivMinRoom.setOnClickListener {
            if (countRoom!!>=1) {
                countRoom = countRoom!! - 1
                binding!!.tvCountRoom.setText(countRoom.toString())
                checkStatusButton()
            }

        }

        binding!!.ivMaxOldRoom.setOnClickListener {
            countOldPassenger= countOldPassenger!!+1
            binding!!.tvCountOldRoom.setText(countOldPassenger.toString())
            checkStatusButton()
        }
        binding!!.ivMinOldRoom.setOnClickListener {
            if (countOldPassenger!! >= 1) {
                countOldPassenger = countOldPassenger!! - 1
                binding!!.tvCountOldRoom.setText(countOldPassenger.toString())
                checkStatusButton()
            }
        }



        binding!!.ivMaxYoungRoom.setOnClickListener {
            countYoungPassenger= countYoungPassenger!!+1
            binding!!.tvCountYoungRoom.setText(countYoungPassenger.toString())
            checkStatusButton()
        }
        binding!!.ivMinYoungRoom.setOnClickListener {
            if (countYoungPassenger!! >= 1) {
                countYoungPassenger = countYoungPassenger!! - 1
                binding!!.tvCountYoungRoom.setText(countYoungPassenger.toString())
                checkStatusButton()
            }
        }


        binding!!.ivMaxBabyRoom.setOnClickListener {
            countBabyPassenger= countBabyPassenger!!+1
            binding!!.tvCountBabyRoom.setText(countBabyPassenger.toString())
            checkStatusButton()
        }
        binding!!.ivMinBabyRoom.setOnClickListener {
            if (countBabyPassenger!! >= 1) {
                countBabyPassenger = countBabyPassenger!! - 1
                binding!!.tvCountBabyRoom.setText(countBabyPassenger.toString())
                checkStatusButton()
            }
        }
        return viewRoot


    }

    private fun checkStatusButton() {
      if (countOldPassenger!!>0 && countRoom!!>0) {
          clickButton = true
          binding!!.btnFindPlace.setBackgroundColor(resources.getColor(R.color.topaz))
      }
        else {
          clickButton = false
          binding!!.btnFindPlace.setBackgroundColor(resources.getColor(R.color.very_light_pink))
      }

    }


}