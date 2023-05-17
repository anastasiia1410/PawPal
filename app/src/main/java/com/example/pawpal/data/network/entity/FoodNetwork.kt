package com.example.pawpal.data.network.entity

import com.example.pawpal.entity.Food
import com.google.gson.annotations.SerializedName

data class FoodNetwork(
    @SerializedName("image")
    val image: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("name")
    val name: String,
)

fun FoodNetwork.toFood(): Food {
    return Food(
        image = image,
        price = price,
        name = name
    )
}