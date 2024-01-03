package ikish.aftab.ws.android.adapters






import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.db.PassengerFlight

import ikish.aftab.ws.android.databinding.ItemPassengerTicketBinding



class FlightPassengerAdapter() : RecyclerView.Adapter<FlightPassengerAdapter.ViewHolder>()  {

    interface DeleteItem {
        fun onClick(F: String,N :String)
    }
    private lateinit var deleteItem:DeleteItem
    public  fun OnItemDeleteListener(deleteItem: DeleteItem)  {
        this.deleteItem=deleteItem
    }



    var passengerFlightList: List<PassengerFlight>

    init {
        passengerFlightList = ArrayList()
    }

    fun addData(arrList: List<PassengerFlight>){
        this.passengerFlightList = arrList
    }

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int):  ViewHolder {
        val binding: ItemPassengerTicketBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_passenger_ticket,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemPassengerTicketBinding) : RecyclerView.ViewHolder(binding.root) {
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
        holder.binding.ivCancel.setOnClickListener {
            deleteItem.onClick(passengerFlightList.get(pos).F!!,passengerFlightList.get(pos).N!!)
        }






    }//onBind


}