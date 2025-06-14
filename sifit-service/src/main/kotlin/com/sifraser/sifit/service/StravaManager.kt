package com.sifraser.sifit.service

import com.sifraser.sbot.client.feign.StravaConfig
import com.sifraser.sbot.client.feign.createStravaClient
import com.sifraser.sifit.strava.client.api.Strava
import com.sifraser.sifit.strava.client.dto.SummaryActivity
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class StravaManager(
    val data: StravaData
) {

    companion object {
        private val Log = LoggerFactory.getLogger(StravaManager::class.java)
    }

}