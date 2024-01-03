package ikish.aftab.ws.android.ui.fragments.passenger

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import androidx.navigation.fragment.findNavController

import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import ikish.aftab.ws.android.fetchData.MyViewModel
import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.classes.PersianDatePicker
import ikish.aftab.ws.android.databinding.FragmentNewPassengerBinding
import ikish.aftab.ws.android.db.Passenger


import kotlin.random.Random
@AndroidEntryPoint
class NewPassengerFragment : Fragment(), View.OnClickListener {
    //region Parameter
    private var binding: FragmentNewPassengerBinding? = null
    private var viewRoot: View? = null
    private var dialog: Dialog? = null
    private var btnConfirm: MaterialButton? = null
    private var btnCancel: MaterialButton? = null
    private var persianDatePicker: PersianDatePicker? = null
    private var gender: String? = "1"
    private var nationality: String? = "1"
    private var IDC: String? = ""
    private var birthday: String? = ""
    private var passenger: Passenger? = null
    private val viewModel: MyViewModel by viewModels()


    //endregion Parameter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNewPassengerBinding.inflate(layoutInflater)
        viewRoot = binding!!.root


        //region Configuration Dialog

        dialog = Dialog(requireActivity())
        dialog!!.setContentView(R.layout.dialog_calendar)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.setContentView(R.layout.dialog_calendar)
        btnCancel = dialog!!.findViewById(R.id.btn_cancel)
        btnConfirm = dialog!!.findViewById(R.id.btn_confirmation)
        persianDatePicker = dialog!!.findViewById(R.id.persian_date_calendar)
        btnCancel!!.setOnClickListener {
            dialog!!.dismiss()
        }


        btnConfirm!!.setOnClickListener {
            dialog!!.dismiss()
            binding!!.tvCalendar.setText(birthday)
        }

        //endregion Configuration Dialog


        //region Get Bundle For Edit

        IDC = NewPassengerFragmentArgs.fromBundle(requireArguments()).icd

        object : AsyncTask<Any?, Any?, Any?>() {
            override fun onPreExecute() {
                super.onPreExecute()
            }


            override fun onPostExecute(o: Any?) {
                if (passenger != null) {
                    gender = passenger!!.GD
                    nationality = passenger!!.CON
                    binding!!.edtFirstName.setText(passenger!!.FN)
                    binding!!.edtLastName.setText(passenger!!.LN)
                    binding!!.edtEnglishFirstName.setText(passenger!!.EFN)
                    binding!!.edtEnglishLastName.setText(passenger!!.ELN)
                    binding!!.edtNationalCode.setText(passenger!!.NC)
                    binding!!.edtPassportNumber.setText(passenger!!.PC)
                    binding!!.edtMobile.setText(passenger!!.MOB)
                    binding!!.tvCalendar.setText(passenger!!.BRD)
                    birthday = passenger!!.BRD




                    if (nationality.equals("2")) {
                        val size: Int = 0
                        binding!!.select.animate().x(size.toFloat()).setDuration(100)
                        binding!!.iranian.setTextColor(resources.getColor(R.color.invalid_name_color))
                        binding!!.otherCountry.setTextColor(resources.getColor(R.color.topaz))

                        binding!!.layoutPassportNumber.visibility = View.VISIBLE
                        binding!!.layoutFirstName.visibility = View.GONE
                        binding!!.layoutLastName.visibility = View.GONE
                        binding!!.layoutNationalCode.visibility = View.GONE
                        binding!!.tvMobile.setText("شماره تلفن همراه(اختیاری)")
                    }

                    if (gender.equals("2")) {
                        val size: Int = 0
                        binding!!.select1.animate().x(size.toFloat()).setDuration(100)
                        binding!!.tvWoman.setTextColor(resources.getColor(R.color.invalid_name_color))
                        binding!!.tvMan.setTextColor(resources.getColor(R.color.topaz))

                    }


                }

                super.onPostExecute(o)
            }

            override fun doInBackground(params: Array<Any?>): Any? {
                try {
                    if (!IDC.equals(""))
                        passenger = viewModel!!.getPassenger(IDC!!)


                } catch (e: Exception) {
                    val y = 0
                }
                return null
            }
        }.execute(0)

        /* viewModel!!.getPassenger(IDC!!)!!.
         observe(requireActivity(), object :
             Observer<Passenger> {
             override fun onChanged(t: Passenger?) {
                 if (p.equals("")) {


                 }
             }
             })
*/

        //endregion Get Bundle For Edit


        binding!!.calendarCard.setOnClickListener {
            dialog!!.show()
        }


        //region  Switch Button Nationality And Gender
        binding!!.iranian.setOnClickListener(this)
        binding!!.otherCountry.setOnClickListener(this)
        binding!!.tvWoman.setOnClickListener(this)
        binding!!.tvMan.setOnClickListener(this)

        //endregion  Switch Button Nationality And Gender


        binding!!.ivClose.setOnClickListener {
            findNavController().popBackStack()
        }

