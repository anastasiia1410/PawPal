package com.example.pawpal.data.network.entity.response

import com.example.pawpal.entity.Paw
import com.google.gson.annotations.SerializedName

data class PawsResponse(@SerializedName("results") val pawList : List<Paw>)