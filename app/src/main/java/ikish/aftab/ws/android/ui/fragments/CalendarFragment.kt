package ikish.aftab.ws.android.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ikish.aftab.ws.android.classes.calendarLibrary.DateRangeCalendarView
import ikish.aftab.ws.android.ui.fragments.residence.PaymentResidenceFragment
import ikish.aftab.ws.android.ui.fragments.ticketAirPlaneService.AirPlaneFragment
import ikish.aftab.ws.android.classes.calendarLibrary.PersianCalendar

import ikish.aftab.ws.android.databinding.FragmentCalendarBinding

class CalendarFragment : Fragment() {

    private var binding: FragmentCalendarBinding? = null
    private var viewRoot: View? = null
    private var mode: String? = null
    private var type: String? = null
    private var flag: String? = null

    //type =1 ورود از بلیط هواپیما
    //type =2 ورود از اجاره آپارتمان
    //type =3 ورود از اجاره خودرو

    private var dateAtach: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentCalendarBinding.inflate(layoutInflater)
        viewRoot = binding!!.root


        type= CalendarFragmentArgs.fromBundle(requireArguments()).type
        mode= CalendarFragmentArgs.fromBundle(requireArguments()).mode
        flag= CalendarFragmentArgs.fromBundle(requireArguments()).flag







        if (mode.equals("1")) {
            binding!!.btnRegistDate.visibility = View.VISIBLE
            binding!!.btnRegistDate.setText("ثبت تاریخ")
            binding!!.calendar.selectionMode = 1
        }
        else if (mode.equals("2")) {
            binding!!.btnRegistDate.visibility = View.GONE
            binding!!.calendar.selectionMode = 2
            if (type.equals("1"))
                binding!!.btnRegistDate.setText("تاریخ رفت و برگشت")
            else
                binding!!.btnRegistDate.setText("رزرو")
        }else{
            binding!!.btnRegistDate.visibility=View.GONE
            binding!!.btnOkPrice.visibility=View.VISIBLE
            binding!!.tvTitle.setText("تقویم قیمت\u200Cها")
            binding!!.tvStartDate.setText("قیمت\u200Cها قابل ویرایش است. برای ویرایش روی روز بزنید.")
            binding!!.calendar.selectionMode = 3
        }


        if (flag != null && flag.equals("1")) {
            binding!!.btnRegistDate.setText("ثبت تاریخ")
        }




        binding!!.btnContinueOkPrice.setOnClickListener {
            if (flag.equals("2")){

            for (i in 0 until 10) {
                findNavController().popBackStack()

            }
            }
            else{
                 for (i in 0 until 7) {
                     findNavController().popBackStack()

                 }
            }
        }



        binding!!.calendar.calendarListener = object : DateRangeCalendarView.CalendarListener {
            override fun onDateSelected(date: PersianCalendar?) {}
            override fun onDateRangeSelected(
                startDate: PersianCalendar?, endDate: PersianCalendar?
            ) {
                if (startDate == null) {

                    binding!!.btnRegistDate.visibility = View.GONE
                    binding!!.tvStartDate.setText("")

                    if (type.equals("1"))
                        binding!!.tvTitle.setText("چه تاریخی می خواهید حرکت کنید ؟")
                    else
                        binding!!.tvTitle.setText("چه تاریخی می خواهید رزرو کنید ؟")

                } else if (startDate != null && mode.equals("2")) {

                    if (type.equals("1"))
                        binding!!.tvTitle.setText("تاریخ برگشت خود را انتخاب کنید")
                    else
                        binding!!.tvTitle.setText("تا چه تاریخی رزرو می کنید ؟")


                } else if (startDate != null) {
                    binding!!.tvStartDate.setText("" + startDate!!.persianDay + " " + startDate.persianMonthName)
                    dateAtach = "" + startDate!!.persianDay + " " + startDate.persianMonthName;
                }

                if (endDate == null) {
                    binding!!.tvStartDate.setText(binding!!.tvStartDate.text.toString())
                    if (mode.equals("2"))
                        binding!!.btnRegistDate.visibility = View.GONE

                } else {
                    binding!!.btnRegistDate.visibility = View.VISIBLE
                    if (type.equals("1"))
                        binding!!.tvStartDate.setText("رفت " + startDate!!.persianDay + " " + startDate!!.persianMonthName + "-" + "برگشت " + endDate!!.persianDay + " " + endDate!!.persianMonthName)
                    else
                        binding!!.tvStartDate.setText("از " + startDate!!.persianDay + " " + startDate!!.persianMonthName + " تا " + endDate!!.persianDay + " " + endDate!!.persianMonthName)

                    dateAtach =
                        "" + startDate!!.persianDay + " " + startDate!!.persianMonthName + "/" + endDate!!.persianDay + " " + endDate!!.persianMonthName
                }
            }

            override fun onCancel() {

            }

        }



        binding!!.ivBackCalendar.setOnClickListener {
            findNavController().popBackStack()
        }



        binding!!.btnRegistDate.setOnClickListener {

            if ((type.equals("2") || type.equals("3"))  && flag.equals("1")) {
                PaymentResidenceFragment.date = dateAtach
                requireFragmentManager().popBackStack()


            }


            //go to flight
            else if (type.equals("1")) {
                AirPlaneFragment.dateMove = dateAtach
                requireFragmentManager().popBackStack()


            }

            else if (type.equals("2")) {

                val action=CalendarFragmentDirections.actionGoToRoomSelectionFragment()
                action.day=dateAtach!!
                    findNavController().navigate(action)
            } else {
                val action=CalendarFragmentDirections.actionGoToResidenceFragment(

                )

                action.day=dateAtach!!
                action.type="3"
                findNavController().navigate(action)
            }

        }



        return viewRoot


    }
}