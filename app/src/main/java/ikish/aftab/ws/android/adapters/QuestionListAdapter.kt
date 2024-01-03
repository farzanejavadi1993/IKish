package ikish.aftab.ws.android.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import ikish.aftab.ws.android.model.QuestionModel
import ikish.aftab.ws.android.databinding.ItemQuestionRecycleBinding


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil


import androidx.recyclerview.widget.RecyclerView

import ikish.aftab.ws.android.R


class QuestionListAdapter() : RecyclerView.Adapter<QuestionListAdapter.ViewHolder>() {

    var questionList: List<QuestionModel>

    init {
        questionList = ArrayList()
    }

    fun addData(arrList: List<QuestionModel>){
        this.questionList = arrList
    }

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int):  ViewHolder {
        val binding: ItemQuestionRecycleBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_question_recycle, parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemQuestionRecycleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any) {

            binding.setVariable(androidx.databinding.library.baseAdapters.BR.question,data)
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return questionList.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(questionList.get(pos))
        holder.itemView.setOnClickListener {


            if (holder.binding.questionTvDescription.visibility == View.VISIBLE) {
                holder.binding.questionTvDescription.visibility = View.GONE
                holder.binding.questionItemBtn.rotation=0f
                holder.binding.questionItemTvTitle.setTextColor(Color.parseColor("#474747"))
                holder.binding.questionItemBtn.setImageResource(R.drawable.ic_fi_rr_caret_left)


            } else {
                holder.binding.questionTvDescription.visibility = View.VISIBLE
                holder.binding.questionItemBtn.rotation=270f
                holder.binding.questionItemTvTitle.setTextColor(Color.parseColor("#13b6ad"))
                holder.binding.questionItemBtn.setImageResource(R.drawable.ic_fi_bottom)
            }
        }

    }//onBind


}