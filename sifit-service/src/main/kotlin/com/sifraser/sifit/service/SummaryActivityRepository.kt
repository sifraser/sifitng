package com.sifraser.sifit.service

import com.sifraser.sifit.strava.client.dto.ActivityType
import com.sifraser.sifit.strava.client.dto.SummaryActivity
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import java.sql.ResultSet
import java.time.Instant
import java.time.LocalDateTime
import java.util.*


@Component
class SummaryActivityRepository(
    val jdbcTemplate: JdbcTemplate
) {

    private object Columns {
         const val id = "id"
         const val type = "type"
         const val name = "name"
         const val distance = "distance"
         const val movingTime = "moving_time"
         const val elapsedTime = "elapsed_time"
         const val totalElevationGain = "total_elevation_gain"
         const val elevHigh = "elev_high"
         const val elevLow = "elev_low"
         const val startDate = "start_date"
         const val startLat = "start_lat"
         const val startLng = "start_lng"
         const val endLat = "end_lat"
         const val endLng = "end_lng"
         const val averageSpeed = "average_speed"
         const val maxSpeed = "max_speed"
         const val averageWatts = "average_watts"
         const val deviceWatts = "device_watts"
         const val averageCadence = "average_cadence"
         const val averageHeartrate = "average_heartrate"
         const val gearId = "gear_id"
    }
    
    fun store(summaryActivity: SummaryActivity) =
        jdbcTemplate.update(
            """
                    INSERT INTO summary_activities(
                        ${Columns.id},
                        ${Columns.type},
                        ${Columns.name},
                        ${Columns.distance},
                        ${Columns.movingTime},
                        ${Columns.elapsedTime},
                        ${Columns.totalElevationGain},
                        ${Columns.elevHigh},
                        ${Columns.elevLow},
                        ${Columns.startDate},
                        ${Columns.startLat},
                        ${Columns.startLng},
                        ${Columns.endLat},
                        ${Columns.endLng},
                        ${Columns.averageSpeed},
                        ${Columns.maxSpeed},
                        ${Columns.averageWatts},
                        ${Columns.deviceWatts},
                        ${Columns.averageCadence},
                        ${Columns.averageHeartrate},
                        ${Columns.gearId}
                    ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
                """,
            summaryActivity.id,
            summaryActivity.type.name,
            summaryActivity.name,
            summaryActivity.distance,
            summaryActivity.movingTime,
            summaryActivity.elapsedTime,
            summaryActivity.totalElevationGain,
            summaryActivity.elevHigh,
            summaryActivity.elevLow,
            summaryActivity.startDate,
            summaryActivity.startLatlng?.getOrNull(0),
            summaryActivity.startLatlng?.getOrNull(1),
            summaryActivity.endLatlng?.getOrNull(0),
            summaryActivity.endLatlng?.getOrNull(1),
            summaryActivity.averageSpeed,
            summaryActivity.maxSpeed,
            summaryActivity.averageWatts,
            summaryActivity.deviceWatts,
            summaryActivity.averageCadence,
            summaryActivity.averageHeartrate,
            summaryActivity.gearId
        )

    fun getAll(): List<SummaryActivity> = jdbcTemplate.query("SELECT * FROM summary_activities", SummaryActivityRowMapper())


    private class SummaryActivityRowMapper : RowMapper<SummaryActivity> {
        override fun mapRow(rs: ResultSet, rowNum: Int) = SummaryActivity(
            id = rs.getLong(Columns.id),
            type = ActivityType.valueOf(rs.getString(Columns.type)),
            name = rs.getString(Columns.name),
            gearId = rs.getString(Columns.gearId),
            distance = rs.getDouble(Columns.distance),
            totalElevationGain = rs.getDouble(Columns.totalElevationGain),
            elevHigh = rs.getDouble(Columns.elevHigh),
            elevLow = rs.getDouble(Columns.elevLow),
            averageSpeed = rs.getDouble(Columns.averageSpeed),
            maxSpeed = rs.getDouble(Columns.maxSpeed),
            averageWatts = rs.getDouble(Columns.averageWatts),
            averageCadence = rs.getDoubleOrNull(Columns.averageCadence),
            averageHeartrate = rs.getDoubleOrNull(Columns.averageHeartrate),
            startLatlng = getLatLng(rs, Columns.startLat, Columns.startLng),
            endLatlng = getLatLng(rs, Columns.endLat, Columns.endLng),
            deviceWatts = rs.getBoolean(Columns.deviceWatts),
            elapsedTime = rs.getInt(Columns.elapsedTime),
            movingTime = rs.getInt(Columns.movingTime),
            // TODO - appears to be giving wrong values
            startDate = rs.getTimestamp(Columns.startDate).toLocalDateTime()
        )

        private fun getLatLng(rs: ResultSet, latColumn: String, lngColumn: String) : List<Double> {
            val lat = rs.getDouble(latColumn)
            val latNull = rs.wasNull()
            val lng = rs.getDouble(lngColumn)
            val lngNull = rs.wasNull()
            return if (latNull || lngNull) {
                emptyList()
            } else {
                listOf(lat, lng)
            }
        }
    }

}

fun ResultSet.getDoubleOrNull(column: String): Double? {
    val dbl = getDouble(column)
    return if (wasNull()) null else dbl
}