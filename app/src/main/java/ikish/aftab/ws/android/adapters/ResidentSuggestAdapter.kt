package ikish.aftab.ws.android.adapters

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import ikish.aftab.ws.android.R


import ikish.aftab.ws.android.db.Residence

import ikish.aftab.ws.android.databinding.ItemResidenceSuggestBinding



class ResidentSuggestAdapter() : RecyclerView.Adapter<ResidentSuggestAdapter.ViewHolder>()  {

    interface ClickItem {
        fun onClick(N: String)
    }
    private lateinit var clickItem:ClickItem

    public  fun OnItemClickListener(clickItem: ClickItem)  {
        this.clickItem=clickItem
    }

    var residenceList: List<Residence>





    init {
        residenceList = ArrayList()
    }

    fun addData(arrList: List<Residence>){
        this.residenceList = arrList
    }

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int):  ViewHolder {
        val binding: ItemResidenceSuggestBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_residence_suggest,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemResidenceSuggestBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any) {

            binding.setVariable(androidx.databinding.library.baseAdapters.BR.ResidenceSuggest, data)
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return residenceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(residenceList.get(pos))

        holder.binding.tvPriceResidenceItem.setText(residenceList.get(pos).PR.split("/").get(0).split(":").get(1))

        if (!residenceList.get(pos).IMG.equals(""))
            holder.binding.ivItem.setImageBitmap(BitmapFactory.decodeFile(residenceList.get(pos).IMG!!.split(":::").get(0)))


        holder.itemView.setOnClickListener {

                clickItem.onClick(residenceList.get(pos).NM!!)


        }


    }//onBind


}