package com.abdulmanov.laovay.ui.adapters

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abdulmanov.laovay.R
import com.abdulmanov.laovay.common.inflate
import com.abdulmanov.laovay.mvp.model.domain.model.Example
import kotlinx.android.synthetic.main.item_list_example.view.*

class ExamplesAdapter:RecyclerView.Adapter<ExamplesAdapter.ViewHolder>() {

    private val examples = mutableListOf<Example>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return examples.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(examples[position])
    }

    fun swapData(data:List<Example>){
        examples.clear()
        examples.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(parent:ViewGroup):RecyclerView.ViewHolder(parent.inflate(R.layout.item_list_example)){

        private val redSpan = ForegroundColorSpan(Color.RED)

        fun bind(example: Example){
            with(itemView){
                item_list_example_word.setText(example.word,example.substring)
                item_list_example_translate.setText(example.translate,example.substring)
            }
        }

        private fun TextView.setText(str:String,subStr:String){
            val index = str.indexOf(subStr)
            text = if(index!=-1){
                SpannableStringBuilder(str).apply {
                    setSpan(redSpan, index, index + subStr.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
                }
            }else{
                str
            }
        }
    }
}