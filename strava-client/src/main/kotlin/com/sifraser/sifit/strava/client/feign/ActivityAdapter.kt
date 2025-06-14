package com.sifraser.sifit.strava.client.feign

import com.sifraser.sbot.client.feign.StravaConfig
import com.sifraser.sbot.client.feign.createFeignClient
import com.sifraser.sifit.strava.client.api.ActivityApi
import com.sifraser.sifit.strava.client.dto.ActivityDetails
import com.sifraser.sifit.strava.client.dto.UpdatableActivity
import feign.Headers
import feign.Param
import feign.RequestLine

class ActivityAdapter(
    config: StravaConfig
) : ActivityApi {

    private val client: ActivityFeignApi = createFeignClient(ActivityFeignApi::class.java, config)

    override fun getDetails(id: Long) = client.getDetails(id)

    override fun updateDescription(id: Long, description: String) = client.update(
        id,
        UpdatableActivity(
            description = description
        )
    )
}

@Headers("Content-Type: application/json")
internal interface ActivityFeignApi {

    @RequestLine("GET /api/v3/activities/{id}")
    fun getDetails(@Param(value = "id") id: Long): ActivityDetails

    @RequestLine("PUT /api/v3/activities/{id}")
    fun update(
        @Param(value = "id") id: Long,
        updatableActivity: UpdatableActivity
    ): ActivityDetails

}