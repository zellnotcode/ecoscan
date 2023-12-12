package com.zenith.ecoscan.data.api.response

import com.google.gson.annotations.SerializedName

data class ItemResponse(

	@field:SerializedName("item_info")
	val itemInfo: ItemInfo? = null
)