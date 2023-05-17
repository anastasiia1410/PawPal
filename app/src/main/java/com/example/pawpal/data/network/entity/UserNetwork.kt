package com.example.pawpal.data.network.entity

import com.example.pawpal.entity.User
import com.google.gson.annotations.SerializedName


data class UserNetwork(
    @SerializedName("username")
    val username: String,
    @SerializedName("objectId")
    val userId: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("avatar")
    val avatar: String?,
)

fun UserNetwork.toUser(): User {
    return User(
        username = username,
        userId = userId,
        email = email,
        avatar = avatar
    )
}


