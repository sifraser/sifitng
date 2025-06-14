package com.sifraser.sifit.strava.client.api

import com.sifraser.sifit.strava.client.dto.DetailedGear

interface GearApi {
    fun getGear(gearId: String) : DetailedGear
}