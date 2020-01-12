package please.review.core.channel.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.EmptySource
import org.junit.jupiter.params.provider.ValueSource
import please.review.core.exception.InvalidGithubRepoFullNameException

internal class GithubRepoTest {

    @Test
    fun `소유자와 저장소명으로 객체를 생성할 수 있다`() {
        val fullName = "red/please-review"
        val channel = Channel(type = ChannelType.TALK, externalId = "1")

        val actual = GithubRepo.from(fullName, channel)

        assertThat(actual).isEqualTo(
            GithubRepo(
                owner = "red",
                name = "please-review",
                channel = channel
            )
        )
    }

    @ParameterizedTest
    @ValueSource(strings = ["red", "/please-review", "red/", "red|please-review", "red/please/review"])
    @EmptySource
    fun `유효하지 않은 형식은 객체를 생성할 수 없다`(invalidRepoName: String) {
        assertThatThrownBy {
            GithubRepo.from(invalidRepoName, Channel(type = ChannelType.TALK, externalId = "1"))
        }
            .isExactlyInstanceOf(InvalidGithubRepoFullNameException::class.java)
    }


    @ParameterizedTest
    @CsvSource(
        "owner1 , name1 , true",
        "owner1 , name2 , false",
        "owner2 , name1 , false",
        "owner2 , name2 , false"
    )
    fun `소유자와 저장소명이 동일하면 같은 저장소다`(owner: String, name: String, expected: Boolean) {
        val channel = Channel(type = ChannelType.TALK, externalId = "1")
        val repo = GithubRepo(owner = "owner1", name = "name1", channel = channel)

        val actual = repo.isEqualTo(GithubRepo(owner = owner, name = name, channel = channel))

        assertThat(actual).isEqualTo(expected)
    }
}