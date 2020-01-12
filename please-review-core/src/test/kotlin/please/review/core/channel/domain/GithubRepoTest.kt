package please.review.core.channel.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class GithubRepoTest {

    @Test
    fun `소유자와 저장소명으로 객체를 생성할 수 있다`() {
        val fullName = "red/please-review"
        val channel = Channel(type = ChannelType.TALK, externalId = "1")

        val actual = GithubRepo.from(fullName, channel)

        assertThat(actual).isEqualTo(
            GithubRepo(
                fullName = GithubRepoFullName("red", "please-review"),
                channel = channel
            )
        )
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
        val repo = GithubRepo(fullName = GithubRepoFullName("owner1", "name1"), channel = channel)

        val actual = repo.isSameFullName(GithubRepo(fullName = GithubRepoFullName(owner, name), channel = channel))

        assertThat(actual).isEqualTo(expected)
    }
}