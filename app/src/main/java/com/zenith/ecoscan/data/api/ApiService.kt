package com.zenith.ecoscan.data.api

import com.zenith.ecoscan.data.api.response.DeviceResponse
import com.zenith.ecoscan.data.api.response.FormResponse
import com.zenith.ecoscan.data.api.response.ItemResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {

    @Multipart
    @POST("predict")
    suspend fun uploadPhoto(
        @Part file: MultipartBody.Part
    ) : ItemResponse

    @GET("devices")
    suspend fun getDevices(
        @Query("location") location: String
    ) : DeviceResponse

    @POST("form")
    suspend fun calculateEnergy(@Body requestBody: Map<String, String>
    ): FormResponse
}