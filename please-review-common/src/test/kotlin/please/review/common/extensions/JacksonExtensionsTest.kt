package please.review.common.extensions

import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class JacksonExtensionsTest {

    @Test
    fun `객체를 JSON으로 변경할 때 스네이크 케이스가 적용된다`() {
        val target = SnakeCase(snakeCase = true)

        val actual = objectMapper().writeValueAsString(target)

        assertThat(actual).isEqualTo("""{"snake_case":true}""")
    }

    @Test
    fun `JSON을 객체로 변경할 때 스네이크 케이스가 적용된다`() {
        val json = """{"snake_case":true}"""

        val actual = objectMapper().readValue<SnakeCase>(json)

        assertThat(actual).isEqualTo(SnakeCase(snakeCase = true))
    }

    @Test
    fun `JSON을 객체로 변경 시 필드에 존재하지 않는 값은 무시한다`() {
        val json = """{"field1":"support", "unknown":"nothing"}"""

        val actual = objectMapper().readValue<IgnoreUnknownField>(json)

        assertThat(actual).isEqualTo(IgnoreUnknownField("support", ""))
    }

    data class SnakeCase(val snakeCase: Boolean)
    data class IgnoreUnknownField(val field1: String = "", val field2: String = "")
}
