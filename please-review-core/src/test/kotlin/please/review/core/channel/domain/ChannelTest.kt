package please.review.core.channel.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import please.review.core.channel.exception.AlreadyAddedGithubRepo

internal class ChannelTest {

    @Test
    fun `Github 저장소를 추가한다`() {
        val channel = Channel(type = ChannelType.TALK, externalId = "1")
        val githubRepo = GithubRepo(owner = "cat", name = "dog")

        channel.addGithubRepo(githubRepo)

        assertThat(channel.getGithubRepos())
            .hasSize(1)
            .first().isEqualTo(githubRepo)
        assertThat(githubRepo.channel).isEqualTo(channel)
    }

    @Test
    fun `중복된 Github 저장소는 저장할 수 없다`() {
        val githubRepo = GithubRepo(owner = "cat", name = "dog")
        val channel = Channel(type = ChannelType.TALK, externalId = "1", githubRepos = listOf(githubRepo))

        assertThatThrownBy { channel.addGithubRepo(githubRepo) }
            .isExactlyInstanceOf(AlreadyAddedGithubRepo::class.java)
    }
}