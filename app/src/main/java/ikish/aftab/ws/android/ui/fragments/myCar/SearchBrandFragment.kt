package ikish.aftab.ws.android.ui.fragments.myCar


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import ikish.aftab.ws.android.adapters.TypeRentAdapter
import ikish.aftab.ws.android.ui.fragments.myResidence.TypePlaceResidenceFragment
import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.databinding.FragmentSearchBrandBinding
import ikish.aftab.ws.android.model.TypeRentModel


class SearchBrandFragment : Fragment() {

    //region Parameter
    private var binding: FragmentSearchBrandBinding? = null
    private var viewRoot: View? = null
    lateinit var adapter: TypeRentAdapter
    private var listCar2: MutableList<TypeRentModel>? = ArrayList()
    private var type:String?="3"
    private var location:Int?=2
    //endregion Parameter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBrandBinding.inflate(layoutInflater)
        viewRoot = binding!!.root

        fullArray()

        //region Cast Location RecyclerView
        binding!!.recyclerBrand.setHasFixedSize(true)
        binding!!.recyclerBrand.setLayoutManager(LinearLayoutManager(activity))
        binding!!.recyclerBrand.setItemAnimator(DefaultItemAnimator())
        adapter = TypeRentAdapter()
        binding!!.recyclerBrand.adapter = adapter
        adapter.addData(listCar2!!)
        adapter.addType(type!!,location!!)
        adapter.notifyDataSetChanged()
        adapter.OnItemClickListener(object : TypeRentAdapter.ClickItem {
            override fun onClick(title: String, type: Int) {
               TypePlaceResidenceFragment.edtBox2=title
                findNavController().popBackStack()
            }

        })

        //endregion Cast Location RecyclerView

        binding!!.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                var s=toEnglishNumber(s.toString())
                search(s!!)
            }


        })

        binding!!.ivBackFramentBrand.setOnClickListener {

            findNavController().popBackStack()
        }



        return viewRoot
    }





    private fun search(search: String) {
        if (binding!!.recyclerBrand.getAdapter() != null) {
            (binding!!.recyclerBrand.getAdapter() as Filterable).filter.filter(
                search
            )
        }
    }

    private fun fullArray(){
        listCar2!!.add(TypeRentModel("Ford ","", R.drawable.bmw))
        listCar2!!.add(TypeRentModel("Mercedes-Benz ","", R.drawable.ferari_icon))
        listCar2!!.add(TypeRentModel("Chevrolet ","", R.drawable.benz_icon))
        listCar2!!.add(TypeRentModel("Bmw ","", R.drawable.bmw))
        listCar2!!.add(TypeRentModel("Ford ","", R.drawable.ferari_icon))
        listCar2!!.add(TypeRentModel("Chevrolet ","", R.drawable.benz_icon))
    }

    fun toEnglishNumber(input: String): String? {
        var input = input
        val persian = arrayOf("۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹")
        val arabic = arrayOf("٠", "١", "٢", "٣", "٤", "٥", "٦", "٧", "٨", "٩")
        for (j in persian.indices) {
            if (input.contains(persian[j])) input = input.replace(persian[j], j.toString())
        }
        for (j in arabic.indices) {
            if (input.contains(arabic[j])) input = input.replace(arabic[j], j.toString())
        }
        return input
    }
}