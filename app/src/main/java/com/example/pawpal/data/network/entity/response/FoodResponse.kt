package com.example.pawpal.data.network.entity.response

import com.example.pawpal.data.network.entity.FoodNetwork
import com.google.gson.annotations.SerializedName

data class FoodResponse (@SerializedName("results") val foodList : List<FoodNetwork>)