package ikish.aftab.ws.android.adapters








import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import ikish.aftab.ws.android.R


import ikish.aftab.ws.android.databinding.ItemSuperviserBinding
import ikish.aftab.ws.android.db.Supervisers


class SuperViserAdapter() : RecyclerView.Adapter<SuperViserAdapter.ViewHolder>()  {

    interface DeleteItem {
        fun onClick(N :String)
    }
    private lateinit var deleteItem:DeleteItem
    public  fun OnItemDeleteListener(deleteItem: DeleteItem)  {
        this.deleteItem=deleteItem
    }



    var list: List<Supervisers>
    var readOnly: String="0"

    init {
        list = ArrayList()
    }

    fun addData(arrList: List<Supervisers>){
        this.list = arrList
    }
    fun addFlag(readOnly:String){
        this.readOnly = readOnly
    }

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int):  ViewHolder {
        val binding: ItemSuperviserBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_superviser,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemSuperviserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any) {

           binding.setVariable(androidx.databinding.library.baseAdapters.BR.SuperViser, data)
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(list.get(pos))

      holder.binding.ivClose.setOnClickListener {
          if (readOnly.equals("0")){
              deleteItem.onClick(list.get(pos).NM!!)
          }

      }






    }//onBind


}