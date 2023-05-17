package com.example.pawpal.data.network

import android.content.Context
import android.net.Uri
import com.example.pawpal.core.App
import com.example.pawpal.core.preference.AppPreference
import com.example.pawpal.data.network.entity.request.CreatePawRequest
import com.example.pawpal.data.network.entity.request.GetPawRequest
import com.example.pawpal.data.network.entity.request.SignUpRequest
import com.example.pawpal.data.network.entity.request.UpdateAvatarRequest
import com.example.pawpal.entity.Paw
import com.example.pawpal.entity.User
import com.example.pawpal.data.network.entity.toBanner
import com.example.pawpal.data.network.entity.toFood
import com.example.pawpal.data.network.entity.toToy
import com.example.pawpal.data.network.entity.toUser
import com.example.pawpal.entity.Banner
import com.example.pawpal.entity.Food
import com.example.pawpal.entity.Toy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream

class NetworkManager(private val context: Context) {
    private val gson: Gson
    private val client: OkHttpClient
    private val retrofit: Retrofit
    private val api: Api
    private val appPreference: AppPreference

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        gson = GsonBuilder().serializeNulls().create()
        retrofit = Retrofit.Builder()
            .baseUrl("https://parseapi.back4app.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        api = retrofit.create(Api::class.java)
        appPreference = App.getInstance(context).appPreferences
    }

    fun logIn(username: String, password: String): Single<String> {
        return api.logging(username, password)
            .doOnSuccess { logInResponse ->
                appPreference.saveToken(logInResponse.token)
                appPreference.saveUserId(logInResponse.userId)
            }
            .map { it.token }
    }

    fun registration(username: String, password: String, email: String): Single<String> {
        return api.signUp(SignUpRequest(username, password, email))
            .doOnSuccess { signUpResponse ->
                appPreference.saveToken(signUpResponse.token)
                appPreference.saveUserId(signUpResponse.userId)
            }
            .map { it.token }
    }

    fun addPaw(name: String, age: Int): Completable {
        val token = appPreference.token
        val userId = appPreference.userId
        val request = CreatePawRequest(name, age, userId!!)
        return api.addPaw(token!!, request)
    }

    fun getPaw(): Single<List<Paw>> {
        val token = appPreference.token
        val userId = appPreference.userId
        val request = GetPawRequest(userId!!)
        val json = gson.toJson(request)
        return api.getPaw(token!!, json)
            .map { it.pawList }
    }

    fun getToys(): Single<List<Toy>> {
        val token = appPreference.token
        return api.getToys(token!!)
            .map { toyResponse ->
                toyResponse.toyList.map { it.toToy() }
            }
    }

    fun getFood(): Single<List<Food>> {
        val token = appPreference.token
        return api.getFood(token!!)
            .map { foodResponse ->
                foodResponse.foodList.map { it.toFood() }
            }
    }

    fun getBanner(): Single<Banner> {
        val token = appPreference.token
        return api.getBanner(token!!)
            .map { bannerResponse ->
                bannerResponse.bannerList[0].toBanner()
            }
    }

    fun getCurrentUser(): Single<User> {
        val token = appPreference.token
        return api.getCurrentUser(token!!)
            .map { userNetwork ->
                userNetwork.toUser()
            }
    }

    fun uploadImage(uri: Uri): Single<String> {
        val fileDir = context.filesDir
        val file = File(fileDir, "image.jpeg")
        FileOutputStream(file).use { outputStream ->
            val inputStream = context.contentResolver.openInputStream(uri)
            inputStream?.use {
                it.copyTo(outputStream)
            }
        }
        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData("image", file.name, requestBody)
        return api.addUserAvatar(multipart = part)
            .map { avatarResponse ->
                avatarResponse.data.avatar
            }
    }

    fun updateAvatar(avatar: String?): Completable {
        val token = appPreference.token
        val userId = appPreference.userId
        val request = UpdateAvatarRequest(avatar)
        return api.updateProfile(token!!, userId!!, request)
    }
}