        //region Action BtnRegister Passenger
        binding!!.btnRegister.setOnClickListener {

            if (
                nationality.equals("1") && (
                        binding!!.tvCalendar.text.toString().equals("") ||
                                binding!!.edtFirstName.text.toString().equals("") ||
                                binding!!.edtLastName.text.toString().equals("") ||
                                binding!!.edtEnglishFirstName.text.toString().equals("") ||
                                binding!!.edtEnglishLastName.text.toString().equals("") ||
                                binding!!.edtMobile.text.toString().equals("") ||
                                binding!!.edtNationalCode.text.toString().equals(""))
            ) {
                Toast.makeText(activity, "تمام فیلد هارا پر کنید", Toast.LENGTH_SHORT).show()
            } else if (
                nationality.equals("2") && (
                        binding!!.tvCalendar.text.toString().equals("") ||
                                binding!!.edtEnglishFirstName.text.toString().equals("") ||
                                binding!!.edtEnglishLastName.text.toString().equals("") ||
                                binding!!.edtPassportNumber.text.toString().equals(""))
            ) {
                Toast.makeText(activity, "تمام فیلد هارا پر کنید", Toast.LENGTH_SHORT).show()
            } else {
                var PC: String? = ""
                var IDCT: String? = ""
                var CN: String? = ""
                var FN: String? = ""
                var LN: String? = ""
                var NC: String? = ""
                var IDCODE: Int? = 0
                if (IDC.equals("")) {
                    IDCODE = Random(System.nanoTime()).nextInt(100000) + 0
                    IDCT = "" + Random(System.nanoTime()).nextInt(100000) + 0
                } else {
                    IDCODE = passenger!!.id
                    IDCT = IDC
                }



                if (nationality.equals("1")) {
                    FN = binding!!.edtFirstName.text.toString()
                    LN = binding!!.edtLastName.text.toString()
                    NC = binding!!.edtNationalCode.text.toString()
                    CN =
                        binding!!.edtFirstName.text.toString() + " " + binding!!.edtLastName.text.toString()

                } else {
                    PC = binding!!.edtPassportNumber.text.toString()
                    CN =
                        binding!!.edtEnglishFirstName.text.toString() + " " + binding!!.edtEnglishLastName.text.toString()
                }

                var psger = Passenger(
                    IDCODE,
                    FN!!,
                    LN!!,
                    binding!!.edtEnglishFirstName.text.toString(),
                    binding!!.edtEnglishLastName.text.toString(),
                    NC!!,
                    PC!!,
                    gender!!,
                    nationality!!,
                    binding!!.edtMobile.text.toString(),
                    CN!!,
                    IDCT!!,
                    birthday!!
                )
                if (!IDC.equals("")) {
                    viewModel.updatePassenger(psger)

                } else {
                    viewModel.insertPassenger(psger)

                }

                findNavController().popBackStack()


            }


        }

       persianDatePicker!!.setOnDateChangedListener(object :
            PersianDatePicker.OnDateChangedListener {
            override fun onDateChanged(newYear: Int, newMonth: Int, newDay: Int) {
                birthday = "" + newYear + "/" + newMonth + "/" + newDay
            }
        })

        //endregion Action BtnRegister Passenger


        return viewRoot
    }


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onClick(view: View?) {

        if (view!!.id == R.id.iranian) {
            nationality = "1"
            val size: Int = binding!!.otherCountry.getWidth()
            binding!!.select.animate().x(size.toFloat()).setDuration(100)
            binding!!.iranian.setTextColor(resources.getColor(R.color.topaz))
            binding!!.otherCountry.setTextColor(resources.getColor(R.color.invalid_name_color))
            binding!!.layoutPassportNumber.visibility = View.GONE
            binding!!.layoutFirstName.visibility = View.VISIBLE
            binding!!.layoutLastName.visibility = View.VISIBLE
            binding!!.layoutNationalCode.visibility = View.VISIBLE
            binding!!.tvMobile.setText("شماره تلفن همراه")

        } else if (view!!.id == R.id.other_country) {
            nationality = "2"
            binding!!.iranian.setTextColor(resources.getColor(R.color.invalid_name_color))
            binding!!.otherCountry.setTextColor(resources.getColor(R.color.topaz))
            binding!!.select.animate().x(0f).setDuration(100)
            binding!!.layoutPassportNumber.visibility = View.VISIBLE
            binding!!.layoutFirstName.visibility = View.GONE
            binding!!.layoutLastName.visibility = View.GONE
            binding!!.layoutNationalCode.visibility = View.GONE
            binding!!.tvMobile.setText("شماره تلفن همراه(اختیاری)")

        }


        if (view!!.id == R.id.tv_woman) {
            gender = "1"
            val size: Int = binding!!.tvMan.getWidth()
            binding!!.select1.animate().x(size.toFloat()).setDuration(100)
            binding!!.tvWoman.setTextColor(resources.getColor(R.color.topaz))
            binding!!.tvMan.setTextColor(resources.getColor(R.color.invalid_name_color))


        } else if (view!!.id == R.id.tv_man) {
            gender = "2"
            binding!!.tvWoman.setTextColor(resources.getColor(R.color.invalid_name_color))
            binding!!.tvMan.setTextColor(resources.getColor(R.color.topaz))
            binding!!.select1.animate().x(0f).setDuration(100)


        }


    }


}