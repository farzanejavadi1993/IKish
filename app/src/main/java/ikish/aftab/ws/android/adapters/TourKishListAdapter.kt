package ikish.aftab.ws.android.adapters

import ikish.aftab.ws.android.databinding.ItemRecycleKishTourBinding



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil


import androidx.recyclerview.widget.RecyclerView

import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.model.TourKishModel


class TourKishListAdapter() : RecyclerView.Adapter<TourKishListAdapter.ViewHolder>() {

    var tourKishList: List<TourKishModel>

    init {
        tourKishList = ArrayList()
    }

    fun addData(arrList: List<TourKishModel>){
        this.tourKishList = arrList
    }

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int):  ViewHolder {
        val binding: ItemRecycleKishTourBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_recycle_kish_tour, parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemRecycleKishTourBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any) {

          binding.setVariable(androidx.databinding.library.baseAdapters.BR.tourKish,data)

            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return tourKishList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(tourKishList.get(pos))

        holder.itemView.setOnClickListener {

        }
    }





}