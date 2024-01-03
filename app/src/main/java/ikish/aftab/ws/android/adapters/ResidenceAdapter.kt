package ikish.aftab.ws.android.adapters

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import ikish.aftab.ws.android.R

import ikish.aftab.ws.android.db.Residence
import ikish.aftab.ws.android.databinding.ItemResidenceBinding


class ResidenceAdapter() : RecyclerView.Adapter<ResidenceAdapter.ViewHolder>() {

    interface ClickItem {
        fun onClick(N: String, img: Int)
    }

    private lateinit var clickItem: ClickItem

    public fun OnItemClickListener(clickItem: ClickItem) {
        this.clickItem = clickItem
    }

    var residenceList: List<Residence>


    init {
        residenceList = ArrayList()

    }

    fun addData(arrList: List<Residence>) {
        this.residenceList = arrList
    }

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): ViewHolder {
        val binding: ItemResidenceBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_residence,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemResidenceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any) {

            binding.setVariable(androidx.databinding.library.baseAdapters.BR.Residence, data)
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return residenceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(residenceList.get(pos))
        holder.binding.tvPriceResidenceItem.setText(
            residenceList.get(pos).PR.split("/").get(0).split(":").get(1)
        )

        if (!residenceList.get(pos).IMG.equals(""))
            holder.binding.ivItem.setImageBitmap(
                BitmapFactory.decodeFile(
                    residenceList.get(pos).IMG!!.split(
                        ":::"
                    ).get(0)
                )
            )
       /* else
            if (residenceList.get(pos).T.equals("2")) {
                holder.binding.ivItem.setImageResource(R.drawable.saheli)
            } else {
                holder.binding.ivItem.setImageResource(R.drawable.benz)
            }*/



        holder.itemView.setOnClickListener {
            if (residenceList.get(pos).T.equals("2")) {
                clickItem.onClick(residenceList.get(pos).NM!!, pos)
            } else {
                clickItem.onClick(residenceList.get(pos).NM!!, pos + 3)
            }

        }


    }//onBind


}