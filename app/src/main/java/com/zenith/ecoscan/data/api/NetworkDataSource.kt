package com.zenith.ecoscan.data.api

import com.zenith.ecoscan.data.api.response.DeviceResponse
import com.zenith.ecoscan.data.api.response.FormResponse
import com.zenith.ecoscan.data.api.response.ItemResponse
import com.zenith.ecoscan.utils.reduceFileImage
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class NetworkDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun uploadPhoto(file: File): ApiResponse<ItemResponse> {
        val newFile = reduceFileImage(file)
        val fileReady = newFile.asRequestBody("image/jpg".toMediaType())
        val multipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "file",
            newFile.name,
            fileReady
        )
        return try {
            val response = apiService.uploadPhoto(multipart)
            ApiResponse.Success(response)
        } catch (e: Exception) {
            ApiResponse.Error(e.message ?: "")
        }
    }
    suspend fun getDevices(location: String): ApiResponse<DeviceResponse> {
        return try {
            val response = apiService.getDevices(location)
            ApiResponse.Success(response)
        } catch (e: Exception) {
            ApiResponse.Error(e.message ?: "")
        }
    }
    suspend fun calculateEnergy(
        location: String,
        device: String,
        deviceType: String
    ): ApiResponse<FormResponse> {
        val requestBody = mapOf(
            "location" to location,
            "device" to device,
            "device_type" to deviceType
        )
        return try {
            val response = apiService.calculateEnergy(requestBody)
            ApiResponse.Success(response)
        } catch (e: Exception) {
            ApiResponse.Error(e.message ?: "")
        }
    }
}