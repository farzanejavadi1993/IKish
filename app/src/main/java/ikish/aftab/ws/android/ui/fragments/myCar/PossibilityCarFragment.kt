package ikish.aftab.ws.android.ui.fragments.myCar

import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import ikish.aftab.ws.android.fetchData.MyViewModel
import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.classes.PersianDatePicker
import ikish.aftab.ws.android.databinding.FragmentPossibilityCarBinding
import ikish.aftab.ws.android.db.Residence
@AndroidEntryPoint
class PossibilityCarFragment : Fragment() {

    private var binding: FragmentPossibilityCarBinding? = null
    private var viewRoot: View? = null
    private var countCapacityPassenger: Int? = 0
    private var countDoor: Int? = 0
    private var capacityTrunk: String? = ""
    private var ID: Int? = 0
    private var residence: Residence? = null
    private lateinit var bsInternal: View
    private lateinit var bsDialog: BottomSheetDialog
    private var capacityTrunkBottomSheet: PersianDatePicker? = null
    private var btnAddcapacityTrunkBottomSheet: MaterialButton? = null
    private var btnClose: ImageView? = null
    private var tvTitleBottomSheet: TextView? = null
    private val viewModel: MyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPossibilityCarBinding.inflate(layoutInflater)



            viewRoot = binding!!.root

        ID = PossibilityCarFragmentArgs.fromBundle(requireArguments()).id
        PersianDatePicker.type="2"




        binding!!.btnAddPossibilityCar.setOnClickListener {


                var possibility:String?=
                    "passenger :"+countCapacityPassenger+"/"+
                            "extraPassenger :"+countDoor+"/"+
                            "room :"+binding!!.tvCapacityTrunk.text.toString()

                val res = Residence(
                    ID!!,
                    residence!!.NM,
                    residence!!.Ab,
                    residence!!.TY,
                    residence!!.ST,
                    possibility!!,
                    residence!!.AR,
                    residence!!.CAR,
                    residence!!.TO,
                    residence!!.ADD,
                    residence!!.TEL,
                    residence!!.lat,
                    residence!!.lng,
                    residence!!.IMG,
                    residence!!.RUL,
                    residence!!.PR,
                    residence!!.DS,
                    residence!!.ME,
                    residence!!.NOP,
                    residence!!.RAT,
                    residence!!.HN,
                    residence!!.T
                )
                viewModel.updateResidence(res)
                val action =
                    PossibilityCarFragmentDirections.actionGoToAddPhotoResidenceFragment()
            action.id=ID!!
            action.type="3"
                findNavController().navigate(action)


        }


        binding!!.ivMaxCapacityPassenger.setOnClickListener {
            countCapacityPassenger = countCapacityPassenger!! + 1
            binding!!.tvCountCapacityPassenger.setText(countCapacityPassenger.toString())

        }
        binding!!.ivMinCapacityPassenger.setOnClickListener {
            if (countCapacityPassenger!! >= 1) {
                countCapacityPassenger = countCapacityPassenger!! - 1
                binding!!.tvCountCapacityPassenger.setText(countCapacityPassenger.toString())

            }
        }



        binding!!.ivMaxDoorCar.setOnClickListener {
            countDoor = countDoor!! + 1
            binding!!.tvCountDoorCar.setText(countDoor.toString())

        }
        binding!!.ivMinDoorCar.setOnClickListener {
            if (countDoor!! >= 1) {
                countDoor = countDoor!! - 1
                binding!!.tvCountDoorCar.setText(countDoor.toString())

            }
        }


        binding!!.btnCapacityTrunk.setOnClickListener {
            showBottomSheet()
        }

        object : AsyncTask<Any?, Any?, Any?>() {
            override fun onPreExecute() {
                super.onPreExecute()
            }



            override fun onPostExecute(o: Any?) {
                if (residence!=null && !residence!!.PO.equals("")){

                    countCapacityPassenger=residence!!.PO!!.split("/").get(0).split(":").get(1).toInt()
                    countDoor=residence!!.PO!!.split("/").get(1).split(":").get(1).toInt()
                    capacityTrunk=residence!!.PO!!.split("/").get(2).split(":").get(1)

                    binding!!.tvCountCapacityPassenger.setText(countCapacityPassenger.toString())
                    binding!!.tvCountDoorCar.setText(countDoor.toString())
                    binding!!.tvCapacityTrunk.setText(capacityTrunk)




                }
                super.onPostExecute(o)
            }

            override fun doInBackground(params: Array<Any?>): Any? {
                try {
                    residence = viewModel!!.getResidenceById(ID!!)!!

                } catch (e: Exception) {
                    val y = 0
                }
                return null
            }
        }.execute(0)


        binding!!.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        return viewRoot


    }

    private fun showBottomSheet() {

        bsDialog = BottomSheetDialog(requireContext(), R.style.DialogStyle)
        bsDialog.setContentView(R.layout.bottom_sheet_choose_time)
        bsInternal = bsDialog.findViewById(R.id.design_bottom_sheet)!!
        capacityTrunkBottomSheet = bsDialog.findViewById(R.id.persian_date_calendar_rule)
        btnAddcapacityTrunkBottomSheet = bsDialog.findViewById(R.id.btn_add_time)
        tvTitleBottomSheet = bsDialog.findViewById(R.id.tv_titlee)
        btnClose = bsDialog.findViewById(R.id.btn_close_dialog)


        BottomSheetBehavior.from(bsInternal).state = BottomSheetBehavior.STATE_EXPANDED


            tvTitleBottomSheet!!.setText("ظرفیت صندوق عقب خودرو را مشخص کنید.")



       capacityTrunkBottomSheet!!.setOnDateChangedListener(object :
            PersianDatePicker.OnDateChangedListener {
            override fun onDateChanged(newYear: Int, newMonth: Int, newDay: Int) {
                binding!!.tvCapacityTrunk.setText(  ""+newDay + " لیتر ")


            }
        })


        btnAddcapacityTrunkBottomSheet!!.setOnClickListener {
            bsDialog.dismiss()
        }

        btnClose!!.setOnClickListener {
            bsDialog.dismiss()
        }
        bsDialog.show()





    }



}