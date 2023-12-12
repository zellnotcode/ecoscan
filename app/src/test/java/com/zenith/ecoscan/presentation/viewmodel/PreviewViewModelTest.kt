package com.zenith.ecoscan.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zenith.ecoscan.MainDispatcherRule
import com.zenith.ecoscan.domain.DataUseCase
import com.zenith.ecoscan.domain.Resource
import com.zenith.ecoscan.domain.entities.ItemData
import com.zenith.ecoscan.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PreviewViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var dataUseCase: DataUseCase

    @Test
    fun `get data from scan file`() = runTest {
        val dummyData = ItemData("1", 1.0, "1", "1")
        val data : Resource<ItemData> = Resource.Success(dummyData)
        val expectedData = MutableLiveData<Resource<ItemData>>()
        expectedData.value = data
        val mockFile = File("/path/that/does/not/exist/AirConditioner.jpg")

        Mockito.`when`(dataUseCase.uploadPhoto(mockFile)).thenReturn(expectedData)

        val previewViewModel = PreviewViewModel(dataUseCase)
        val actualData : Resource<ItemData> = previewViewModel.uploadPhoto(mockFile).getOrAwaitValue()

        assertNotNull(actualData)
        assertEquals(expectedData.value, actualData)
    }
}