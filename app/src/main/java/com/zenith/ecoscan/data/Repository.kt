package com.zenith.ecoscan.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.zenith.ecoscan.data.api.ApiResponse
import com.zenith.ecoscan.data.api.NetworkDataSource
import com.zenith.ecoscan.data.api.response.EnergyUsage
import com.zenith.ecoscan.data.local.LocalDataSource
import com.zenith.ecoscan.domain.IDataRepository
import com.zenith.ecoscan.domain.Resource
import com.zenith.ecoscan.domain.entities.Device
import com.zenith.ecoscan.domain.entities.Energy
import com.zenith.ecoscan.domain.entities.ItemData
import com.zenith.ecoscan.utils.DataMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val networkDataSource: NetworkDataSource
) :
    IDataRepository {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    override fun uploadPhoto(file: File): LiveData<Resource<ItemData>> = liveData {
        emit(Resource.Loading())

        when (val response =
            networkDataSource.uploadPhoto(file)
        ) {
            is ApiResponse.Success -> {
                val dataEntity = DataMapper.mapItemResponseToEntities(response.data)
                val dataReady = DataMapper.mapItemResponseToDomain(response.data)
                withContext(ioDispatcher) {
                    localDataSource.insertData(dataEntity)
                }
                emit(Resource.Success(dataReady))
            }

            is ApiResponse.Error -> {
                emit(Resource.Error(response.errorMessage))
            }
        }
    }

    override fun getAllHistory(): LiveData<List<ItemData>> = liveData {
        withContext(ioDispatcher) {
            val history = localDataSource.getAllHistory()
            val historyDomain = DataMapper.mapItemEntityToDomain(history)
            emit(historyDomain)
        }
    }

    override fun getDevicesData(location: String): LiveData<Resource<List<Device>>> = liveData {

        when (val response =
            networkDataSource.getDevices(location)
        ) {
            is ApiResponse.Success -> {
                val dataReady = DataMapper.mapListDeviceResponseToDomain(
                    response.data.devices?.filterNotNull()
                        ?: emptyList()
                )
                emit(Resource.Success(dataReady))
            }

            is ApiResponse.Error -> {
                emit(Resource.Error(response.errorMessage))
            }
        }
    }

    override fun calculateEnergy(
        location: String,
        device: String,
        deviceType: String
    ): LiveData<Resource<Energy>> = liveData {

        when (val response =
            networkDataSource.calculateEnergy(location, device, deviceType)
        ) {
            is ApiResponse.Success -> {
                val dataReady = DataMapper.mapEnergyUsageResponseToDomain(
                    response.data.energyUsage ?: EnergyUsage()
                )
                emit(Resource.Success(dataReady))
            }

            is ApiResponse.Error -> {
                emit(Resource.Error(response.errorMessage))
            }
        }
    }
}