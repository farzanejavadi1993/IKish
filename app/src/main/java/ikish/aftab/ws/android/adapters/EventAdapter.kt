package ikish.aftab.ws.android.adapters






import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.databinding.ItemEventListBinding
import ikish.aftab.ws.android.model.EventModel


class EventAdapter() : RecyclerView.Adapter<EventAdapter.ViewHolder>()  {




    var list: List<EventModel>

    init {
        list = ArrayList()
    }

    fun addData(arrList: List<EventModel>){
        this.list = arrList
    }

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int):  ViewHolder {
        val binding: ItemEventListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_event_list,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemEventListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any) {

            binding.setVariable(androidx.databinding.library.baseAdapters.BR.event, data)
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(list.get(pos))




    }//onBind


}