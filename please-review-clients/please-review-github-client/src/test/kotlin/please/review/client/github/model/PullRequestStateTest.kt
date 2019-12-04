package please.review.client.github.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class PullRequestStateTest {

    @ParameterizedTest
    @CsvSource(
        value = [
            "open | OPEN",
            "closed | CLOSED"
        ],
        delimiter = '|'
    )
    fun `문자열로 상태 파싱`(state: String, expected: PullRequestState) {
        assertThat(PullRequestState.of(state)).isEqualTo(expected)
    }
}