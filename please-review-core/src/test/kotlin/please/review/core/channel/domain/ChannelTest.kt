package please.review.core.channel.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ChannelTest {

    @Test
    fun `Github 저장소를 추가한다`() {
        val channel = Channel(
            type = ChannelType.TALK,
            externalId = "1"
        )
        val githubRepo = GithubRepo(owner = "cat", name = "dog", channel = channel)

        channel.addRepo(githubRepo)

        assertThat(channel.getGithubRepos())
            .hasSize(1)
            .first().isEqualTo(githubRepo)
        assertThat(githubRepo.channel).isEqualTo(channel)
    }
}