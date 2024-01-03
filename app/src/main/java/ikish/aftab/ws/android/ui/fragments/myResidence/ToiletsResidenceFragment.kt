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
import ikish.aftab.ws.android.fetchData.MyViewModel
import ikish.aftab.ws.android.R

import ikish.aftab.ws.android.databinding.FragmentToiletsResidenceBinding
import ikish.aftab.ws.android.db.Residence
@AndroidEntryPoint
class ToiletsResidenceFragment : Fragment() {

    //region Parameter
    private var binding: FragmentToiletsResidenceBinding? = null
    private var viewRoot: View? = null
    private var countBathRoom: Int? = 0
    private var countIranToilet: Int? = 0
    private var countEuropeToilet: Int? = 0
    private var clickButton: String? = null
    private val viewModel: MyViewModel by viewModels()
    private var ID: Int? = 0
    private var residence: Residence? = null
    //endregion Parameter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentToiletsResidenceBinding.inflate(layoutInflater)


        clickButton = "0"
        if (viewRoot != null) {

            clickButton = "1"
        }else{
            viewRoot = binding!!.root
        }


        ID = ToiletsResidenceFragmentArgs.fromBundle(requireArguments()).id

        object : AsyncTask<Any?, Any?, Any?>() {
            override fun onPreExecute() {
                super.onPreExecute()
            }


            override fun onPostExecute(o: Any?) {
                if (residence != null && !residence!!.TO!!.equals("")) {
                    countBathRoom = residence!!.TO!!.split("/").get(0).split(":").get(1).toInt()
                    countIranToilet = residence!!.TO!!.split("/").get(1).split(":").get(1).toInt()
                    countEuropeToilet = residence!!.TO!!.split("/").get(2).split(":").get(1).toInt()
                    binding!!.tvCountBathRoom.setText(countBathRoom.toString())
                    binding!!.tvCountEuropeToilet.setText(countEuropeToilet.toString())
                    binding!!.tvCountIranToilet.setText(countIranToilet.toString())
                    checkStatusButton()
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
        binding!!.tvCountBathRoom.setText(countBathRoom.toString())
        binding!!.tvCountIranToilet.setText(countIranToilet.toString())
        binding!!.tvCountEuropeToilet.setText(countEuropeToilet.toString())


        binding!!.ivMaxBathRoom.setOnClickListener {
            countBathRoom = countBathRoom!! + 1
            binding!!.tvCountBathRoom.setText(countBathRoom.toString())
            checkStatusButton()
        }
        binding!!.ivMinBathRoom.setOnClickListener {
            if (countBathRoom!! >= 1) {
                countBathRoom = countBathRoom!! - 1
                binding!!.tvCountBathRoom.setText(countBathRoom.toString())
                checkStatusButton()
            }
        }


        binding!!.ivMaxIranToilet.setOnClickListener {
            countIranToilet = countIranToilet!! + 1
            binding!!.tvCountIranToilet.setText(countIranToilet.toString())
            checkStatusButton()
        }
        binding!!.ivMinIranTiolet.setOnClickListener {
            if (countIranToilet!! >= 1) {
                countIranToilet = countIranToilet!! - 1
                binding!!.tvCountIranToilet.setText(countIranToilet.toString())
                checkStatusButton()
            }
        }


        binding!!.ivMaxEuropeToilet.setOnClickListener {
            countEuropeToilet = countEuropeToilet!! + 1
            binding!!.tvCountEuropeToilet.setText(countEuropeToilet.toString())
            checkStatusButton()
        }
        binding!!.ivMinEuropeToilet.setOnClickListener {
            if (countEuropeToilet!! >= 1) {
                countEuropeToilet = countEuropeToilet!! - 1
                binding!!.tvCountEuropeToilet.setText(countEuropeToilet.toString())
                checkStatusButton()
            }
        }

        binding!!.btnContinueToilet.setOnClickListener {
            if (clickButton.equals("1")) {
                var toilet: String? =
                    "bathRoom :" + countBathRoom + "/" +
                            "iran :" + countIranToilet + "/" +
                            "europe :" + countEuropeToilet

                val res = Residence(
                    ID!!,
                    residence!!.NM,
                    residence!!.Ab,
                    residence!!.TY,
                    residence!!.ST,
                    residence!!.PO!!,
                    residence!!.AR,
                    residence!!.CAR,
                    toilet!!,
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
                    ToiletsResidenceFragmentDirections.actionGoToLocationResidenceFragment()
                action.id=ID!!
                findNavController().navigate(action)
            }

        }



        binding!!.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        return viewRoot


    }


    private fun checkStatusButton() {
        if (
            countIranToilet!! > 0
        ) {
            clickButton = "1"
            binding!!.btnContinueToilet.setBackgroundColor(resources.getColor(R.color.topaz))

        } else {
            clickButton = "0"
            binding!!.btnContinueToilet.setBackgroundColor(resources.getColor(R.color.very_light_pink))

        }

    }


}