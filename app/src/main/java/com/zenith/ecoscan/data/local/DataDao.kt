package com.zenith.ecoscan.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DataDao {

    @Query("SELECT * FROM history_data")
    fun getAllHistory() : List<DataEntity>

    @Query("SELECT * FROM history_data WHERE name = :name")
    fun getData(name: String) : DataEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(dataEntity: DataEntity)

}