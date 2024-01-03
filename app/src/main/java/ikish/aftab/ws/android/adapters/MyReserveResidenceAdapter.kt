package ikish.aftab.ws.android.adapters

import ikish.aftab.ws.android.databinding.ItemMyReserveResidenceBinding



import android.annotation.SuppressLint


import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil


import androidx.recyclerview.widget.RecyclerView

import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.db.ReserveResidence


class MyReserveResidenceAdapter() : RecyclerView.Adapter<MyReserveResidenceAdapter.ViewHolder>() {
    interface ClickItem {
        fun onClick(N: String,type:String,date :String)
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
        val binding: ItemMyReserveResidenceBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_my_reserve_residence, parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemMyReserveResidenceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any) {

          binding.setVariable(androidx.databinding.library.baseAdapters.BR.MyReserveResidence,data)
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(list.get(pos))
       /* if(!list.get(pos).POS.equals("")){
            holder.binding.profileIvUser.setImageBitmap(BitmapFactory.decodeFile(list.get(pos).POS.split(":::").get(0)))
        }*/
       /* if(list.get(pos).T.equals("2")){
            holder.binding.profileIvUser.setImageResource(R.drawable.saheli)
        }else{
            holder.binding.profileIvUser.setImageResource(R.drawable.benz)
        }*/


        holder.itemView.setOnClickListener {

                clickItem.onClick(list.get(pos).Name!!,list.get(pos).T!!,list.get(pos).DATE!!)



        }


    }//onBind


}