package ikish.aftab.ws.android.adapters
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import ikish.aftab.ws.android.R

import ikish.aftab.ws.android.databinding.PhotoResidenceItemBinding
import ikish.aftab.ws.android.db.Residence


class PhotoResidenceAdapter() : RecyclerView.Adapter<PhotoResidenceAdapter.ViewHolder>()  {

    interface DeleteItem {
        fun onClick(imagePath: String)
    }
    private lateinit var deleteItem:DeleteItem
    public  fun OnItemDeleteListener(deleteItem: DeleteItem)  {
        this.deleteItem=deleteItem
    }


    interface ItemClick {
        fun onClick(I : String)
    }
    private lateinit var itemClick:ItemClick
    public  fun OnItemClickListener(itemClick: ItemClick)  {
        this.itemClick=itemClick
    }



    var list: List<Residence>

    init {
        list = ArrayList()
    }

    fun addData(arrList: List<Residence>){
        this.list = arrList
    }

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int):  ViewHolder {
        val binding: PhotoResidenceItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.photo_residence_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: PhotoResidenceItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any) {

            binding.setVariable(androidx.databinding.library.baseAdapters.BR.PhotoResidence, data)
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(list.get(pos))
        if (list.get(pos).IMG.equals("") && list.get(pos).id==1){
            holder.binding.btnDeletePhoto.visibility=View.GONE
            holder.binding.photoResidence.setImageBitmap(null)
            holder.binding.btnAddPhoto.setBackgroundResource(R.drawable.dotted_line_shape)
            holder.binding.tvAddPhoto.visibility=View.VISIBLE
            holder.binding.ivFillPhoto.visibility=View.VISIBLE
        }else{
            holder.binding.photoResidence.setImageBitmap(BitmapFactory.decodeFile(list.get(pos).IMG))
            holder.binding.tvAddPhoto.visibility=View.GONE
            holder.binding.ivFillPhoto.visibility=View.GONE
        }


        holder.binding.btnAddPhoto.setOnClickListener {
           if (list.get(pos).IMG.equals("") && list.get(pos).id==1){
               itemClick.onClick("1")
           }
        }


        holder.binding.btnDeletePhoto.setOnClickListener {
            holder.binding.btnDeletePhoto.isClickable=false
            deleteItem.onClick(list.get(pos).IMG!!)
            holder.binding.btnDeletePhoto.isClickable=true
        }






    }//onBind


}