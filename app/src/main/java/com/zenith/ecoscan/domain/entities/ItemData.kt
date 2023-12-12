package com.zenith.ecoscan.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemData(
    val lokasi: String? = null,
    val averageEnergy: Double? = null,
    val dampakDisposal: String? = null,
    val sumber: String? = null,
    val dampakProduksi: String? = null,
    val name: String? = null,
    val dampakKonsumsi: String? = null,
    val image: String? = null,
    val recommendations: String? = null,
    val dampakDisposalPanjang: String? = null,
    val dampakProduksiPanjang: String? = null,
    val dampakKonsumsiPanjang: String? = null,
    val result: String? = null,
    val time: String? = null
) : Parcelable
