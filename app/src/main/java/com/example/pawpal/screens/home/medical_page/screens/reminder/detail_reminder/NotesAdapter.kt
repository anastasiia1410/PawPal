package com.example.pawpal.screens.home.medical_page.screens.reminder.detail_reminder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pawpal.databinding.ItemReminderBinding
import com.example.pawpal.screens.home.medical_page.entity.Note
import com.example.pawpal.util.getStringOnlyDate

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.ItemVH>() {
     var noteList : MutableList<Note> = mutableListOf()

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
        val note = noteList[position]
        with(holder.binding){
            tvTitle.text = note.title
            tvDate.text= note.date.getStringOnlyDate()
        }
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun updateItems(newListNotes: List<Note>) {
        noteList.clear()
        noteList.addAll(newListNotes)
        notifyDataSetChanged()
    }

    class ItemVH(val binding: ItemReminderBinding) : RecyclerView.ViewHolder(binding.root)
}