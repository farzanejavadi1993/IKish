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
import ikish.aftab.ws.android.databinding.FragmentAreaResidenceBinding
import ikish.aftab.ws.android.db.Residence
@AndroidEntryPoint
class AreaResidenceFragment : Fragment() {

    private var binding: FragmentAreaResidenceBinding? = null
    private var viewRoot: View? = null
    private var ID: Int? = 0
    private var residence: Residence? = null
    private val viewModel: MyViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAreaResidenceBinding.inflate(layoutInflater)




            viewRoot = binding!!.root



            ID = AreaResidenceFragmentArgs.fromBundle(requireArguments()).id
            object : AsyncTask<Any?, Any?, Any?>() {
                override fun onPreExecute() {
                    super.onPreExecute()
                }


                override fun onPostExecute(o: Any?) {
                    if (residence!=null){
                        binding!!.edtArea.setText(residence!!.AR)
                        binding!!.edtCompleteArea.setText(residence!!.CAR)
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



            binding!!.btnAddAreaResidence.setOnClickListener {

                val res = Residence(
                    ID!!,
                    residence!!.NM,
                    residence!!.Ab,
                    residence!!.TY,
                    residence!!.ST,
                    residence!!.PO,
                    binding!!.edtArea.text.toString(),
                    binding!!.edtCompleteArea.text.toString(),
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
                val action = AreaResidenceFragmentDirections.actionGoToToiletsResidenceFragment()
                action.id=ID!!
                findNavController().navigate(action)
            }
            binding!!.ivBack.setOnClickListener {
                findNavController().popBackStack()
            }


        return viewRoot


    }


}