package please.review.core.channel.domain

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EmptySource
import org.junit.jupiter.params.provider.ValueSource
import please.review.core.exception.InvalidGithubRepoFullNameException

internal class GithubRepoFullNameTest {

    @ParameterizedTest
    @ValueSource(strings = ["red", "/please-review", "red/", "red|please-review", "red/please/review"])
    @EmptySource
    fun `유효하지 않은 형식은 객체를 생성할 수 없다`(invalidRepoName: String) {
        assertThatThrownBy { GithubRepoFullName.from(invalidRepoName) }
            .isExactlyInstanceOf(InvalidGithubRepoFullNameException::class.java)
    }
}