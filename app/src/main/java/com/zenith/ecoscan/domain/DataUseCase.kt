package com.zenith.ecoscan.domain

import androidx.lifecycle.LiveData
import com.zenith.ecoscan.domain.entities.Device
import com.zenith.ecoscan.domain.entities.Energy
import com.zenith.ecoscan.domain.entities.ItemData
import java.io.File

interface DataUseCase {
    fun uploadPhoto(file: File) : LiveData<Resource<ItemData>>

    fun getAllHistory() : LiveData<List<ItemData>>

    fun getDevices(location: String): LiveData<Resource<List<Device>>>

    fun calculateEnergy(location: String, device: String, deviceType: String)
            : LiveData<Resource<Energy>>
}