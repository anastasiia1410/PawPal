package com.example.pawpal.screens.home.head_page.entity

import com.example.pawpal.entity.Food
import com.example.pawpal.entity.Toy

sealed class Item {
    data class FoodItem(val food : Food) : Item()
    data class ToyItem(val toy : Toy) : Item()
    object SeeAll : Item()
}