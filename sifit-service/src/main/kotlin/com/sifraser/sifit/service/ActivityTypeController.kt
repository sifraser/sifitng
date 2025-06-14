package com.sifraser.sifit.service

import com.sifraser.sifit.strava.client.dto.ActivityType
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class ActivityTypeController(
    val stravaManager: StravaManager
) {

    @CrossOrigin(origins = ["http://localhost:5173"])
    @GetMapping("/activityTypes")
    fun activityTypes(): List<ActivityType> {
        val detailTypes = stravaManager.data.summaryActivities.map { (_, a) -> a.type }.toSet()
        val summaryTypes = ActivityType.entries.filter { st -> detailTypes.any { st.includes(it) } }
        return (detailTypes + summaryTypes).sortedBy { it.name }
    }

}