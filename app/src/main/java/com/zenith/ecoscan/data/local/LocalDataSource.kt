package com.zenith.ecoscan.data.local

import javax.inject.Inject

class LocalDataSource @Inject constructor(private val dataDao: DataDao) {

    fun getAllHistory() : List<DataEntity> = dataDao.getAllHistory()

    suspend fun insertData(data: DataEntity) = dataDao.insertData(data)

}