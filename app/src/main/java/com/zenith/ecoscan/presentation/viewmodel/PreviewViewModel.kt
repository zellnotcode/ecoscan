package com.zenith.ecoscan.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zenith.ecoscan.domain.DataUseCase
import com.zenith.ecoscan.domain.Resource
import com.zenith.ecoscan.domain.entities.ItemData
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PreviewViewModel @Inject constructor(private val dataUseCase: DataUseCase) : ViewModel() {
    fun uploadPhoto(file: File) : LiveData<Resource<ItemData>> {
        return dataUseCase.uploadPhoto(file)
    }
}