package com.zenith.ecoscan.domain

import androidx.lifecycle.LiveData
import com.zenith.ecoscan.domain.entities.Device
import com.zenith.ecoscan.domain.entities.Energy
import com.zenith.ecoscan.domain.entities.ItemData
import java.io.File
import javax.inject.Inject

class DataInteractor @Inject constructor(private val repository: IDataRepository) : DataUseCase {

    override fun uploadPhoto(file: File): LiveData<Resource<ItemData>> {
        return repository.uploadPhoto(file)
    }

    override fun getAllHistory(): LiveData<List<ItemData>> {
        return repository.getAllHistory()
    }

    override fun getDevices(location: String): LiveData<Resource<List<Device>>> {
        return repository.getDevicesData(location)
    }

    override fun calculateEnergy(location: String, device: String, deviceType: String)
            : LiveData<Resource<Energy>> {
        return repository.calculateEnergy(location, device, deviceType)
    }
}