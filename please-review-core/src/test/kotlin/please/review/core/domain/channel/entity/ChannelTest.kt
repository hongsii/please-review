package please.review.core.domain.channel.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ChannelTest {

    @Test
    fun `Github 저장소를 추가한다`() {
        val channel = Channel(type = ChannelType.TALK, externalId = "1")
        val githubRepo = GithubRepo(owner = "cat", name = "dog")

        channel.addRepo(githubRepo)

        assertThat(channel.githubRepos)
            .hasSize(1)
            .first().isEqualTo(githubRepo)
        assertThat(githubRepo.channel).isEqualTo(channel)
    }
}