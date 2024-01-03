package ikish.aftab.ws.android.adapters

import android.annotation.SuppressLint
import android.graphics.BitmapFactory


import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil


import androidx.recyclerview.widget.RecyclerView

import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.db.ReserveResidence
import ikish.aftab.ws.android.databinding.ItemReserveResidentBinding


class ReserveResidenceAdapter() : RecyclerView.Adapter<ReserveResidenceAdapter.ViewHolder>() {
    interface ClickItem {
        fun onClick(N: String,img :Int,type: String,day: String)
    }
    private lateinit var clickItem:ClickItem

    public  fun OnItemClickListener(clickItem: ClickItem)  {
        this.clickItem=clickItem
    }
    var list: List<ReserveResidence>

    init {
        list = ArrayList()

    }

    fun addData(arrList: List<ReserveResidence>){
        this.list = arrList
    }

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int):  ViewHolder {
        val binding: ItemReserveResidentBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_reserve_resident, parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemReserveResidentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any) {

            binding.setVariable(androidx.databinding.library.baseAdapters.BR.ReserveResidence,data)
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(list.get(pos))

        if(!list.get(pos).POS.equals("")){
            holder.binding.ivReserve.setImageBitmap(BitmapFactory.decodeFile(list.get(pos).POS.split(":::").get(0)))
        }
       /* if(list.get(pos).T.equals("2")){
            holder.binding.ivReserve.setImageResource(R.drawable.saheli)
        }else{
            holder.binding.ivReserve.setImageResource(R.drawable.benz)
        }
*/

        holder.itemView.setOnClickListener {
            if (list.get(pos).T.equals("2")){
                clickItem.onClick(list.get(pos).Name!!,pos,list.get(pos).T!!,list.get(pos).DATE!!)
            }else{
                clickItem.onClick(list.get(pos).Name!!,pos+3,list.get(pos).T!!,list.get(pos).DATE!!)
            }

        }


    }//onBind


}