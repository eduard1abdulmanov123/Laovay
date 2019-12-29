package com.abdulmanov.laovay.ui.adapters

import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.abdulmanov.laovay.R
import com.abdulmanov.laovay.common.inflate
import com.abdulmanov.laovay.mvp.model.domain.model.Word
import kotlinx.android.synthetic.main.item_list_library_word.view.*

class LibraryAdapter(
    private val clickListener:(word: Word)->Unit,
    private val removeCallback:((word: Word)->Unit)?=null
): RecyclerView.Adapter<LibraryAdapter.ViewHolder>() {

    private val words = mutableListOf<Word>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent)

    override fun getItemCount(): Int = words.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(words[position])
    }

    fun swapData(data:List<Word>){
        words.clear()
        words.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(parent.inflate(R.layout.item_list_library_word)){

        init {
            with(itemView){
                setOnClickListener {
                    clickListener.invoke(words[adapterPosition])
                }
                item_list_delete.setOnClickListener {
                    removeCallback?.invoke(words[adapterPosition])
                }
            }
        }

        fun bind(word: Word){
            with(itemView) {
                item_list_library_word.text = word.word
                item_list_library_transcription.text = word.transcription ?: ""
                item_list_library_translate.setText(
                    HtmlCompat.fromHtml(word.translate, HtmlCompat.FROM_HTML_MODE_LEGACY),
                    TextView.BufferType.SPANNABLE)
            }
        }
    }
}