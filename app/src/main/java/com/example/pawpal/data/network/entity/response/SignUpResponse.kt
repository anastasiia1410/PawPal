package com.example.pawpal.data.network.entity.response

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("sessionToken") val token: String,
    @SerializedName("objectId") val userId: String,
)