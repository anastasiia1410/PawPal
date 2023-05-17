package com.example.pawpal.data.network.entity.response

import com.example.pawpal.data.network.entity.BannerNetwork
import com.google.gson.annotations.SerializedName

data class BannerResponse(@SerializedName("results") val bannerList: List<BannerNetwork>)