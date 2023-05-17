package com.example.pawpal.data.network

import com.example.pawpal.data.network.entity.*
import com.example.pawpal.data.network.entity.request.CreatePawRequest
import com.example.pawpal.data.network.entity.request.SignUpRequest
import com.example.pawpal.data.network.entity.response.AvatarResponse
import com.example.pawpal.data.network.entity.response.BannerResponse
import com.example.pawpal.data.network.entity.response.FoodResponse
import com.example.pawpal.data.network.entity.response.LoggingResponse
import com.example.pawpal.data.network.entity.response.PawsResponse
import com.example.pawpal.data.network.entity.response.SignUpResponse
import com.example.pawpal.data.network.entity.response.ToyResponse
import com.example.pawpal.data.network.entity.request.UpdateAvatarRequest
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import retrofit2.http.*

interface Api {
    @GET("login")
    @Headers(
        "X-Parse-Application-Id: pgVc6wVrzVehS6Ji7cggcDDDNqSc89wUwtXIRyTl",
        "X-Parse-REST-API-Key: pX6ubHbyVkwtdyBs2spm0F6UqEgV8NwKybApCw0A",
        "X-Parse-Revocable-Session: 1"
    )
    fun logging(
        @Query("username") username: String,
        @Query("password") password: String,
    ): Single<LoggingResponse>

    @POST("users")
    @Headers(
        "X-Parse-Application-Id: pgVc6wVrzVehS6Ji7cggcDDDNqSc89wUwtXIRyTl",
        "X-Parse-REST-API-Key: pX6ubHbyVkwtdyBs2spm0F6UqEgV8NwKybApCw0A",
        "X-Parse-Revocable-Session: 1",
        "Content-Type: application/json"
    )
    fun signUp(@Body user: SignUpRequest): Single<SignUpResponse>

    @POST("classes/Paw")
    @Headers(
        "X-Parse-Application-Id: pgVc6wVrzVehS6Ji7cggcDDDNqSc89wUwtXIRyTl",
        "X-Parse-REST-API-Key: pX6ubHbyVkwtdyBs2spm0F6UqEgV8NwKybApCw0A",
        "Content-Type: application/json"
    )
    fun addPaw(
        @Header("X-Parse-Session-Token") sessionToken: String,
        @Body request: CreatePawRequest,
    ): Completable

    @GET("classes/Paw")
    @Headers(
        "X-Parse-Application-Id: pgVc6wVrzVehS6Ji7cggcDDDNqSc89wUwtXIRyTl",
        "X-Parse-REST-API-Key: pX6ubHbyVkwtdyBs2spm0F6UqEgV8NwKybApCw0A"
    )
    fun getPaw(
        @Header("X-Parse-Session-Token") sessionToken: String,
        @Query("where") json: String,
    ): Single<PawsResponse>

    @GET("classes/Toys")
    @Headers(
        "X-Parse-Application-Id: pgVc6wVrzVehS6Ji7cggcDDDNqSc89wUwtXIRyTl",
        "X-Parse-REST-API-Key: pX6ubHbyVkwtdyBs2spm0F6UqEgV8NwKybApCw0A"
    )
    fun getToys(
        @Header("X-Parse-Session-Token") sessionToken: String,
    ): Single<ToyResponse>

    @GET("classes/Food")
    @Headers(
        "X-Parse-Application-Id: pgVc6wVrzVehS6Ji7cggcDDDNqSc89wUwtXIRyTl",
        "X-Parse-REST-API-Key: pX6ubHbyVkwtdyBs2spm0F6UqEgV8NwKybApCw0A"
    )
    fun getFood(
        @Header("X-Parse-Session-Token") sessionToken: String,
    ): Single<FoodResponse>

    @GET("classes/Banner")
    @Headers(
        "X-Parse-Application-Id: pgVc6wVrzVehS6Ji7cggcDDDNqSc89wUwtXIRyTl",
        "X-Parse-REST-API-Key: pX6ubHbyVkwtdyBs2spm0F6UqEgV8NwKybApCw0A"
    )
    fun getBanner(
        @Header("X-Parse-Session-Token") sessionToken: String,
    ): Single<BannerResponse>

    @GET("users/me")
    @Headers(
        "X-Parse-Application-Id: pgVc6wVrzVehS6Ji7cggcDDDNqSc89wUwtXIRyTl",
        "X-Parse-REST-API-Key: pX6ubHbyVkwtdyBs2spm0F6UqEgV8NwKybApCw0A"
    )
    fun getCurrentUser(@Header("X-Parse-Session-Token") sessionToken: String): Single<UserNetwork>

    @Multipart
    @POST
    fun addUserAvatar(
        @Url url: String = "https://api.imgbb.com/1/upload",
        @Query("key") key: String = "451e5f75182faf09b2fe6e08d52b4ac6",
        @Part multipart: MultipartBody.Part,
    ): Single<AvatarResponse>

    @PUT("users/{userId}")
    @Headers(
        "X-Parse-Application-Id: pgVc6wVrzVehS6Ji7cggcDDDNqSc89wUwtXIRyTl",
        "X-Parse-REST-API-Key: pX6ubHbyVkwtdyBs2spm0F6UqEgV8NwKybApCw0A",
        "Content-Type: application/json"
    )
    fun updateProfile(
        @Header("X-Parse-Session-Token") sessionToken: String,
        @Path("userId") userId: String,
        @Body request : UpdateAvatarRequest,
    ): Completable
}