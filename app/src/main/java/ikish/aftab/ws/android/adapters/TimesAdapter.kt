package ikish.aftab.ws.android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.databinding.ItemTimeFlightBinding
import ikish.aftab.ws.android.model.FlightTimeModel


class TimesAdapter() : RecyclerView.Adapter<TimesAdapter.ViewHolder>()  {

    interface ClickItem {
        fun onClick(N: String)
    }
    private lateinit var clickItem:ClickItem
    public  fun OnItemClickListener(clickItem: ClickItem)  {
        this.clickItem=clickItem
    }



    var flightTimeList: List<FlightTimeModel>

    lateinit var context:Context

    init {
        flightTimeList = ArrayList()
    }

    fun addData(arrList: List<FlightTimeModel>){
        this.flightTimeList = arrList
    }
    fun addContext(context: Context){
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int):  ViewHolder {
        val binding: ItemTimeFlightBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_time_flight,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemTimeFlightBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any) {

            binding.setVariable(androidx.databinding.library.baseAdapters.BR.flightTime, data)
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return flightTimeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(flightTimeList.get(pos))

        if(pos==0){
            holder.binding.tvNameDayOfMounth.setTextColor(context.resources.getColor(R.color.topaz))
            holder.binding.tvNameDayOfWeek.setTextColor(context.resources.getColor(R.color.topaz))
            holder.binding.tvNumberDayOfMounth.setTextColor(context.resources.getColor(R.color.topaz))
            holder.binding.cardMainTime.setStrokeColor(context.resources.getColor(R.color.topaz))
        }
        holder.itemView.setOnClickListener {
            clickItem.onClick("")
        }


    }//onBind


}