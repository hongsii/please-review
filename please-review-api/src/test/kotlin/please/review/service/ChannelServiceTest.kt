package please.review.service

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import please.review.core.domain.channel.entity.Channel
import please.review.core.domain.channel.entity.ChannelType
import please.review.core.domain.channel.repository.ChannelRepository
import please.review.exception.AlreadyRegisteredChannel

@ExtendWith(MockitoExtension::class)
internal class ChannelServiceTest {

    @InjectMocks
    private lateinit var channelService: ChannelService

    @Mock
    private lateinit var channelRepository: ChannelRepository

    @Test
    fun `미등록 채널이면 저장할 수 있다`() {
        val channelType = ChannelType.TALK
        val externalId = "1"

        `when`(channelRepository.findByTypeAndExternalId(channelType, externalId))
            .thenReturn(null)

        channelService.register(channelType, externalId)
    }

    @Test
    fun `이미 등록된 채널이면 저장할 수 없다`() {
        val channelType = ChannelType.TALK
        val externalId = "1"

        `when`(channelRepository.findByTypeAndExternalId(channelType, externalId))
            .thenReturn(Channel(type = channelType, externalId = externalId))

        assertThatThrownBy { channelService.register(channelType, externalId) }
            .isExactlyInstanceOf(AlreadyRegisteredChannel::class.java)
    }
}
