package ikish.aftab.ws.android.adapters

import android.content.Context
import ikish.aftab.ws.android.databinding.ItemMyResidenceListBinding


import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.switchmaterial.SwitchMaterial

import ikish.aftab.ws.android.R

import ikish.aftab.ws.android.db.Residence


class MyResidenceAdapter() : RecyclerView.Adapter<MyResidenceAdapter.ViewHolder>() {
    private lateinit var bsInternal: View
    private lateinit var bsDialog: BottomSheetDialog
    private lateinit var btnEdit: RelativeLayout
    private lateinit var btnComment: RelativeLayout
    private lateinit var btnCalendar: RelativeLayout
    private lateinit var switchButton: SwitchMaterial
    private lateinit var tvEdit: TextView
    private lateinit var tvComment: TextView
    private lateinit var tvSwitch: TextView

    private lateinit var btnClose: ImageView

    private lateinit var context: Context

    interface ClickItem {
        fun onClick(id: Int, t: String)
    }

    private lateinit var clickItem: ClickItem

    public fun OnItemClickListener(clickItem: ClickItem) {
        this.clickItem = clickItem
    }

    var residenceList: List<Residence>


    init {
        residenceList = ArrayList()

    }

    fun addData(arrList: List<Residence>, context: Context) {
        this.residenceList = arrList
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): ViewHolder {
        val binding: ItemMyResidenceListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_my_residence_list,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemMyResidenceListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any) {
            binding.setVariable(androidx.databinding.library.baseAdapters.BR.MyResidence, data)
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return residenceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(residenceList.get(pos))

        if (!residenceList.get(pos).IMG.equals(""))
            holder.binding.ivMyResidence.setImageBitmap(
                BitmapFactory.decodeFile(
                    residenceList.get(pos).IMG!!.split(
                        ":::"
                    ).get(0)
                )
            )

        if (residenceList.get(pos).T.equals("3")) {
            holder.binding.imageView2.visibility = View.GONE
            holder.binding.tvAddressMyResidene.visibility = View.GONE
        }


        holder.binding.btnMenu.setOnClickListener {
            holder.binding.btnMenu.setStrokeColor(context.resources.getColor(R.color.topaz))
            holder.binding.ivMenu.setImageResource(R.drawable.ic_menu_topaz)
            showBottomSheet(holder, pos)
        }


    }//onBind

    private fun showBottomSheet(holder: ViewHolder, pos: Int) {

        bsDialog = BottomSheetDialog(context, R.style.DialogStyle)
        bsDialog.setContentView(R.layout.bottom_sheet_my_residence)
        bsInternal = bsDialog.findViewById(R.id.design_bottom_sheet)!!
        btnComment = bsDialog.findViewById(R.id.btn_comment)!!
        btnCalendar = bsDialog.findViewById(R.id.btn_Calendar)!!
        btnEdit = bsDialog.findViewById(R.id.btn_edit)!!
        btnClose = bsDialog.findViewById(R.id.btn_close)!!
        switchButton = bsDialog.findViewById(R.id.switchButton)!!
        tvEdit = bsDialog.findViewById(R.id.tv_edit)!!
        tvComment = bsDialog.findViewById(R.id.tv_comment)!!
        tvSwitch = bsDialog.findViewById(R.id.tv_activity)!!
        bsDialog.setCancelable(false)
        BottomSheetBehavior.from(bsInternal).state = BottomSheetBehavior.STATE_EXPANDED


        if (residenceList.get(pos).T.equals("3")){
            tvSwitch.setText(context.resources.getString(R.string.activity_car))
            tvEdit.setText(context.resources.getString(R.string.edit_car))
            tvComment.setText(context.resources.getString(R.string.comment_user_car))
        }
        bsDialog.show()


        btnComment.setOnClickListener {
            holder.binding.btnMenu.setStrokeColor(context.resources.getColor(R.color.medium_color))
            holder.binding.ivMenu.setImageResource(R.drawable.ic_menu)
            bsDialog.dismiss()
            clickItem.onClick(residenceList.get(pos).id, "comment")

        }

        btnEdit.setOnClickListener {
            holder.binding.btnMenu.setStrokeColor(context.resources.getColor(R.color.medium_color))
            holder.binding.ivMenu.setImageResource(R.drawable.ic_menu)
            bsDialog.dismiss()
            clickItem.onClick(residenceList.get(pos).id, "edit")

        }

        btnCalendar.setOnClickListener {
            holder.binding.btnMenu.setStrokeColor(context.resources.getColor(R.color.medium_color))
            holder.binding.ivMenu.setImageResource(R.drawable.ic_menu)
            bsDialog.dismiss()
            clickItem.onClick(residenceList.get(pos).id, "calendar")

        }

        btnClose.setOnClickListener {
            holder.binding.btnMenu.setStrokeColor(context.resources.getColor(R.color.medium_color))
            holder.binding.ivMenu.setImageResource(R.drawable.ic_menu)
            bsDialog.dismiss()
        }

//        btnCloseDialog!!.setOnClickListener {
//            bsDialog.dismiss()
//        }


    }
}