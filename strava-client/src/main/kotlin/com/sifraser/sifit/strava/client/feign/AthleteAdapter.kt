package com.sifraser.sifit.strava.client.feign

import com.sifraser.sbot.client.feign.StravaConfig
import com.sifraser.sbot.client.feign.createFeignClient
import com.sifraser.sifit.strava.client.api.AthleteApi
import com.sifraser.sifit.strava.client.dto.DetailedAthlete
import com.sifraser.sifit.strava.client.dto.SummaryActivity
import feign.Headers
import feign.QueryMap
import feign.RequestLine
import java.time.LocalDateTime
import java.time.ZoneOffset

class AthleteAdapter(
    config: StravaConfig

) : AthleteApi {

    private val client: AthleteFeignApi = createFeignClient(AthleteFeignApi::class.java, config)

    override fun getCurrent(): DetailedAthlete = client.getCurrent()

    override fun listActivitiesForCurrent(
        before: LocalDateTime?,
        after: LocalDateTime?,
        page: Int,
        perPage: Int
    ) = client.listActivitiesForCurrent(
        mapOf(
            "before" to before?.toEpochSecond(ZoneOffset.UTC),
            "after" to after?.toEpochSecond(ZoneOffset.UTC),
            "page" to page,
            "per_page" to perPage
        )
    )
}

@Headers("Content-Type: application/json")
internal interface AthleteFeignApi {

    @RequestLine("GET /api/v3/athlete")
    fun getCurrent(): DetailedAthlete

    @RequestLine("GET /api/v3/athlete/activities")
    fun listActivitiesForCurrent(@QueryMap queryMap: Map<String, Any?>): List<SummaryActivity>

}