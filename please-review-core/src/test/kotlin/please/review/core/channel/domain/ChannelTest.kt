package please.review.core.channel.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import please.review.core.exception.DuplicatedGithubRepoException

internal class ChannelTest {

    @Test
    fun `Github 저장소를 추가한다`() {
        val channel = Channel(
            type = ChannelType.TALK,
            externalId = "1"
        )
        val githubRepo = GithubRepo(fullName = GithubRepoFullName("owner", "name"), channel = channel)

        channel.addRepo(githubRepo)

        assertThat(channel.getGithubRepos())
            .hasSize(1)
            .first().isEqualTo(githubRepo)
        assertThat(githubRepo.channel).isEqualTo(channel)
    }

    @Test
    fun `중복된 저장소는 추가할 수 없다`() {
        val channel = Channel(
            type = ChannelType.TALK,
            externalId = "1"
        )
        val owner = "owner"
        val name = "name"
        channel.addRepo(GithubRepo(id = 1, fullName = GithubRepoFullName(owner, name), channel = channel))

        assertThatThrownBy {
            channel.addRepo(GithubRepo(fullName = GithubRepoFullName(owner, name), channel = channel))
        }
            .isExactlyInstanceOf(DuplicatedGithubRepoException::class.java)
    }
}