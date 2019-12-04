package please.review.client.github.extensions.jackson

import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class DefaultLocalDateTimeDeserializerTest {

    @Test
    fun `문자를 날짜 클래스로 파싱`() {
        val objectMapper = objectMapper()

        val actual: LocalDateTime = objectMapper.readValue(""""2019-12-04T19:38:02Z"""")

        assertThat(actual).isEqualTo(LocalDateTime.of(2019, 12, 4, 19, 38, 2))
    }
}