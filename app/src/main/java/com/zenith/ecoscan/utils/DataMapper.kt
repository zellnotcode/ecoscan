package com.zenith.ecoscan.utils

import com.zenith.ecoscan.data.api.response.DevicesItem
import com.zenith.ecoscan.data.api.response.EnergyUsage
import com.zenith.ecoscan.data.api.response.ItemResponse
import com.zenith.ecoscan.data.local.DataEntity
import com.zenith.ecoscan.domain.entities.Device
import com.zenith.ecoscan.domain.entities.Energy
import com.zenith.ecoscan.domain.entities.ItemData

object DataMapper {
    fun mapItemResponseToEntities(input: ItemResponse): DataEntity {
        val data = with(input.itemInfo) {
            DataEntity(
                id = 0,
                lokasi = this?.lokasi,
                averageEnergy = this?.averageEnergy.toString(),
                dampakDisposal = this?.dampakDisposalPendek,
                dampakProduksi = this?.dampakProduksiPendek,
                name = this?.name,
                dampakKonsumsi = this?.dampakKonsumsiPendek,
                image = this?.image,
                linkSumber = this?.sumber,
                recommendations = this?.recommendations,
                dampakDisposalPanjang = this?.dampakDisposalPanjang,
                dampakProduksiPanjang = this?.dampakProduksiPanjang,
                dampakKonsumsiPanjang = this?.dampakKonsumsiPanjang,
                result = this?.result,
                time = this?.time,
            )
        }
        return data
    }

    fun mapItemResponseToDomain(input: ItemResponse): ItemData {
        val data = with(input.itemInfo) {
            ItemData(
                lokasi = this?.lokasi,
                averageEnergy = this?.averageEnergy,
                dampakDisposal = this?.dampakDisposalPendek,
                dampakProduksi = this?.dampakProduksiPendek,
                name = this?.name,
                dampakKonsumsi = this?.dampakKonsumsiPendek,
                image = this?.image,
                sumber = this?.sumber,
                recommendations = this?.recommendations,
                dampakDisposalPanjang = this?.dampakDisposalPanjang,
                dampakProduksiPanjang = this?.dampakProduksiPanjang,
                dampakKonsumsiPanjang = this?.dampakKonsumsiPanjang,
                result = this?.result,
                time = this?.time,
            )
        }
        return data
    }

    fun mapItemEntityToDomain(input: List<DataEntity>): List<ItemData> =
        input.map {
            ItemData(
                lokasi = it.lokasi,
                averageEnergy = it.averageEnergy?.toDouble(),
                dampakDisposal = it.dampakDisposal,
                dampakProduksi = it.dampakProduksi,
                name = it.name,
                dampakKonsumsi = it.dampakKonsumsi,
                image = it.image,
                sumber = it.linkSumber,
                recommendations = it.recommendations,
                dampakDisposalPanjang = it.dampakDisposalPanjang,
                dampakProduksiPanjang = it.dampakProduksiPanjang,
                dampakKonsumsiPanjang = it.dampakKonsumsiPanjang,
                result = it.result,
                time = it.time,
            )
        }


    fun mapListDeviceResponseToDomain(input: List<DevicesItem>): List<Device> =
        input.map {
            Device(
                detailedType = it.detailedType,
                device = it.device
            )
        }

    fun mapEnergyUsageResponseToDomain(input: EnergyUsage) : Energy =
        Energy(
            energyUsage = input.energyUsage,
            device = input.device,
            deviceType = input.deviceType,
            location = input.location
        )
}