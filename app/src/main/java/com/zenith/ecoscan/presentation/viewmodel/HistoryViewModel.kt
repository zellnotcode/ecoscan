package com.zenith.ecoscan.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zenith.ecoscan.domain.DataUseCase
import com.zenith.ecoscan.domain.entities.ItemData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val useCase: DataUseCase) : ViewModel() {
    fun getAllHistory() : LiveData<List<ItemData>> {
        return useCase.getAllHistory()
    }
}