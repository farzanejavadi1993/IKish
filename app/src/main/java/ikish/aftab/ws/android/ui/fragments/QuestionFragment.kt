package ikish.aftab.ws.android.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ikish.aftab.ws.android.adapters.QuestionListAdapter
import ikish.aftab.ws.android.fetchData.MyViewModel
import ikish.aftab.ws.android.model.QuestionModel
import ikish.aftab.ws.android.databinding.FragmentQuestionBinding
@AndroidEntryPoint
class QuestionFragment : Fragment() {

    //region Parameter
    private var binding: FragmentQuestionBinding? = null
    private var viewRoot: View? = null
    lateinit var questionAdapter: QuestionListAdapter
    private val viewModel: MyViewModel by viewModels()
    //endregion Parameter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentQuestionBinding.inflate(layoutInflater)



            viewRoot = binding!!.root

        binding!!.questionRecyclerView.setHasFixedSize(true);
        binding!!.questionRecyclerView.setLayoutManager(LinearLayoutManager(activity))
        binding!!.questionRecyclerView.setItemAnimator(DefaultItemAnimator())
        questionAdapter = QuestionListAdapter()
        binding!!.questionRecyclerView.adapter = questionAdapter







        viewModel!!.getAllQuestion()!!.observe(requireActivity(), object : Observer<List<QuestionModel>> {

            override fun onChanged(t: List<QuestionModel>?) {


                questionAdapter.addData(t!!)
                questionAdapter.notifyDataSetChanged()

            }

        })


        binding!!.askIvBack.setOnClickListener {
            findNavController().popBackStack()
        }
        return viewRoot
    }
}