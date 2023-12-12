package com.zenith.ecoscan.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "history_data", indices = [Index(value = ["name"], unique = true)])
data class DataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val lokasi: String?,
    val averageEnergy: String?,
    val dampakDisposal: String?,
    val dampakProduksi: String?,
    val name: String?,
    val dampakKonsumsi: String?,
    val image: String?,
    val linkSumber: String?,
    val recommendations: String?,
    val dampakDisposalPanjang: String?,
    val dampakProduksiPanjang: String?,
    val dampakKonsumsiPanjang: String?,
    val result: String?,
    val time: String?,
)