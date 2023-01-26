package com.example.monkeyfixture

import com.navercorp.fixturemonkey.LabMonkey
import com.navercorp.fixturemonkey.api.generator.DefaultNullInjectGenerator
import com.navercorp.fixturemonkey.jackson.plugin.JacksonPlugin
import com.navercorp.fixturemonkey.javax.validation.plugin.JavaxValidationPlugin
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(controllers = [TestController::class])
@AutoConfigureMockMvc
class TestControllerTest {

    @MockkBean
    private lateinit var testService: TestService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun getSavedQuestionnaire() {
        val uri = "/test"
        val sut: LabMonkey = LabMonkey.labMonkeyBuilder()
            .defaultNullInjectGenerator { DefaultNullInjectGenerator.NOT_NULL_INJECT }
            .plugin(KotlinPlugin())
            .plugin(JacksonPlugin())
            .build()
        val fixture = sut.giveMeOne<TestDataClass>()
        println(fixture.toString())
        every { testService.test() } returns fixture

        mockMvc
            .perform(MockMvcRequestBuilders.get(uri))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(fixture.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(fixture.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name2").value(fixture.name2))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name3").value(fixture.name3))
            .andDo(MockMvcResultHandlers.print())
    }

    // Fails intermittently
    @Test
    fun getSavedQuestionnaireWithJavaxPlugin() {
        val uri = "/test"
        val sut: LabMonkey = LabMonkey.labMonkeyBuilder()
            .defaultNullInjectGenerator { DefaultNullInjectGenerator.NOT_NULL_INJECT }
            .plugin(KotlinPlugin())
            .plugin(JacksonPlugin())
            .plugin(JavaxValidationPlugin())
            .build()
        val fixture = sut.giveMeOne<TestDataClass>()
        println(fixture.toString())
        every { testService.test() } returns fixture

        mockMvc
            .perform(MockMvcRequestBuilders.get(uri))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(fixture.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(fixture.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name2").value(fixture.name2))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name3").value(fixture.name3))
            .andDo(MockMvcResultHandlers.print())
    }
}
