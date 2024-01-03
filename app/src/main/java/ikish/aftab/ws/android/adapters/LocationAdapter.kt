package ikish.aftab.ws.android.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.db.Locations
import ikish.aftab.ws.android.databinding.ItemLocationBinding


class LocationAdapter() : RecyclerView.Adapter<LocationAdapter.ViewHolder>() ,Filterable {





    private val mLock = Any()
    private var mOriginalValues: ArrayList<Locations> ?= null
    interface ClickItem {
        fun onClick(N: String)
    }
    private lateinit var clickItem:ClickItem
    public  fun OnItemClickListener(clickItem: ClickItem)  {
        this.clickItem=clickItem
    }



    var locationList: List<Locations>

    init {
        locationList = ArrayList()
    }

    fun addData(arrList: List<Locations>){
        this.locationList = arrList
    }

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int):  ViewHolder {
        val binding: ItemLocationBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_location,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemLocationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any) {

           binding.setVariable(androidx.databinding.library.baseAdapters.BR.location, data)
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(locationList.get(pos))

        holder.itemView.setOnClickListener {
            clickItem.onClick(locationList.get(pos).N.toString())
        }
    }//onBind

    override fun getFilter(): android.widget.Filter {
        return object : android.widget.Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val filterResults = FilterResults()
                var tempList: java.util.ArrayList<Locations?> = java.util.ArrayList<Locations?>()
                if (mOriginalValues == null) {
                    synchronized(mLock) {
                        mOriginalValues = ArrayList(locationList)



                    }
                }
                if (constraint.toString() != "" && constraint != null && locationList != null) {
                    val tempSearch = constraint.toString().trim { it <= ' ' }
                        .split(" ".toRegex()).toTypedArray()
                    val length: Int = mOriginalValues!!.size
                    var counter = 0
                    val searchSize = tempSearch.size
                    for (item in mOriginalValues!!) {
                        counter = 0
                        for (searchItem in tempSearch) {
                            if (item.N!!.contains(searchItem)) {
                                counter++
                            }
                        }
                        if (counter == searchSize) tempList.add(item)
                    }
                    filterResults.values = tempList
                    filterResults.count = tempList.size
                } else {
                    synchronized(mLock) { tempList = java.util.ArrayList<Locations?>(mOriginalValues) }
                    filterResults.values = tempList
                    filterResults.count = tempList.size
                }
                return filterResults
            }

            override fun publishResults(contraint: CharSequence, results: FilterResults) {
               locationList =results.values as MutableList<Locations>
                notifyDataSetChanged()
                if(locationList.size==0){
                  clickItem.onClick("")
                }

            }
        }
    }


}