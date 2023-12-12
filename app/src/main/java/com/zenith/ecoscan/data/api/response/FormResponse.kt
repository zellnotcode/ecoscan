package com.zenith.ecoscan.data.api.response

import com.google.gson.annotations.SerializedName

data class FormResponse(

	@field:SerializedName("energy_usage")
	val energyUsage: EnergyUsage? = null
)