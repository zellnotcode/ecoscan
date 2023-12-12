package com.zenith.ecoscan.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zenith.ecoscan.domain.DataUseCase
import com.zenith.ecoscan.domain.Resource
import com.zenith.ecoscan.domain.entities.Device
import com.zenith.ecoscan.domain.entities.Energy
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FormViewModel @Inject constructor(private val useCase: DataUseCase): ViewModel() {
    fun getDevices(location: String): LiveData<Resource<List<Device>>> {
        return useCase.getDevices(location)
    }

    fun calculateEnergy(location: String, device: String, deviceType: String)
        : LiveData<Resource<Energy>> {
        return useCase.calculateEnergy(location, device, deviceType)
    }
}