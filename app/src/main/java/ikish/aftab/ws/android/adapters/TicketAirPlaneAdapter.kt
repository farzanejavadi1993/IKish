package ikish.aftab.ws.android.adapters






import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.db.DetailFlight

import ikish.aftab.ws.android.databinding.ItemTicketAirPlaneBinding


class TicketAirPlaneAdapter() : RecyclerView.Adapter<TicketAirPlaneAdapter.ViewHolder>()  {

    interface ClickItem {
        fun onClick(N: String)
    }
    private lateinit var clickItem:ClickItem
    public  fun OnItemClickListener(clickItem: ClickItem)  {
        this.clickItem=clickItem
    }
    var ticketList: List<DetailFlight>




    init {
        ticketList = ArrayList()
    }

    fun addData(arrList: List<DetailFlight>){
        this.ticketList = arrList
    }

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int):  ViewHolder {
        val binding: ItemTicketAirPlaneBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_ticket_air_plane,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemTicketAirPlaneBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any) {

           binding.setVariable(androidx.databinding.library.baseAdapters.BR.detailFlight, data)
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return ticketList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(ticketList.get(pos))

        holder.itemView.setOnClickListener {
           clickItem.onClick(ticketList.get(pos).ONN!!)
        }


    }//onBind


}