package ikish.aftab.ws.android.ui.fragments.myResidence

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ikish.aftab.ws.android.classes.calendarLibrary.DateRangeCalendarView
import ikish.aftab.ws.android.fetchData.MyViewModel
import ikish.aftab.ws.android.databinding.FragmentDiscountResidenceBinding
import ikish.aftab.ws.android.db.Residence

@AndroidEntryPoint
class DiscountResidenceFragment : Fragment() {

    private var binding: FragmentDiscountResidenceBinding? = null
    private var viewRoot: View? = null

    private val viewModel: MyViewModel by viewModels()
    private var ID: Int?=0
    private var type: String?=""
    private var residence: Residence?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiscountResidenceBinding.inflate(layoutInflater)
        viewRoot = binding!!.root

        ID = DiscountResidenceFragmentArgs.fromBundle(requireArguments()).id
        type = DiscountResidenceFragmentArgs.fromBundle(requireArguments()).type
        object : AsyncTask<Any?, Any?, Any?>() {
            override fun onPreExecute() {
                super.onPreExecute()
            }


            override fun onPostExecute(o: Any?) {
                if (residence!=null && !residence!!.DS.equals("")){
                    binding!!.edtDiscount28.setText(residence!!.DS!!.split("/").get(1).split(":").get(1))
                    binding!!.edtDiscount5.setText(residence!!.DS!!.split("/").get(0).split(":").get(1))

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

     binding!!.btnContinueDiscount.setOnClickListener {

             var discount:String?=
                 "dis5 :"+binding!!.edtDiscount5.text.toString()+"/"+
             "dis28 :"+binding!!.edtDiscount28.text.toString()

             var NOF=residence!!.PO!!.split("/").get(0).split(":").get(1) + "نفر"
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
                 residence!!.RUL,
                 residence!!.PR,
                 discount!!,
                 "1",
                 NOF!!,
                 "5",
                 "فرزانه جوادی",
                 type!!
             )
             viewModel.updateResidence(res)



         DateRangeCalendarView.type="2"
         val action = DiscountResidenceFragmentDirections.actionGoToCalendarFragment()
         action.mode="3"
         action.type="3"
         action.flag=type!!
         findNavController().navigate(action)





        }

        binding!!.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        return viewRoot


    }

    override fun onDestroy() {
        super.onDestroy()
        DateRangeCalendarView.type="0"
    }

}