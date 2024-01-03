package ikish.aftab.ws.android.ui.fragments.myResidence


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
import ikish.aftab.ws.android.adapters.MyResidenceAdapter
import ikish.aftab.ws.android.fetchData.MyViewModel
import ikish.aftab.ws.android.ui.fragments.TabFragmentDirections
import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.databinding.FragmentMyResidenceBinding

import ikish.aftab.ws.android.db.Residence
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.collections4.Predicate

@AndroidEntryPoint
class MyResidenceFragment() : Fragment() {

    private lateinit var binding: FragmentMyResidenceBinding
    private var viewRoot: View? = null
    private lateinit var adapter: MyResidenceAdapter
    var list:List<Residence>?=ArrayList()
    private val viewModel: MyViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyResidenceBinding.inflate(layoutInflater)

            viewRoot = binding!!.root




        if(TYPE.equals("3")){
            binding!!.tvNotFindDescription.setText(requireActivity().resources.getString(R.string.my_residence_not_find_description_car))
            binding!!.btnAddResidence.setText(requireActivity().resources.getString(R.string.my_residence_add_btn_car))
            binding!!.btnAddResidenceMini.setText(requireActivity().resources.getString(R.string.my_residence_add_btn_car))
        }




        adapter = MyResidenceAdapter()
        binding!!.recyclerMyResidence.setHasFixedSize(true)
        binding!!.recyclerMyResidence.setLayoutManager(LinearLayoutManager(activity))
        binding!!.recyclerMyResidence.setItemAnimator(DefaultItemAnimator())
        binding!!.recyclerMyResidence.adapter = adapter


       object : AsyncTask<Any?, Any?, Any?>() {
            override fun onPreExecute() {
                super.onPreExecute()
            }


            override fun onPostExecute(o: Any?) {

                CollectionUtils.filter(
                    list,
                    Predicate<Residence> { r: Residence -> r.T.equals(TYPE) })
                if (list!!.size>0){
                    binding!!.ivIconEmpty.visibility=View.GONE
                    binding!!.tvNotFind.visibility=View.GONE
                    binding!!.tvNotFindDescription.visibility=View.GONE
                    binding!!.btnAddResidence.visibility=View.GONE
                    binding!!.btnAddResidenceMini.visibility=View.VISIBLE
                    adapter!!.addData(list!!,activity!!)
                    adapter!!.notifyDataSetChanged()
                }
                super.onPostExecute(o)
            }

            override fun doInBackground(params: Array<Any?>): Any? {
                try {
                    list = viewModel!!.getAllResidence()

                } catch (e: Exception) {
                    val y = 0
                }
                return null
            }
        }.execute(0)



        binding!!.btnAddResidence.setOnClickListener {
            val action = TabFragmentDirections.actionGoToTypePlaceResidenceFragment()
            action.type= TYPE!!
            findNavController().navigate(action)
        }

        binding!!.btnAddResidenceMini.setOnClickListener {
            val action = TabFragmentDirections.actionGoToTypePlaceResidenceFragment()
            action.type= TYPE!!
            action.id=0
            findNavController().navigate(action)
        }





        adapter!!.OnItemClickListener(object : MyResidenceAdapter.ClickItem {
            override fun onClick(id: Int, t: String) {
               if (t.equals("edit")){
                   val action = TabFragmentDirections.actionGoToTypePlaceResidenceFragment()
                   action.type= TYPE!!
                   action.id= id
                   findNavController().navigate(action)
               }else if(t.equals("comment")){
                   val action = TabFragmentDirections.actionGoToAboutResidenceFragment()
                   action.title= "مشاهده نظرات"
                   action.name="comments"
                   findNavController().navigate(action)
               }
            }

        })

        return viewRoot


    }





    companion object {

        var TYPE: String? = ""
    }

    override fun onDestroy() {
        super.onDestroy()
        TYPE =""
    }




}