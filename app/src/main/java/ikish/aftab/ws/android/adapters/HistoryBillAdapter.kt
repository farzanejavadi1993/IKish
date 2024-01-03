package ikish.aftab.ws.android.adapters






import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.databinding.ItemBillsListBinding
import ikish.aftab.ws.android.db.Bill


class HistoryBillAdapter() : RecyclerView.Adapter<HistoryBillAdapter.ViewHolder>()  {

    interface ClickItem {
        fun onClick()
    }
    private lateinit var clickItem :ClickItem
    public  fun OnClickListener(clickItem: ClickItem)  {
        this.clickItem=clickItem
    }

    var list: List<Bill>

    init {
        list = ArrayList()
    }

    fun addData(arrList: List<Bill>){
        this.list = arrList
    }

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int):  ViewHolder {
        val binding: ItemBillsListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_bills_list,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemBillsListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any) {

            binding.setVariable(androidx.databinding.library.baseAdapters.BR.Bill, data)
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(list.get(pos))

        holder.itemView.setOnClickListener {
            clickItem.onClick()
        }



    }//onBind


}