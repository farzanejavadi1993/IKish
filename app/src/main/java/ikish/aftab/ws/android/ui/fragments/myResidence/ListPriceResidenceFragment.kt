package ikish.aftab.ws.android.ui.fragments.myResidence

import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ikish.aftab.ws.android.fetchData.MyViewModel
import ikish.aftab.ws.android.R

import ikish.aftab.ws.android.databinding.FragmentListPriceResidenceBinding
import ikish.aftab.ws.android.db.Residence

@AndroidEntryPoint
class ListPriceResidenceFragment : Fragment() {

    private var binding: FragmentListPriceResidenceBinding? = null
    private var viewRoot: View? = null
    private var clickButton: String? = null
    private val viewModel: MyViewModel by viewModels()
    private var ID: Int? = 0
    private var type: String? = ""
    private var residence: Residence? = null
    private var textWatcher: TextWatcher? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListPriceResidenceBinding.inflate(layoutInflater)


        viewRoot = binding!!.root

        ID = ListPriceResidenceFragmentArgs.fromBundle(requireArguments()).id
        type = ListPriceResidenceFragmentArgs.fromBundle(requireArguments()).type
        object : AsyncTask<Any?, Any?, Any?>() {
            override fun onPreExecute() {
                super.onPreExecute()
            }


            override fun onPostExecute(o: Any?) {
                if (residence != null && !residence!!.PR.equals("")) {
                    binding!!.edtNaturalPrice.setText(
                        residence!!.PR.split("/").get(0).split(":").get(1)
                    )
                    binding!!.edtEndWeekPrice.setText(
                        residence!!.PR.split("/").get(1).split(":").get(1)
                    )
                    binding!!.edtPriceExtraPassenger.setText(
                        residence!!.PR.split("/").get(3).split(":").get(1)
                    )
                    binding!!.edtPriceVacation.setText(
                        residence!!.PR.split("/").get(2).split(":").get(1)
                    )
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



        binding!!.btnContinueListPrice.setOnClickListener {
            if (clickButton != null && clickButton!!.equals("1")) {
                var Price: String? =
                    "NPrice :" + binding!!.edtNaturalPrice.text.toString() + "/" +
                            "EWPrice :" + binding!!.edtEndWeekPrice.text.toString() + "/" +
                            "WPrice :" + binding!!.edtPriceVacation.text.toString() + "/" +
                            "EPPrice :" + binding!!.edtPriceExtraPassenger.text.toString()
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
                    Price!!,
                    residence!!.DS,
                    residence!!.ME,
                    residence!!.NOP,
                    residence!!.RAT,
                    residence!!.HN,
                    residence!!.T
                )
                viewModel.updateResidence(res)


                val action =
                    ListPriceResidenceFragmentDirections.actionGoToDiscountResidenceFragment()
                action.id=ID!!
                action.type=type!!
                findNavController().navigate(action)
            }

        }

        textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkStatusButton()

            }


        }

        binding!!.edtNaturalPrice.addTextChangedListener(textWatcher)
        binding!!.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        return viewRoot


    }

    private fun checkStatusButton() {
        if (
            binding!!.edtNaturalPrice!!.text.toString().equals("")

        ) {
            clickButton = "0"
            binding!!.btnContinueListPrice.setBackgroundColor(resources.getColor(R.color.very_light_pink))

        } else {
            clickButton = "1"
            binding!!.btnContinueListPrice.setBackgroundColor(resources.getColor(R.color.topaz))

        }

    }

}