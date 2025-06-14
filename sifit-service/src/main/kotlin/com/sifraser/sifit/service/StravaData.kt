package com.sifraser.sifit.service

import com.sifraser.sifit.strava.client.dto.SummaryActivity
import org.slf4j.LoggerFactory
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.ConcurrentHashMap

@Component
class StravaData(
    val summaryActivityRepository: SummaryActivityRepository,
) {

    val summaryActivities = ConcurrentHashMap(
        summaryActivityRepository.getAll().associateBy { it.id })

    @Transactional
    fun add(summaryActivity: SummaryActivity) {
        try {
            summaryActivityRepository.store(summaryActivity)
            summaryActivities[summaryActivity.id] = summaryActivity
        } catch (e: DuplicateKeyException) {
            val existing = summaryActivities[summaryActivity.id]
            Log.error("Trying to insert duplicate summary activity: existing=$existing new=$summaryActivity")
        }
    }

    fun hasSummaryActivity(summaryActivity: SummaryActivity) =
        summaryActivities.containsKey(summaryActivity.id)

    companion object {
        private val Log = LoggerFactory.getLogger(StravaManager::class.java)
    }


}