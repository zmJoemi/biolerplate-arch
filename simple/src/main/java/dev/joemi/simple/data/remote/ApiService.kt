package dev.joemi.simple.data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("api/bilibili-rs/")
    suspend fun getBiliBiliRS(): Response<ResponseBody>
}