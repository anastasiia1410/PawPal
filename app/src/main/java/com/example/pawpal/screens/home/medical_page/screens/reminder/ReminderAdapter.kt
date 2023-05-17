package com.example.pawpal.screens.home.medical_page.screens.reminder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pawpal.databinding.ItemGroupBinding
import com.example.pawpal.databinding.ItemReminderBinding
import com.example.pawpal.screens.home.medical_page.entity.ListItems
import java.text.SimpleDateFormat
import java.util.Locale

class ReminderAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items: MutableList<ListItems> = mutableListOf()
    private val dateFormat: SimpleDateFormat = SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())
    var onItemClick: ((id: Long) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            REMINDER_VIEW_TYPE -> ReminderVH(
                ItemReminderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            GROUP_VIEW_TYPE -> GroupVH(
                ItemGroupBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> throw java.lang.IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ReminderVH -> {
                val reminderItem = items[position] as ListItems.ReminderItem
                with(holder.binding) {
                    tvTitle.text = reminderItem.reminder.title
                    tvDate.text = dateFormat.format(reminderItem.reminder.date)
                    cvCardView.setOnClickListener {
                        onItemClick?.invoke(reminderItem.reminder.id)
                    }
                }
            }

            is GroupVH -> {
                val groupItem = items[position] as ListItems.GroupItem
                holder.binding.tvDate.text = groupItem.title
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ListItems.ReminderItem -> REMINDER_VIEW_TYPE
            is ListItems.GroupItem -> GROUP_VIEW_TYPE
        }
    }

    fun updateItems(newListItems: List<ListItems>) {
        items.clear()
        items.addAll(newListItems)
        notifyDataSetChanged()
    }

    class ReminderVH(val binding: ItemReminderBinding) : RecyclerView.ViewHolder(binding.root)
    class GroupVH(val binding: ItemGroupBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        const val REMINDER_VIEW_TYPE = 1
        const val GROUP_VIEW_TYPE = 2
    }
}