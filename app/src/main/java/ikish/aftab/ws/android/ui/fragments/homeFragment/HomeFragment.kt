package ikish.aftab.ws.android.ui.fragments.homeFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.securepreferences.SecurePreferences
import dagger.hilt.android.AndroidEntryPoint
import ikish.aftab.ws.android.adapters.OfferListAdapter
import ikish.aftab.ws.android.adapters.TourKishListAdapter
import ikish.aftab.ws.android.fetchData.MyViewModel
import ikish.aftab.ws.android.databinding.FragmentHomeBinding
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    //region Parameter
    @Inject
    lateinit var  sharedPreferences: SecurePreferences
    private var  uuid: String?=""
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewRoot: View
    private lateinit var offerListAdapter: OfferListAdapter
    private lateinit var tourKishListAdapter: TourKishListAdapter

    private val viewModel: MyViewModel by viewModels()
    //endregion Parameter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        viewRoot = binding.root


      //  val view = App.sharedPreferences.getString("view", "")
        val view = sharedPreferences.getString("view", "")
       //  uuid = sharedPreferences.getString("uuid", "")
  /*      if (view.equals("")) {
            Toast.makeText(activity,"refreshToken",Toast.LENGTH_SHORT).show()
           sharedPreferences.edit().putString("view", "1").apply()
            val refreshToken =sharedPreferences.getString("refresh_token", "")
            viewModel.getResultRefreshToken(
                refreshToken!!,
                uuid!!,
                "android",
                "MGvQLx5UAhsBClG7TeqAFSZTSk1tcRy9HV64crW2"
            )!!.observe(requireActivity(),
                { t ->
                    if (t!!.getIsSuccess() == true && t.getCode() == 202) {

                       sharedPreferences.edit().putString(
                            "access_token",
                            t.getResponse()!!.getTokens()!!.getAccessToken()
                        ).apply()
                       sharedPreferences.edit().putString(
                            "refresh_token",
                            t.getResponse()!!.getTokens()!!.getRefreshToken()
                        ).apply()

                    }
                })


        }*/





        binding.homeRecyclerOffer.setHasFixedSize(true);
        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.homeRecyclerOffer.layoutManager = mLayoutManager
        binding.homeRecyclerOffer.itemAnimator = DefaultItemAnimator()
        offerListAdapter = OfferListAdapter()
        binding.homeRecyclerOffer.adapter = offerListAdapter



        binding.homeRecyclerOffer.post { mLayoutManager.scrollToPosition(2) }


        binding.homeRecycleTourKish.setHasFixedSize(true);
        binding.homeRecycleTourKish.layoutManager = LinearLayoutManager(activity)
        binding.homeRecycleTourKish.itemAnimator = DefaultItemAnimator()
        tourKishListAdapter = TourKishListAdapter()
        binding.homeRecycleTourKish.adapter = tourKishListAdapter

        viewModel.getAllOfferList()!!
            .observe(requireActivity(),
                { t ->
                    offerListAdapter.addData(t!!)
                    offerListAdapter.notifyDataSetChanged()
                })




        viewModel.getAllTourKish()!!
            .observe(requireActivity(),
                { t ->
                    tourKishListAdapter.addData(t!!)
                    tourKishListAdapter.notifyDataSetChanged()
                })




        return viewRoot
    }


    override fun onDestroy() {
        super.onDestroy()
    }
}