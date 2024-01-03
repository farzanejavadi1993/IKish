package ikish.aftab.ws.android.ui.fragments.myResidence

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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import ikish.aftab.ws.android.fetchData.MyViewModel
import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.classes.PersianDatePicker

import ikish.aftab.ws.android.databinding.FragmentRuleResidenceBinding
import ikish.aftab.ws.android.db.Residence
@AndroidEntryPoint
class RuleResidenceFragment : Fragment() {

    //region Parameter
    private var binding: FragmentRuleResidenceBinding? = null
    private var viewRoot: View? = null
    private var countTime :Int ?=0
    private var clickButton : String?=null
    private val viewModel: MyViewModel by viewModels()
    private var ID: Int?=0
    private var residence: Residence?=null
    private lateinit var bsInternal: View
    private lateinit var bsDialog: BottomSheetDialog
    private var time: PersianDatePicker? = null

    private var valuOfTimeDelivery: String? = null
    private var valuOfTimeHandOver: String? = null
    private var btnAddTime: MaterialButton? = null
    private var btnClose: ImageView? = null
    private var tvTitle: TextView? = null
    private var type: String? = ""
    //endregion Parameter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRuleResidenceBinding.inflate(layoutInflater)


          // PersianDatePicker.type="2"
           viewRoot = binding!!.root

           ID = RuleResidenceFragmentArgs.fromBundle(requireArguments()).id
           type = RuleResidenceFragmentArgs.fromBundle(requireArguments()).type

        if (type.equals("3"))
        {
            binding!!.tvTitleLeastTimeRent.setText(requireActivity().resources.getString(R.string.title_least_time_rent_car))
            binding!!.tvLeastTimeRent.setText(requireActivity().resources.getString(R.string.least_time_rent_car))
            binding!!.tvTitleRule.setText(requireActivity().resources.getString(R.string.title_rule_car))
            binding!!.rule1.setText(requireActivity().resources.getString(R.string.rule1_car))
            binding!!.rule2.setText(requireActivity().resources.getString(R.string.rule2_car))
            binding!!.rule3.setText(requireActivity().resources.getString(R.string.rule3_car))
            binding!!.rule4.setText(requireActivity().resources.getString(R.string.rule4_car))
            binding!!.tvTitleTimeDelivery.setText(requireActivity().resources.getString(R.string.title_time_delivery_car))
            binding!!.tvTitleDelivery.setText(requireActivity().resources.getString(R.string.title_delivery_car))
            binding!!.tvDescriptionDelivery.setText(requireActivity().resources.getString(R.string.description_delivery_car))
            binding!!.tvHandOver.setText(requireActivity().resources.getString(R.string.title_hand_over_car))
            binding!!.tvDescriptionHandOver.setText(requireActivity().resources.getString(R.string.description_hand_over_car))

        }



           object : AsyncTask<Any?, Any?, Any?>() {
               override fun onPreExecute() {
                   super.onPreExecute()
               }


               override fun onPostExecute(o: Any?) {
                   if (residence!=null && !residence!!.RUL.equals("")){
                     var leatTime =residence!!.RUL!!.split("/").get(0).split(":").get(1).toInt()
                       binding!!.tvCountTime.setText(leatTime.toString())
                       binding!!.tvDelivery.setText(residence!!.RUL!!.split("/").get(1).split(":").get(1))
                       binding!!.tvHandOver.setText(residence!!.RUL!!.split("/").get(2).split(":").get(1))

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

           binding!!.tvCountTime.setText(countTime.toString())


           binding!!.ivMaxTime.setOnClickListener {
               countTime = countTime!! + 1
               binding!!.tvCountTime.setText(countTime.toString())

           }
           binding!!.ivMinTime.setOnClickListener {
               if (countTime!! >= 1) {
                   countTime = countTime!! - 1
                   binding!!.tvCountTime.setText(countTime.toString())

               }
           }

           binding!!.btnDelivery.setOnClickListener {
               showBottomSheet(1)
           }

           binding!!.btnHandOver.setOnClickListener {
               showBottomSheet(2)
           }
           binding!!.btnContinueRule.setOnClickListener {
               var Rule:String?=
                   "leatTime :"+countTime+"/"+
                   "delivery :"+valuOfTimeDelivery+"/"+
                   "handOver :"+valuOfTimeHandOver+"/"


               val res = Residence(
                   ID!!,
                 residence!!.NM,
                   residence!!.Ab,
                   residence!!.TY,
                   residence!!.ST,
                   residence!!.PO,
                   residence!!.AR,
                   residence!!.CAR,
                   residence!!.TO,
                   residence!!.ADD,
                   residence!!.TEL,
                   residence!!.lat,
                   residence!!.lng,
                   residence!!.IMG,
                   Rule!!,
                   residence!!.PR,
                   residence!!.DS,
                   residence!!.ME,
                   residence!!.NOP,
                   residence!!.RAT,
                   residence!!.HN,
                   residence!!.T

               )
               viewModel.updateResidence(res)
               val action = RuleResidenceFragmentDirections.actionGoToListPriceResidenceFragment()
               action.id=ID!!
               action.type=type!!
               findNavController().navigate(action)
           }





        return viewRoot


    }

    private fun showBottomSheet(location:Int) {

        bsDialog = BottomSheetDialog(requireContext(), R.style.DialogStyle)
        bsDialog.setContentView(R.layout.bottom_sheet_choose_time)
        bsInternal = bsDialog.findViewById(R.id.design_bottom_sheet)!!
        time = bsDialog.findViewById(R.id.persian_date_calendar_rule)
        btnAddTime = bsDialog.findViewById(R.id.btn_add_time)
        btnClose = bsDialog.findViewById(R.id.btn_close_dialog)
        tvTitle = bsDialog.findViewById(R.id.tv_titlee)


        BottomSheetBehavior.from(bsInternal).state = BottomSheetBehavior.STATE_EXPANDED

        if (location==1) {
            if(type!!.equals("3"))
                tvTitle!!.setText(requireActivity().resources.getString(R.string.title_delivery_car))

                else
            tvTitle!!.setText("ساعت ورود (Check in)")

        }
        else {
            if(type!!.equals("3"))
                tvTitle!!.setText(requireActivity().resources.getString(R.string.title_hand_over_car))

            else
                tvTitle!!.setText("ساعت خروج (Check Out)")



        }


//        time!!.setOnDateChangedListener(object :
//            PersianDatePicker.OnDateChangedListener {
//            override fun onDateChanged(newYear: Int, newMonth: Int, newDay: Int) {
//               if (location==1){
//                   valuOfTimeDelivery = ""+newDay
//                   binding!!.tvDelivery.setText(valuOfTimeDelivery)
//               }else{
//                   valuOfTimeHandOver = ""+newDay
//                   binding!!.tvHandOver.setText(valuOfTimeHandOver)
//               }
//
//            }
//        })


        btnAddTime!!.setOnClickListener {
            bsDialog.dismiss()
        }

        btnClose!!.setOnClickListener {
            bsDialog.dismiss()
        }
        bsDialog.show()





    }

    override fun onDestroy() {
        super.onDestroy()
        PersianDatePicker.type=""
    }
}