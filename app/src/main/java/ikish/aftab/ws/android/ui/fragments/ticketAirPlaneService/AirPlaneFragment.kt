package ikish.aftab.ws.android.ui.fragments.ticketAirPlaneService

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ikish.aftab.ws.android.ui.fragments.homeFragment.ServicesFragmentDirections
import ikish.aftab.ws.android.ui.fragments.passenger.PassengerListFragment
import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.classes.calendarLibrary.PassengerTypeDialog
import ikish.aftab.ws.android.databinding.FragmentAirPlaneBinding


class AirPlaneFragment :Fragment() , View.OnClickListener{
    //region Parameter
    private var binding: FragmentAirPlaneBinding? = null
    private var viewRoot: View? = null
    private var change: Boolean? = false


    //endregion Parameter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAirPlaneBinding.inflate(layoutInflater)
        viewRoot = binding!!.root




        binding!!.tvOneWay.setOnClickListener(this)
        binding!!.tvRoundAndRound.setOnClickListener(this)


        binding!!.tvOrigin.setText(nameOrigin)
        binding!!.tvDestination.setText(nameDestination)
        binding!!.tvDateMove.setText(dateMove)
        binding!!.tvNumberOfPassenger.setText(nameNumberOfPassenfer)
        mode = mode


       if (mode.equals("2")){
           val size: Int = binding!!.tvOneWay.getWidth()
           binding!!.select.animate().x(size.toFloat()).setDuration(100)
           binding!!.tvOneWay.setTextColor(resources.getColor(R.color.invalid_name_color))
           binding!!.tvRoundAndRound.setTextColor(resources.getColor(R.color.topaz))
        }




        binding!!.btnOrigin.setOnClickListener {
            callFragment("1")
        }
        binding!!.btnDestination.setOnClickListener {
            callFragment("2")
        }



        binding!!.ivChange.setOnClickListener {
            if (!change!! && !nameOrigin.equals("مبدا را انتخاب کنید") && !nameDestination.equals("مقصد را انتخاب کنید") ){
                binding!!.tvDestination.setText(nameOrigin)
                binding!!.tvOrigin.setText(nameDestination)
                change=true
            }else if (change!! && !nameOrigin.equals("مبدا را انتخاب کنید") && !nameDestination.equals("مقصد را انتخاب کنید") ){
                binding!!.tvDestination.setText(nameDestination)
                binding!!.tvOrigin.setText(nameOrigin)
                change=false
            }else{
                Toast.makeText(activity,"ابتدا مبدا و مقصد را مشخص کنید" ,Toast.LENGTH_SHORT).show()
            }

        }



        binding!!.ivBackFrament.setOnClickListener {
            activity?.finish()
        }



        binding!!.btnDateMove.setOnClickListener {


            val action=ServicesFragmentDirections.actionGoToCalendarFragment()
            action.mode= mode!!
            action.type= "1"
            action.flag= "0"
            findNavController().navigate(action)
        }


        binding!!.btnNumberOfPassengers.setOnClickListener {
            nameNumberOfPassenfer ="تعداد مسافران"
            binding!!.tvNumberOfPassenger.setText(nameNumberOfPassenfer)
            val awesomeTimePickerDialog = PassengerTypeDialog(activity, requireActivity().getString(R.string.select_time),
                object : PassengerTypeDialog.DialogCallback {
                    override fun onClickSelected(name:String) {
                        nameNumberOfPassenfer =name
                        binding!!.tvNumberOfPassenger.setText(name)
                    }
                    override fun onCancel() {

                    }
                })
            awesomeTimePickerDialog.showDialog()
        }

        binding!!.btnSearchTicket.setOnClickListener {
            if (
                nameOrigin.equals("مبدا را انتخاب کنید") ||
                nameDestination.equals("مقصد را انتخاب کنید") ||
                nameNumberOfPassenfer.equals("تعداد مسافران") ||
                dateMove.equals( "تاریخ حرکت")
                    ){
                Toast.makeText(activity,"تمام فیلد هارا پر کنید.",Toast.LENGTH_SHORT).show()
            }else{
                val action=AirPlaneFragmentDirections.actionGoToFlightFragment()
                findNavController().navigate(action)
            }

        }


        binding!!.ivBackFrament.setOnClickListener {
            findNavController().popBackStack()
        }

        return viewRoot
    }

    companion object {
        var nameOrigin: String? = "مبدا را انتخاب کنید"
        var nameDestination: String? = "مقصد را انتخاب کنید"
        var nameNumberOfPassenfer: String? = "تعداد مسافران"
        var dateMove: String? = "تاریخ حرکت"
        var mode: String? = "1"
    }

    private fun callFragment(type: String) {
        val action=AirPlaneFragmentDirections.actionGoToSearchLocationFragment()
        action.type=type
        findNavController().navigate(action)
    }

    override fun onClick(v: View?) {
        dateMove = "تاریخ حرکت"
        binding!!.tvDateMove.setText(dateMove)
        if (v!!.id == R.id.tv_one_way) {
            mode = "1"
            val size: Int = binding!!.tvRoundAndRound.getWidth()
            binding!!.select.animate().x(size.toFloat()).setDuration(100)
            binding!!.tvOneWay.setTextColor(resources.getColor(R.color.topaz))
            binding!!.tvRoundAndRound.setTextColor(resources.getColor(R.color.invalid_name_color))

        }
        else if (v!!.id == R.id.tv_round_and_round) {
            mode = "2"
            binding!!.tvOneWay.setTextColor(resources.getColor(R.color.invalid_name_color))
            binding!!.tvRoundAndRound.setTextColor(resources.getColor(R.color.topaz))
            binding!!.select.animate().x(0f).setDuration(100)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        nameOrigin = "مبدا را انتخاب کنید"
        nameDestination = "مقصد را انتخاب کنید"
        nameNumberOfPassenfer = "تعداد مسافران"
        dateMove = "تاریخ حرکت"
        mode = "1"
        PassengerListFragment.TYPE ="1"
    }

}