package ikish.aftab.ws.android.ui.fragments.ticketAirPlaneService

import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ikish.aftab.ws.android.adapters.LocationAdapter
import ikish.aftab.ws.android.adapters.LocationRecentlyAdapter
import ikish.aftab.ws.android.fetchData.MyViewModel
import ikish.aftab.ws.android.databinding.FragmentSearchLocationBinding
import ikish.aftab.ws.android.db.Locations
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.collections4.Predicate

@AndroidEntryPoint
class SearchLocationFragment : Fragment() {

    //region Parameter
    private var binding: FragmentSearchLocationBinding? = null
    private var viewRoot: View? = null
    lateinit var locationAdapter: LocationAdapter
    lateinit var locationRecentlyAdapter: LocationRecentlyAdapter
    lateinit var locationRecentlyList: List<Locations>

    lateinit var locationList: List<Locations>
    private val viewModel: MyViewModel by viewModels()

    //endregion Parameter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchLocationBinding.inflate(layoutInflater)
        viewRoot = binding!!.root





        return viewRoot
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //region Get Argument

        var bundle: Bundle
        bundle = requireArguments()
        var type: String? = bundle.getString("type")
        if (type.equals("1")) {
            binding!!.tvChooseYourLocation.setText("مبدا را انتخاب کنید")
            binding!!.tvPopularLocation.setText("مبدا های محبوب")

        } else {
            binding!!.tvChooseYourLocation.setText("مقصد را انتخاب کنید")
            binding!!.tvPopularLocation.setText("مقصد های محبوب")
        }
        //endregion Get Argument

        //region Cast Location RecyclerView


