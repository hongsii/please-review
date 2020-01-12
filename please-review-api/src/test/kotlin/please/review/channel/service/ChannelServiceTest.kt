package please.review.channel.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import please.review.channel.service.dto.ChannelGithubRepo
import please.review.core.channel.domain.Channel
import please.review.core.channel.domain.ChannelType
import please.review.core.channel.domain.GithubRepo
import please.review.core.channel.domain.GithubRepoFullName
import please.review.core.channel.repository.ChannelRepository

@ExtendWith(MockitoExtension::class)
internal class ChannelServiceTest {

    @InjectMocks
    private lateinit var channelService: ChannelService

    @Mock
    private lateinit var channelRepository: ChannelRepository

    @Test
    fun `등로된 채널이 있으면 해당 채널에 저장소를 추가한다`() {
        assertToAddGithubRepo(Channel(type = ChannelType.TALK, externalId = "11"))
    }

    @Test
    fun `등록된 채널이 없으면 신규 채널을 생성해 저장소를 추가한다`() {
        assertToAddGithubRepo(null)
    }

    private fun assertToAddGithubRepo(channel: Channel?) {
        val channelGithubRepo = ChannelGithubRepo(
            channelType = channel?.type ?: ChannelType.TALK,
            externalId = channel?.externalId ?: "1",
            fullNameOfRepo = "red/please-review"
        )

        `when`(channelRepository.findByTypeAndExternalId(channelGithubRepo.channelType, channelGithubRepo.externalId))
            .thenReturn(channel)
        val actual = channelService.addGithubRepo(channelGithubRepo)

        assertThat(actual.type).isEqualTo(channelGithubRepo.channelType)
        assertThat(actual.externalId).isEqualTo(channelGithubRepo.externalId)
        assertThat(actual.getGithubRepos())
            .hasSize(1)
            .containsExactly(GithubRepo(fullName = GithubRepoFullName("red", "please-review"), channel = actual))
    }
}
