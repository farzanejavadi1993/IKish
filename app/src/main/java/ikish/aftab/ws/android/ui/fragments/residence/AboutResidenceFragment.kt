package ikish.aftab.ws.android.ui.fragments.residence


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

import ikish.aftab.ws.android.db.Transactions

import ikish.aftab.ws.android.databinding.AboutResidenceBinding


@AndroidEntryPoint
class AboutResidenceFragment : Fragment() {

    private lateinit var binding: AboutResidenceBinding
    private var viewRoot: View? = null
    private var title: String? = null
    lateinit var adapter: TransactionAdapter
    private val viewModel: MyViewModel by viewModels()
    private var list:List<Transactions>?=ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = AboutResidenceBinding.inflate(layoutInflater)
        viewRoot = binding!!.root

        title= AboutResidenceFragmentArgs.fromBundle(requireArguments()).title
        var name= AboutResidenceFragmentArgs.fromBundle(requireArguments()).name
       var type= AboutResidenceFragmentArgs.fromBundle(requireArguments()).type

      if(!title.equals("map"))
      binding!!.tvTitle.setText("")

        if (name.equals("about")){
            binding!!.tvAbout.visibility=View.VISIBLE

        }
        else if (name.equals("comments")){

            binding!!.commentLayout.visibility=View.VISIBLE
        }
        else if (name.equals("rules_residence")){
            binding!!.rolesResidenceLayout.visibility=View.VISIBLE
        }
        else if (name.equals("rules_cancel")){
            binding!!.rolesCancelLayout.visibility=View.VISIBLE
        }
        else if (name.equals("possibility")){
            if(type!=null && type.equals("3"))
                binding!!.posibilityLayoutCar.visibility=View.VISIBLE

                else
                binding!!.posibilityLayout.visibility=View.VISIBLE


        }
        else if (name.equals("transaction")){

                binding!!.transactionLayout.visibility=View.VISIBLE


            object : AsyncTask<Any?, Any?, Any?>() {
                override fun onPreExecute() {
                    super.onPreExecute()
                }


                override fun onPostExecute(o: Any?) {
                    if (list!!.size>0) {
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

            /*viewModel!!.getAllTransactions()!!.observe(requireActivity(), object :
                Observer<List<Transactions>> {

                override fun onChanged(t: List<Transactions>?) {
                    var list = t
                    if (list!!.size>0) {

                        binding!!.tvNotFound.visibility=View.GONE
                        binding!!.tvNotFoundDescription.visibility=View.GONE
                        binding!!.ivNotFound.visibility=View.GONE
                        binding!!.recyclerTransaction.setLayoutManager(LinearLayoutManager(activity))
                        binding!!.recyclerTransaction.setItemAnimator(DefaultItemAnimator())
                        adapter = TransactionAdapter()
                        adapter.addData(list!!)
                        binding!!.recyclerTransaction.adapter = adapter
                    }


                }

            })*/


        }

        binding!!.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        return viewRoot


    }




}