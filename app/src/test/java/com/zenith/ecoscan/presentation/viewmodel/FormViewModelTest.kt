package com.zenith.ecoscan.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.zenith.ecoscan.GenerateData
import com.zenith.ecoscan.MainDispatcherRule
import com.zenith.ecoscan.domain.DataUseCase
import com.zenith.ecoscan.domain.Resource
import com.zenith.ecoscan.domain.entities.Device
import com.zenith.ecoscan.domain.entities.Energy
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

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FormViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var dataUseCase: DataUseCase

    @Test
    fun `get devices data and get the data`() = runTest {
        val dummyData = GenerateData.generateListDevice()
        val data: Resource<List<Device>> = Resource.Success(dummyData)
        val expectedData = MutableLiveData<Resource<List<Device>>>()
        expectedData.value = data
        //tambahin lokasi apa aja
        Mockito.`when`(dataUseCase.getDevices("Dapur")).thenReturn(expectedData)

        val formViewModel = FormViewModel(dataUseCase)
        val actualData = formViewModel.getDevices("Dapur").getOrAwaitValue()

        assertNotNull(actualData)
        assertEquals(expectedData.value, actualData)
    }

    @Test
    fun `get device with jalan parameter and got null`() = runTest {
        val expectedData = MutableLiveData<Resource<List<Device>>>()
        expectedData.value = null

        Mockito.`when`(dataUseCase.getDevices("Jalan")).thenReturn(expectedData)

        val formViewModel = FormViewModel(dataUseCase)
        val actualData = formViewModel.getDevices("Jalan").getOrAwaitValue()

        assertNull(actualData)
    }

    @Test
    fun `calculate energy`() = runTest {
        val dummyData = Energy(99.0, "Plastic-Based Oven", "Dapur")
        val data: Resource<Energy> = Resource.Success(dummyData)
        val expectedData = MutableLiveData<Resource<Energy>>()
        expectedData.value = data

        Mockito.`when`(dataUseCase.calculateEnergy("Dapur", "Oven", "Plastic-Based Oven"))
            .thenReturn(expectedData)

        val formViewModel = FormViewModel(dataUseCase)
        val actualData =
            formViewModel.calculateEnergy("Dapur", "Oven", "Plastic-Based Oven").getOrAwaitValue()

        assertNotNull(actualData)
        assertEquals(expectedData.value, actualData)
    }
}