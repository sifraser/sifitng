package com.sifraser.sifit.strava.client.api

interface Strava {
    val activities: ActivityApi
    val athletes: AthleteApi
    val gears: GearApi
}