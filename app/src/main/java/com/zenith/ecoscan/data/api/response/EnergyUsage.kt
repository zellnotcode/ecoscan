package com.zenith.ecoscan.data.api.response

import com.google.gson.annotations.SerializedName

data class EnergyUsage(

	@field:SerializedName("energy_usage")
	val energyUsage: Double? = null,

	@field:SerializedName("device_type")
	val deviceType: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("device")
	val device: String? = null
)