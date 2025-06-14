package com.sifraser.sbot.client.feign

import com.sifraser.sifit.strava.client.api.GearApi
import com.sifraser.sifit.strava.client.dto.DetailedGear
import feign.Headers
import feign.Param
import feign.RequestLine

class GearAdapter(
        config: StravaConfig
) : GearApi {
    private val client: GearFeignApi = createFeignClient(GearFeignApi::class.java, config)

    override fun getGear(gearId: String) = client.getGear(gearId)
}

@Headers("Content-Type: application/json")
internal interface GearFeignApi {

    @RequestLine("GET /api/v3/gear/{gearId}")
    fun getGear(@Param(value = "gearId") gearId: String): DetailedGear

}