        binding!!.locationRecyclerView.setHasFixedSize(true)
        binding!!.locationRecyclerView.setLayoutManager(LinearLayoutManager(activity))
        binding!!.locationRecyclerView.setItemAnimator(DefaultItemAnimator())
        locationAdapter = LocationAdapter()
        binding!!.locationRecyclerView.adapter = locationAdapter
        locationAdapter.OnItemClickListener(object : LocationAdapter.ClickItem {
            override fun onClick(NAME: String) {

                if (NAME.equals("")) {
                    binding!!.layoutSearch.visibility = View.VISIBLE
                    binding!!.nestedScroolView.visibility = View.GONE
                    return
                }
                if (type.equals("1")) {
                    if (!NAME!!.equals(AirPlaneFragment.nameDestination)) {
                        AirPlaneFragment.nameOrigin = NAME
                        findNavController().popBackStack()


                    } else {
                        Toast.makeText(
                            activity,
                            "مبدا و مقصد نمیتواند یکی باشد",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    if (!NAME!!.equals(AirPlaneFragment.nameOrigin)) {
                        findNavController()!!.popBackStack()
                        AirPlaneFragment.nameDestination = NAME

                    } else {
                        Toast.makeText(
                            activity,
                            "مبدا و مقصد نمیتواند یکی باشد",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                val listResult: MutableList<Locations> = mutableListOf()
                listResult.addAll(locationList!!)
                CollectionUtils.filter(
                    listResult,
                    Predicate<Locations> { r: Locations -> r.N.equals(NAME) })
                if (listResult.size > 0) {
                    viewModel!!.updateLocationRecently(listResult.get(0).id, "1")

                }
            }
        })








       /* viewModel!!.getAllLocations()!!.observe(requireActivity(), object :
            Observer<List<Locations>> {

            override fun onChanged(t: List<Locations>?) {

                if (p.equals("")) {
                    locationList = t!!
                    locationAdapter.addData(locationList!!)
                    locationAdapter.notifyDataSetChanged()
                }

            }

        })*/


        //endregion Cast Location RecyclerView


        //region Cast Recently Location RecyclerView
        binding!!.recentlyRecyclerView.setHasFixedSize(true)
        binding!!.recentlyRecyclerView.setLayoutManager(LinearLayoutManager(activity))
        binding!!.recentlyRecyclerView.setItemAnimator(DefaultItemAnimator())
        locationRecentlyAdapter = LocationRecentlyAdapter()
        binding!!.recentlyRecyclerView.adapter = locationRecentlyAdapter
        locationRecentlyAdapter.OnItemClickListener(object : LocationRecentlyAdapter.ClickItem {
            override fun onClick(NAME: String) {


                if (type.equals("1")) {
                    if (!NAME!!.equals(AirPlaneFragment.nameDestination)) {
                        findNavController()!!.popBackStack()
                        AirPlaneFragment.nameOrigin = NAME

                    } else {
                        Toast.makeText(
                            activity,
                            "مبدا و مقصد نمیتواند یکی باشد",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    if (!NAME!!.equals(AirPlaneFragment.nameOrigin)) {
                        AirPlaneFragment.nameDestination = NAME
                        findNavController()!!.popBackStack()

                    } else {
                        Toast.makeText(
                            activity,
                            "مبدا و مقصد نمیتواند یکی باشد",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

            }


        })




       /* viewModel!!.getAllRecentlyLocations("1")!!.observe(requireActivity(), object :
            Observer<List<Locations>> {
            override fun onChanged(t: List<Locations>?) {

                if (p.equals("")) {

                }

            }


        })*/


        //endregion Cast Recently Location RecyclerView

        binding!!.btnSearchAgain.setOnClickListener {
            if (locationRecentlyList.size > 0) {
                binding!!.tvRecentlySeen.visibility = View.VISIBLE
                binding!!.recentlyRecyclerView.visibility = View.VISIBLE
                binding!!.tvPopularLocation.visibility = View.VISIBLE

            }
            binding!!.edtSearch.setText("")
            locationAdapter.addData(locationList)
            locationAdapter.notifyDataSetChanged()
        }


        binding!!.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s.toString().isEmpty()) {
                    if (locationRecentlyList.size > 0) {
                        binding!!.tvRecentlySeen.visibility = View.VISIBLE
                        binding!!.recentlyRecyclerView.visibility = View.VISIBLE
                        binding!!.tvPopularLocation.visibility = View.VISIBLE
                        binding!!.nestedScroolView.visibility = View.VISIBLE
                        binding!!.layoutSearch.visibility = View.GONE
                    }
                    binding!!.layoutSearch.visibility = View.GONE
                    binding!!.nestedScroolView.visibility = View.VISIBLE
                    binding!!.tvChooseYourLocation.setText("مبدا خود را انتخاب کنید.")

                } else {
                    binding!!.tvRecentlySeen.visibility = View.GONE
                    binding!!.recentlyRecyclerView.visibility = View.GONE
                    binding!!.tvPopularLocation.visibility = View.GONE
                    binding!!.tvChooseYourLocation.setText("نتایج نزدیک به جستجوی شما")

                }
                search(s.toString())

            }


        })

        binding!!.ivBackFrament.setOnClickListener {

            findNavController().popBackStack()
        }

        object : AsyncTask<Any?, Any?, Any?>() {
            override fun onPreExecute() {
                super.onPreExecute()
            }

            override fun onPostExecute(o: Any?) {
                locationAdapter.addData(locationList!!)
                locationAdapter.notifyDataSetChanged()


                if (locationRecentlyList!!.size > 0) {
                    binding!!.tvRecentlySeen.visibility = View.VISIBLE
                    binding!!.recentlyRecyclerView.visibility = View.VISIBLE
                    binding!!.tvPopularLocation.visibility = View.VISIBLE
                }
                locationRecentlyAdapter.addData(locationRecentlyList)
                locationRecentlyAdapter.notifyDataSetChanged()
                super.onPostExecute(o)
            }

            override fun doInBackground(params: Array<Any?>): Any? {
                try {
                    locationList = viewModel!!.getAllLocations()!!
                    locationRecentlyList  = viewModel!!.getAllRecentlyLocations("1")!!
                } catch (e: Exception) {
                    val y = 0
                }
                return null
            }
        }.execute(0)



    }

    private fun search(search: String) {
        if (binding!!.locationRecyclerView.getAdapter() != null) {
            (binding!!.locationRecyclerView.getAdapter() as Filterable).filter.filter(
                search
            )
        }
    }


}