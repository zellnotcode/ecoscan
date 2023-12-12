package com.zenith.ecoscan.data.api.response

import com.google.gson.annotations.SerializedName

data class ItemInfo(

	@field:SerializedName("Dampak Produksi Pendek")
	val dampakProduksiPendek: String? = null,

	@field:SerializedName("Sumber")
	val sumber: String? = null,

	@field:SerializedName("Dampak Disposal Panjang")
	val dampakDisposalPanjang: String? = null,

	@field:SerializedName("Dampak Produksi Panjang")
	val dampakProduksiPanjang: String? = null,

	@field:SerializedName("Dampak Konsumsi Panjang")
	val dampakKonsumsiPanjang: String? = null,

	@field:SerializedName("Dampak Konsumsi Pendek")
	val dampakKonsumsiPendek: String? = null,

	@field:SerializedName("Dampak Disposal Pendek")
	val dampakDisposalPendek: String? = null,

	@field:SerializedName("Image")
	val image: String? = null,

	@field:SerializedName("recommendations")
	val recommendations: String? = null,

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("Lokasi")
	val lokasi: String? = null,

	@field:SerializedName("Average Energy")
	val averageEnergy: Double? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("time")
	val time: String? = null
)