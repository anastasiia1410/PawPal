package com.example.pawpal.data.network.entity.response

import com.example.pawpal.entity.Avatar
import com.google.gson.annotations.SerializedName

data class AvatarResponse(@SerializedName("data") val data : Avatar)