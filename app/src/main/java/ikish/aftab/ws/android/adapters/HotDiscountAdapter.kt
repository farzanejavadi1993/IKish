package ikish.aftab.ws.android.adapters



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import androidx.recyclerview.widget.RecyclerView

import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.databinding.ItemBestDiscountBinding


import ikish.aftab.ws.android.model.HotDiscountModel


class HotDiscountAdapter() : RecyclerView.Adapter<HotDiscountAdapter.ViewHolder>() {


    interface ClickItem {
        fun onClick()
    }
    private lateinit var clickItem:ClickItem
    public  fun OnItemClickListener(clickItem: ClickItem)  {
        this.clickItem=clickItem
    }
    var list: List<HotDiscountModel>

    init {
        list = ArrayList()
    }

    fun addData(arrList: List<HotDiscountModel>){
        this.list = arrList
    }

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int):  ViewHolder {
        val binding: ItemBestDiscountBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_best_discount, parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemBestDiscountBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any) {

           binding.setVariable(androidx.databinding.library.baseAdapters.BR.bestDiscount,data)
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(list.get(pos))
        holder.binding.ivBestDiscount.setImageResource(list.get(pos).img)


        holder.itemView.setOnClickListener {
            clickItem.onClick()
        }
    }//onBind


}