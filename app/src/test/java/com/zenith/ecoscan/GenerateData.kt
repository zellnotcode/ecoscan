package com.zenith.ecoscan

import com.zenith.ecoscan.domain.entities.Device
import com.zenith.ecoscan.domain.entities.ItemData

object GenerateData {

    fun generateListItemData(): List<ItemData> {
        val items: MutableList<ItemData> = arrayListOf()
        for (i in 0..100) {
            val data = ItemData(
                i.toString(),
                i.toDouble(),
                i.toString(),
                i.toString(),
            )
            items.add(data)
        }
        return items
    }

    fun generateListDevice(): List<Device> {
        val items: MutableList<Device> = arrayListOf()
        for (i in 0..100) {
            val data = Device(
                i.toString(),
                i.toString()
            )
            items.add(data)
        }
        return items
    }
}