package ikish.aftab.ws.android.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ikish.aftab.ws.android.ui.fragments.passenger.PassengerListFragment
import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.databinding.ItemPassengerListBinding


class PassengerListAdapter() : RecyclerView.Adapter<PassengerListAdapter.ViewHolder>() {
    interface ClickMenu {
        fun onClick(item: String , view :View ,namePassenger :String)
    }
    private lateinit var clickMenu:ClickMenu

    public  fun OnClickItemListener(clickMenu: ClickMenu)  {
        this.clickMenu=clickMenu
    }



    interface CheckItem {
        fun isCheck(Id:String ,check :Boolean)
    }
    private lateinit var checkItem:CheckItem

    public  fun OnCheckListener(checkItem: CheckItem)  {
        this.checkItem=checkItem
    }








    var passengerList: List<ikish.aftab.ws.android.db.Passenger>

    init {
        passengerList = ArrayList()
    }

    fun addData(arrList: List<ikish.aftab.ws.android.db.Passenger>){
        this.passengerList = arrList
    }

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int):  ViewHolder {
        val binding: ItemPassengerListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_passenger_list,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemPassengerListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any) {

           binding.setVariable(androidx.databinding.library.baseAdapters.BR.passenger, data)

            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return passengerList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(passengerList.get(pos))
        if (PassengerListFragment.TYPE.equals("2")){
            holder.binding.checkbox.visibility=View.VISIBLE
            holder.binding!!.tvAge.visibility=View.VISIBLE
        }else if(PassengerListFragment.TYPE.equals("3")){
            holder.binding.radio.visibility=View.VISIBLE

        }

        holder.binding.ivMenu.setOnClickListener {
            clickMenu.onClick(passengerList.get(pos).IDC!!,holder.binding.ivMenu,passengerList.get(pos).FN+" "+passengerList.get(pos).LN)
        }

        holder.binding.checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
           checkItem.isCheck(passengerList.get(pos).IDC!!,isChecked)
        }

        holder.binding.radio.setOnCheckedChangeListener { buttonView, isChecked ->
            checkItem.isCheck(passengerList.get(pos).CN!!,isChecked)
        }


    }//onBind


}