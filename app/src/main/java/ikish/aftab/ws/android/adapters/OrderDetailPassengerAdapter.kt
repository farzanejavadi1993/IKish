package ikish.aftab.ws.android.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.db.PassengerFlight
import ikish.aftab.ws.android.databinding.ItemPassengerOrderDetailBinding





class OrderDetailPassengerAdapter() : RecyclerView.Adapter<OrderDetailPassengerAdapter.ViewHolder>()  {


    interface CheckItem {
        fun onCheck(N: String,isCheck :Boolean)
    }
    private lateinit var checkItem:CheckItem
    public  fun OnItemDeleteListener(checkItem: CheckItem)  {
        this.checkItem=checkItem
    }



    var passengerFlightList: List<PassengerFlight>
    var type:String?="1"
    var res:Drawable?=null

    init {
        passengerFlightList = ArrayList()
    }

    fun addData(arrList: List<PassengerFlight>){
        this.passengerFlightList = arrList
    }

    fun addType(type: String){
        this.type = type
    }

    fun addRes(res: Drawable){
        this.res= res
    }


    override fun onCreateViewHolder(parent: ViewGroup, pos: Int):  ViewHolder {
        val binding: ItemPassengerOrderDetailBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_passenger_order_detail,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemPassengerOrderDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any) {

           binding.setVariable(androidx.databinding.library.baseAdapters.BR.passengerFlight, data)
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return passengerFlightList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {

        holder.bind(passengerFlightList.get(pos))
        if (type.equals("1")){

            holder.binding.checkbox.visibility=View.GONE
            holder.binding.viewItem.visibility=View.VISIBLE
        }else{
            holder.binding.checkbox.visibility=View.VISIBLE
            holder.binding.viewItem.visibility=View.GONE
            holder.binding.cardItem.setBackgroundDrawable(res)
        }


        holder.binding.checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            checkItem.onCheck(passengerFlightList.get(pos).N!!,isChecked)
        }

    }//onBind


}