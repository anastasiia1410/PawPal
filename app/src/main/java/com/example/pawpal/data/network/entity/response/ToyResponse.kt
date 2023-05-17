package com.example.pawpal.data.network.entity.response

import com.example.pawpal.data.network.entity.ToyNetwork
import com.google.gson.annotations.SerializedName

data class ToyResponse (@SerializedName("results") val toyList : List<ToyNetwork>)