package ikish.aftab.ws.android.adapters




import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.databinding.ItemFlightBinding
import ikish.aftab.ws.android.model.FlightModel


class FlightsAdapter() : RecyclerView.Adapter<FlightsAdapter.ViewHolder>()  {

    interface ClickItem {
        fun onClick(N: String)
    }
    private lateinit var clickItem:ClickItem
    public  fun OnItemClickListener(clickItem: ClickItem)  {
        this.clickItem=clickItem
    }



    var flightList: List<FlightModel>

    init {
        flightList = ArrayList()
    }

    fun addData(arrList: List<FlightModel>){
        this.flightList = arrList
    }

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int):  ViewHolder {
        val binding: ItemFlightBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_flight,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemFlightBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any) {

            binding.setVariable(androidx.databinding.library.baseAdapters.BR.flight, data)
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return flightList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(flightList.get(pos))

        holder.itemView.setOnClickListener {
            clickItem.onClick(flightList.get(pos).NumberFlight)
        }


    }//onBind


}