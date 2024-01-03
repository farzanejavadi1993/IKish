package ikish.aftab.ws.android.ui.fragments.credit

import android.graphics.PorterDuff
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint

import ikish.aftab.ws.android.adapters.TransactionAdapter
import ikish.aftab.ws.android.fetchData.MyViewModel
import ikish.aftab.ws.android.R

import ikish.aftab.ws.android.databinding.FragmentCreditBinding
import ikish.aftab.ws.android.db.Transactions

@AndroidEntryPoint
class CreditFragment : Fragment() {

    private lateinit var binding: FragmentCreditBinding
    private var viewRoot: View? = null
    lateinit var adapter: TransactionAdapter
    private val viewModel: MyViewModel by viewModels()
    private var list:List<Transactions>?=ArrayList()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCreditBinding.inflate(layoutInflater)
        viewRoot = binding!!.root




        binding!!.tvCreditRial.setText(Rial)



        val newColor = requireContext().resources.getColor(R.color.white)
      binding!!.ivAddCridit.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP)


        binding!!.addCredit.setOnClickListener {
            val action=CreditFragmentDirections.actionGoToIncreaseCreditFragment()
            findNavController().navigate(action)
        }
        object : AsyncTask<Any?, Any?, Any?>() {
            override fun onPreExecute() {
                super.onPreExecute()
            }


            override fun onPostExecute(o: Any?) {
                if (list!!.size>0) {
                    binding!!.btnSeeAll.visibility=View.VISIBLE
                    binding!!.cardCredit.setBackgroundDrawable(resources.getDrawable(R.drawable.ic_etebar_card))
                    binding!!.tvNotFound.visibility=View.GONE
                    binding!!.tvNotFoundDescription.visibility=View.GONE
                    binding!!.ivNotFound.visibility=View.GONE
                    binding!!.recyclerTransaction.setLayoutManager(LinearLayoutManager(activity))
                    binding!!.recyclerTransaction.setItemAnimator(DefaultItemAnimator())
                    adapter = TransactionAdapter()
                    adapter.addData(list!!)
                    binding!!.recyclerTransaction.adapter = adapter
                }
                super.onPostExecute(o)
            }

            override fun doInBackground(params: Array<Any?>): Any? {
                try {
                    list = viewModel!!.getAllTransactions()

                } catch (e: Exception) {
                    val y = 0
                }
                return null
            }
        }.execute(0)





        binding!!.btnSeeAll.setOnClickListener {
            val action=CreditFragmentDirections.actionGoToAboutResidenceFragment()
            action.title="تراکنش ها"
            action.name="transaction"
            findNavController().navigate(action)
        }

        binding!!.ivBackFrament.setOnClickListener {
           findNavController().popBackStack()
        }
        return viewRoot


    }

    companion object {

        var Rial:String? = "0"
    }

    override fun onDestroy() {
        super.onDestroy()
        Rial ="";
    }


}