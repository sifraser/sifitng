package com.sifraser.sifit.service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration
import org.springframework.boot.runApplication


@SpringBootApplication(
	exclude = [GroovyTemplateAutoConfiguration::class]
)
class SifitApplication

fun main(args: Array<String>) {
    runApplication<SifitApplication>(*args)
}
