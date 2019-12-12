package please.review.core.channel.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EmptySource
import org.junit.jupiter.params.provider.ValueSource

internal class GithubRepoTest {

    @Test
    fun `저장소 풀네임으로 객체 생성`() {
        val fullName = "red/please-review"

        val actual = GithubRepo.from(fullName)

        assertThat(actual).isNotNull
        assertThat(actual.owner).isEqualTo("red")
        assertThat(actual.name).isEqualTo("please-review")
    }

    @ParameterizedTest
    @ValueSource(strings = ["red", "/please-review", "red", "red|please-review"])
    @EmptySource
    fun `잘못된 저장소 풀네임은 객체 생성 실패`(invalidRepoName: String) {
        assertThatThrownBy { GithubRepo.from(invalidRepoName) }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
    }
}