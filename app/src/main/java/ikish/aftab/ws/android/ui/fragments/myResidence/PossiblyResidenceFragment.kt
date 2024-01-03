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
import ikish.aftab.ws.android.databinding.FragmentPossibilityResidenceBinding
import ikish.aftab.ws.android.db.Residence
@AndroidEntryPoint
class PossiblyResidenceFragment : Fragment() {

    private var binding: FragmentPossibilityResidenceBinding? = null
    private var viewRoot: View? = null


    private var countPassenger: Int? = 0
    private var countExtraPassenger: Int? = 0
    private var countRoom: Int? = 0
    private var countBed1: Int? = 0
    private var countBed2: Int? = 0
    private var countOldBed: Int? = 0
    private var ID: Int? = 0


    private var clickButton: String? = null
    private var residence: Residence? = null

    private val viewModel: MyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPossibilityResidenceBinding.inflate(layoutInflater)




            viewRoot = binding!!.root

            ID = PossiblyResidenceFragmentArgs.fromBundle(requireArguments()).id





            binding!!.btnAddPossibility.setOnClickListener {
                if (clickButton.equals("1")) {

                    var possibility:String?=
                        "passenger :"+countPassenger+"/"+
                        "extraPassenger :"+countExtraPassenger+"/"+
                        "room :"+countRoom+"/"+
                        "bed1 :"+countBed1+"/"+
                        "bed2 :"+countBed2+"/"+
                        "oldBed:"+countOldBed+"/"
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
                        PossiblyResidenceFragmentDirections.actionGoToAreaResidenceFragment()
                    action.id=ID!!
                    findNavController().navigate(action)
                }

            }


            binding!!.ivMaxPassenger.setOnClickListener {
                countPassenger = countPassenger!! + 1
                binding!!.tvCountPassenger.setText(countPassenger.toString())
                checkStatusButton()
            }
            binding!!.ivMinPassenger.setOnClickListener {
                if (countPassenger!! >= 1) {
                    countPassenger = countPassenger!! - 1
                    binding!!.tvCountPassenger.setText(countPassenger.toString())
                    checkStatusButton()
                }
            }



            binding!!.ivMaxExtraPassenger.setOnClickListener {
                countExtraPassenger = countExtraPassenger!! + 1
                binding!!.tvCountExtraPassenger.setText(countExtraPassenger.toString())
                checkStatusButton()
            }
            binding!!.ivMinExtraPassenger.setOnClickListener {
                if (countExtraPassenger!! >= 1) {
                    countExtraPassenger = countExtraPassenger!! - 1
                    binding!!.tvCountExtraPassenger.setText(countExtraPassenger.toString())
                    checkStatusButton()
                }
            }






            binding!!.ivMaxRoom.setOnClickListener {
                countRoom = countRoom!! + 1
                binding!!.tvCountRoom.setText(countRoom.toString())
                checkStatusButton()
            }
            binding!!.ivMinRoom.setOnClickListener {
                if (countRoom!! >= 1) {
                    countRoom = countRoom!! - 1
                    binding!!.tvCountRoom.setText(countRoom.toString())
                    checkStatusButton()
                }
            }





            binding!!.ivMaxBed1.setOnClickListener {
                countBed1 = countBed1!! + 1
                binding!!.tvCountBed1.setText(countBed1.toString())
                checkStatusButton()
            }
            binding!!.ivMinBed1.setOnClickListener {
                if (countBed1!! >= 1) {
                    countBed1 = countBed1!! - 1
                    binding!!.tvCountBed1.setText(countBed1.toString())
                    checkStatusButton()
                }
            }





            binding!!.ivMaxBed2.setOnClickListener {
                countBed2 = countBed2!! + 1
                binding!!.tvCountBed2.setText(countBed2.toString())
                checkStatusButton()
            }
            binding!!.ivMinBed2.setOnClickListener {
                if (countBed2!! >= 1) {
                    countBed2 = countBed2!! - 1
                    binding!!.tvCountBed2.setText(countBed2.toString())
                    checkStatusButton()
                }
            }


            binding!!.ivMaxOldBed.setOnClickListener {
                countOldBed = countOldBed!! + 1
                binding!!.tvCountOldBed.setText(countOldBed.toString())
                checkStatusButton()
            }
            binding!!.ivMinOldBed.setOnClickListener {
                if (countOldBed!! >= 1) {
                    countOldBed = countOldBed!! - 1
                    binding!!.tvCountOldBed.setText(countOldBed.toString())
                    checkStatusButton()
                }
            }


        object : AsyncTask<Any?, Any?, Any?>() {
            override fun onPreExecute() {
                super.onPreExecute()
            }



            override fun onPostExecute(o: Any?) {
                if (residence!=null && !residence!!.PO.equals("")){

                    countPassenger=residence!!.PO!!.split("/").get(0).split(":").get(1).toInt()
                    countExtraPassenger=residence!!.PO!!.split("/").get(1).split(":").get(1).toInt()
                    countRoom=residence!!.PO!!.split("/").get(2).split(":").get(1).toInt()
                    countBed1=residence!!.PO!!.split("/").get(3).split(":").get(1).toInt()
                    countBed2=residence!!.PO!!.split("/").get(4).split(":").get(1).toInt()
                    countOldBed=residence!!.PO!!.split("/").get(5).split(":").get(1).toInt()
                    binding!!.tvCountPassenger.setText(countPassenger.toString())
                    binding!!.tvCountExtraPassenger.setText(countExtraPassenger.toString())
                    binding!!.tvCountRoom.setText(countRoom.toString())
                    binding!!.tvCountBed1.setText(countBed1.toString())
                    binding!!.tvCountBed2.setText(countBed2.toString())
                    binding!!.tvCountOldBed.setText(countOldBed.toString())

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



        binding!!.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        return viewRoot


    }


    private fun checkStatusButton() {
        if (
            countPassenger!! > 0 &&
            countExtraPassenger!! > 0 &&
            countRoom!! > 0

        ) {
            clickButton = "1"
            binding!!.btnAddPossibility.setBackgroundColor(resources.getColor(R.color.topaz))


        } else {
            clickButton = "0"
            binding!!.btnAddPossibility.setBackgroundColor(resources.getColor(R.color.very_light_pink))

        }

    }


}