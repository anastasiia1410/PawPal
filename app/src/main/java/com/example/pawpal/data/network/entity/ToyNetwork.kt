package com.example.pawpal.data.network.entity

import com.example.pawpal.entity.Toy
import com.google.gson.annotations.SerializedName

data class ToyNetwork(
    @SerializedName("image")
    val image: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("name")
    val name: String,
)

fun ToyNetwork.toToy(): Toy {
    return Toy(
        image = image,
        price = price,
        name = name
    )
}