package com.example.pawpal.screens.home.head_page

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pawpal.R
import com.example.pawpal.databinding.ItemDashboardBinding
import com.example.pawpal.databinding.ItemSeeAllBinding
import com.example.pawpal.entity.Food
import com.example.pawpal.screens.home.head_page.entity.Item
import com.example.pawpal.entity.Toy
import com.example.pawpal.util.layoutInflater
import com.example.pawpal.util.loadImage

class HeadAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items: MutableList<Item> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE -> ItemVH(
                ItemDashboardBinding.inflate(
                    parent.layoutInflater,
                    parent,
                    false
                )
            )

            SEE_ALL_VIEW_TYPE -> SeeAllVH(
                ItemSeeAllBinding.inflate(
                    parent.layoutInflater,
                    parent,
                    false
                )
            )

            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (holder is ItemVH) holder.fillItem(item)
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Item.FoodItem, is Item.ToyItem -> ITEM_VIEW_TYPE
            Item.SeeAll -> SEE_ALL_VIEW_TYPE
        }
    }

    fun updateItems(newItems: List<Item>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    class ItemVH(private val binding: ItemDashboardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private fun fillFood(food: Food) {
            binding.tvName.text = food.name
            binding.tvPrice.text =
                binding.tvPrice.context.getString(R.string.grn_pattern, food.price)
            binding.ivImage.loadImage(food.image)
        }

        private fun fillToy(toy: Toy) {
            binding.tvName.text = toy.name
            binding.tvPrice.text =
                binding.tvPrice.context.getString(R.string.grn_pattern, toy.price)
            binding.ivImage.loadImage(toy.image)
        }

        fun fillItem(item: Item) {
            if (item is Item.FoodItem) {
                fillFood(item.food)
            } else if (item is Item.ToyItem) {
                fillToy(item.toy)
            }
        }
    }

    class SeeAllVH(binding: ItemSeeAllBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        private const val ITEM_VIEW_TYPE = 1
        private const val SEE_ALL_VIEW_TYPE = 2
    }
}