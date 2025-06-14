package com.sifraser.sifit.strava.client.api

import com.sifraser.sifit.strava.client.dto.DetailedAthlete
import com.sifraser.sifit.strava.client.dto.SummaryActivity
import java.time.LocalDateTime

interface AthleteApi {

    fun getCurrent() : DetailedAthlete

    fun listActivitiesForCurrent(
            before: LocalDateTime? = null,
            after: LocalDateTime? = null,
            page: Int = 1,
            perPage: Int = 200
    ): List<SummaryActivity>
}