package com.example.pawpal.data.network.entity.response

import com.google.gson.annotations.SerializedName


data class LoggingResponse(
    @SerializedName("sessionToken")
    val token: String,
    @SerializedName("objectId")
    val userId: String,
    @SerializedName("email")
    val userEmail : String,
    @SerializedName("username")
    val userLogin : String
)
