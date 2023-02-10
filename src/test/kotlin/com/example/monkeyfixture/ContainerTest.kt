package com.example.monkeyfixture

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.navercorp.fixturemonkey.LabMonkey
import com.navercorp.fixturemonkey.api.generator.ArbitraryContainerInfo
import com.navercorp.fixturemonkey.api.generator.DefaultNullInjectGenerator
import com.navercorp.fixturemonkey.jackson.plugin.JacksonPlugin
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.RepeatedTest

internal class ContainerTest {

    @RepeatedTest(1000)
    fun test() {
        val minSize = 3
        val maxSize = 10
        val sut: LabMonkey = LabMonkey.labMonkeyBuilder()
            .plugin(KotlinPlugin())
            .plugin(
                JacksonPlugin(
                    jacksonObjectMapper()
                        .registerModule(JavaTimeModule())
                        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                        .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
                )
            )
            .defaultArbitraryContainerInfo(ArbitraryContainerInfo(minSize, maxSize, false))
            .defaultNullInjectGenerator { DefaultNullInjectGenerator.NOT_NULL_INJECT }
            .build()

        val giveMeOne = sut.giveMeOne<TestDataClassV2>()
        Assertions.assertThat(giveMeOne.container.size >= minSize).isTrue
        Assertions.assertThat(giveMeOne.container.size <= maxSize).isTrue
        Assertions.assertThat(giveMeOne.container2.size >= minSize).isTrue
        Assertions.assertThat(giveMeOne.container2.size <= maxSize).isTrue
    }

    @RepeatedTest(1000)
    fun testWithJakartaValidationPlugin() {
        val minSize = 3
        val maxSize = 10
        val sut: LabMonkey = LabMonkey.labMonkeyBuilder()
            .plugin(KotlinPlugin())
            .plugin(
                JacksonPlugin(
                    jacksonObjectMapper()
                        .registerModule(JavaTimeModule())
                        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                        .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
                )
            )
            .plugin(JakartaValidationPlugin())
            .defaultArbitraryContainerInfo(ArbitraryContainerInfo(minSize, maxSize, false))
            .defaultNullInjectGenerator { DefaultNullInjectGenerator.NOT_NULL_INJECT }
            .build()

        val giveMeOne = sut.giveMeOne<TestDataClassV2>()
        Assertions.assertThat(giveMeOne.container.size >= minSize).isTrue
        Assertions.assertThat(giveMeOne.container.size <= maxSize).isTrue
        Assertions.assertThat(giveMeOne.container2.size >= minSize).isTrue
        Assertions.assertThat(giveMeOne.container2.size <= maxSize).isTrue
    }
}
