package ikish.aftab.ws.android.adapters



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.databinding.ItemRecycleTypeRentBinding

import ikish.aftab.ws.android.model.TypeRentModel


class TypeRentAdapter() : RecyclerView.Adapter<TypeRentAdapter.ViewHolder>()  , Filterable {


    interface ClickItem {
        fun onClick(title: String,type:Int)
    }

    private lateinit var clickItem: ClickItem

    public fun OnItemClickListener(clickItem: ClickItem) {
        this.clickItem = clickItem
    }

    private val mLock = Any()
    private var mOriginalValues: ArrayList<TypeRentModel> ?= null
    var list: List<TypeRentModel>
    var type:String?=""
    var location:Int?=0


    init {
        list = ArrayList()

    }

    fun addData(arrList: List<TypeRentModel>) {
        this.list = arrList
    }


    fun addType(type:String,name:Int) {
        this.type = type//3 خودرو    2ویلا
        this.location = name//1از انتخاب اول      2 از انتخاب دوم
    }

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): ViewHolder {
        val binding: ItemRecycleTypeRentBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_recycle_type_rent,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemRecycleTypeRentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any) {

            binding.setVariable(androidx.databinding.library.baseAdapters.BR.TypeRent, data)
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(list.get(pos))

        if (type.equals("3") && location==2){
            holder.binding.itemCardMain.visibility=View.GONE
            holder.binding.cardBrand.visibility=View.VISIBLE
            holder.binding.view.visibility=View.GONE
            holder.binding.ivBrand.setImageResource(list.get(pos).image)
        }else{
            holder.binding.itemCardMain.visibility=View.VISIBLE
            holder.binding.cardBrand.visibility=View.GONE
            holder.binding.view.visibility=View.VISIBLE
        }

        holder.binding.itemCardMain.setOnClickListener {

            clickItem.onClick(list.get(pos).title!!,location!!)
        }

        holder.binding.cardBrand.setOnClickListener {
            clickItem.onClick(list.get(pos).title!!,location!!)
        }


    }//onBind

    override fun getFilter(): android.widget.Filter {
        return object : android.widget.Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val filterResults = FilterResults()
                var tempList: java.util.ArrayList<TypeRentModel?> = java.util.ArrayList<TypeRentModel?>()
                if (mOriginalValues == null) {
                    synchronized(mLock) {
                        mOriginalValues = ArrayList(list)



                    }
                }
                if (constraint.toString() != "" && constraint != null && list != null) {
                    val tempSearch = constraint.toString().trim { it <= ' ' }
                        .split(" ".toRegex()).toTypedArray()
                    val length: Int = mOriginalValues!!.size
                    var counter = 0
                    val searchSize = tempSearch.size
                    for (item in mOriginalValues!!) {
                        counter = 0
                        for (searchItem in tempSearch) {
                            if (item.title!!.contains(searchItem)) {
                                counter++
                            }
                        }
                        if (counter == searchSize) tempList.add(item)
                    }
                    filterResults.values = tempList
                    filterResults.count = tempList.size
                } else {
                    synchronized(mLock) { tempList = java.util.ArrayList<TypeRentModel?>(mOriginalValues) }
                    filterResults.values = tempList
                    filterResults.count = tempList.size
                }
                return filterResults
            }

            override fun publishResults(contraint: CharSequence, results: FilterResults) {
                list =results.values as MutableList<TypeRentModel>
                notifyDataSetChanged()


            }
        }
    }


}