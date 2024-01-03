package ikish.aftab.ws.android.adapters


import android.view.LayoutInflater

import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView


import ikish.aftab.ws.android.R

import ikish.aftab.ws.android.db.Transactions

import ikish.aftab.ws.android.databinding.ItemTransactionBinding


class TransactionAdapter() : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {


    var list: List<Transactions>

    init {
        list = ArrayList()
    }

    fun addData(arrList: List<Transactions>) {
        this.list = arrList
    }

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): ViewHolder {
        val binding: ItemTransactionBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_transaction,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any) {

            binding.setVariable(androidx.databinding.library.baseAdapters.BR.Transaction, data)

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