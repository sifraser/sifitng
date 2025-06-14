package com.sifraser.sbot.client.feign

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.sifraser.sifit.strava.client.api.AthleteApi
import com.sifraser.sifit.strava.client.api.GearApi
import com.sifraser.sifit.strava.client.api.Strava
import com.sifraser.sifit.strava.client.feign.ActivityAdapter
import com.sifraser.sifit.strava.client.feign.AthleteAdapter
import feign.Feign
import feign.RequestInterceptor
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import feign.slf4j.Slf4jLogger
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

data class StravaConfig(
        val url: String = "https://www.strava.com",
        val clientId: String,
        val clientSecret: String,
        val token: String

) {
    val httpClient = feign.okhttp.OkHttpClient(OkHttpClient.Builder()
            .connectionPool(ConnectionPool(10, 5, TimeUnit.MINUTES))
            .followRedirects(true)
            .build())

    val tokenRequestInterceptor = RequestInterceptor() { template ->
        template.header("Authorization", "Bearer $token")
    }

    val objectMapper = ObjectMapper()
             .registerModule(KotlinModule.Builder().build())
             .registerModule(JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
}

internal fun <T> createFeignClient(clazz: Class<T>, config: StravaConfig) =
        Feign.builder()
                .client(config.httpClient)
                .logger(Slf4jLogger())
                .requestInterceptor(config.tokenRequestInterceptor)
                .encoder(JacksonEncoder(config.objectMapper))
                .decoder(JacksonDecoder(config.objectMapper))
                .target(clazz, config.url)

internal class StravaFeignAdapter(config: StravaConfig) : Strava {
    override val athletes: AthleteApi = AthleteAdapter(config)
    override val gears: GearApi = GearAdapter(config)
    override val activities = ActivityAdapter(config)
}

fun createStravaClient(config: StravaConfig): Strava = StravaFeignAdapter(config)

