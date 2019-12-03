package please.review.common.extensions

import org.junit.jupiter.api.Test

internal class LoggerExtensionsTest {

    private val log = logger()

    @Test
    fun `로거 선언 확인`() {
        log.info("success logging!")
    }
}