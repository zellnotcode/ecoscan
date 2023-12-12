package com.zenith.ecoscan.data.api.response

import com.google.gson.annotations.SerializedName

data class DeviceResponse(

	@field:SerializedName("devices")
	val devices: List<DevicesItem?>? = null
)