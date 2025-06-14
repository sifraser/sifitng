package com.sifraser.sifit.strava.client.dto

import java.time.LocalDateTime

interface Athlete {
    val id: Long
}

data class DetailedAthlete(
        override val id: Long,
        val firstname: String,
        val lastname: String,
        val city: String,
        val state: String,
        val country: String?,
        val sex: AthleteSex,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime
) : Athlete

data class MetaAthlete(
        override val id: Long
) : Athlete

data class SummaryAthlete(
        override val id: Long,
        val firstname: String,
        val lastname: String,
        val city: String,
        val state: String,
        val country: String?,
        val sex: AthleteSex,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime
) : Athlete

enum class AthleteSex {
    M,
    F
}