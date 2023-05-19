package com.example.pawpal.screens.home.medical_page.screens.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pawpal.databinding.ItemReminderBinding
import com.example.pawpal.screens.home.medical_page.entity.Note
import com.example.pawpal.util.getStringOnlyDate

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.ItemVH>() {
    private val items: MutableList<Note> = mutableListOf()
    var onItemClick: ((id: Long) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVH {
        return ItemVH(
            ItemReminderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemVH, position: Int) {
        val item = items[position]
        with(holder.binding) {
            tvTitle.text = item.title
            tvDate.text = item.date.getStringOnlyDate()
            cvCardView.setOnClickListener {
                onItemClick?.invoke(item.reminderId)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(newItems : List<Note>){
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }


    class ItemVH(val binding: ItemReminderBinding) : RecyclerView.ViewHolder(binding.root)
}