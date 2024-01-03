package ikish.aftab.ws.android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.db.Locations

import ikish.aftab.ws.android.databinding.ItemRecentlyLocationBinding

class LocationRecentlyAdapter() : RecyclerView.Adapter<LocationRecentlyAdapter.ViewHolder>() {
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
        val binding: ItemRecentlyLocationBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_recently_location, parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemRecentlyLocationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any) {

            binding.setVariable(androidx.databinding.library.baseAdapters.BR.location,data)
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        if (locationList.size>3)
            return 3
      else
        return locationList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(locationList.get(pos))
        holder.itemView.setOnClickListener {
            clickItem.onClick(locationList.get(pos).N.toString())
        }
    }//onBind


}