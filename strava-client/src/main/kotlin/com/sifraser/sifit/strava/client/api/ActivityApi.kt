package com.sifraser.sifit.strava.client.api

import com.sifraser.sifit.strava.client.dto.ActivityDetails

interface ActivityApi {

    fun getDetails(id: Long): ActivityDetails

    fun updateDescription(id: Long, description: String): ActivityDetails
}