package ikish.aftab.ws.android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import androidx.recyclerview.widget.RecyclerView

import ikish.aftab.ws.android.model.OfferModel
import ikish.aftab.ws.android.R





import ikish.aftab.ws.android.databinding.ItemOfferRecycleHomeBinding



class OfferListAdapter() : RecyclerView.Adapter<OfferListAdapter.ViewHolder>() {

    var offerList: List<OfferModel>

    init {
        offerList = ArrayList()
    }

    fun addData(arrList: List<OfferModel>){
        this.offerList = arrList
    }

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int):  ViewHolder {
        val binding: ItemOfferRecycleHomeBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_offer_recycle_home, parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemOfferRecycleHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any) {

            binding.setVariable(androidx.databinding.library.baseAdapters.BR.offer,data)
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return offerList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(offerList.get(pos))
    }//onBind


}