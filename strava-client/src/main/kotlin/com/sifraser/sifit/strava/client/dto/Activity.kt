package com.sifraser.sifit.strava.client.dto

import java.time.LocalDateTime

interface Activity {
    val id: Long
}

data class MetaActivity(
    override val id: Long
) : Activity

enum class ActivityType {
    All,
    AllRide,
    AllRun,
    AlpineSki,
    BackcountrySki,
    Canoeing,
    Crossfit,
    EBikeRide,
    Elliptical,
    Hike,
    IceSkate,
    InlineSkate,
    Kayaking,
    Kitesurf,
    NordicSki,
    Ride,
    RockClimbing,
    RollerSki,
    Rowing,
    Run,
    Snowboard,
    Snowshoe,
    StairStepper,
    StandUpPaddling,
    Surfing,
    Swim,
    VirtualRide,
    VirtualRun,
    Walk,
    WeightTraining,
    Windsurf,
    Workout,
    Yoga;

    fun includes(other: ActivityType) =
        when (this) {
            All -> true
            AllRide -> other in setOf(Ride, VirtualRide, EBikeRide)
            AllRun -> other in setOf(Run, VirtualRun)
            else -> other == this
        }

}

data class SummaryActivity(
    override val id: Long,
    val type: ActivityType,
    val name: String,
    val distance: Double,
    val movingTime: Int,
    val elapsedTime: Int,
    val totalElevationGain: Double,
    val elevHigh: Double,
    val elevLow: Double,
    val startDate: LocalDateTime,
    val startLatlng: List<Double>,
    val endLatlng: List<Double>,
    val averageSpeed: Double,
    val maxSpeed: Double,
    val averageWatts: Double,
    val deviceWatts: Boolean,
    val averageCadence: Double?,
    val averageHeartrate: Double?,
    val gearId: String?
) : Activity

data class ActivityDetails(
    override val id: Long,
    val description: String?,
//        val gear: SummaryGear?,
//        val segmentEfforts: List<DetailedSegmentEffort>?,
//        val bestEfforts: List<DetailedSegmentEffort>?
) : Activity

data class UpdatableActivity(
//    val commute: Boolean? = null,
//    val trainer: Boolean? = null,
//    val hideFromHome: Boolean? = null,
    val description: String? = null,
//    val name: String? = null,
//    val type: ActivityType? = null,
//    val sportType: ActivityType? = null, // ToDo SportType
//    val gearId: String? = null,
)

data class ActivityStats(
    val biggestRideDistance: Double,
    val biggestClimbElevationGain: Double,
    val recentRideTotals: ActivityTotal,
    val recentRunTotals: ActivityTotal,
    val recentSwimTotals: ActivityTotal,
    val ytdRideTotals: ActivityTotal,
    val ytdRunTotals: ActivityTotal,
    val ytdSwimTotals: ActivityTotal,
    val allRideTotals: ActivityTotal,
    val allRunTotals: ActivityTotal,
    val allSwimTotals: ActivityTotal
)

data class ActivityTotal(
    val count: Int,
    val distance: Double,
    val movingTime: Int,
    val elapsedTime: Int,
    val elevationGain: Double,
    val achievementCount: Int
)

data class ActivityZone(
    val score: Int,
    //       val distributionBuckets: TimedZoneDistribution, ???
    val type: ActivityZoneType,
    val sensorBased: Boolean,
    val points: Int,
    val customZones: Boolean,
    val max: Int
)

enum class ActivityZoneType {
    Heartrate,
    power
}

data class BaseStream(
    val originalSize: Int,
    val resolution: BaseStreamResolution,
    val seriesType: SeriesType
)

enum class SeriesType {
    distance,
    time
}

enum class BaseStreamResolution {
    low,
    medium,
    high
}

data class ClubAnnouncement(
    val id: Long,
    val clubId: Long,
//        val athlete: SummaryAthlete,
    val createdAt: LocalDateTime,
    val message: String
)