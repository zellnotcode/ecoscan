package com.zenith.ecoscan.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.zenith.ecoscan.GenerateData
import com.zenith.ecoscan.MainDispatcherRule
import com.zenith.ecoscan.domain.DataUseCase
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

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HistoryViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var dataUseCase: DataUseCase

    @Test
    fun `get data history`() = runTest {
        val dummyData = GenerateData.generateListItemData()
        val expectedData = MutableLiveData<List<ItemData>>()
        expectedData.value = dummyData

        Mockito.`when`(dataUseCase.getAllHistory()).thenReturn(expectedData)

        val historyViewModel = HistoryViewModel(dataUseCase)
        val actualData = historyViewModel.getAllHistory().getOrAwaitValue()

        assertNotNull(actualData)
        assertEquals(expectedData.value, actualData)
    }

    @Test
    fun `get data history when history is empty`() = runTest {
        val expectedData = MutableLiveData<List<ItemData>>()
        expectedData.value = null

        Mockito.`when`(dataUseCase.getAllHistory()).thenReturn(expectedData)

        val historyViewModel = HistoryViewModel(dataUseCase)
        val actualData = historyViewModel.getAllHistory().getOrAwaitValue()

        assertNull(actualData)
    }
}