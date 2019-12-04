package please.review.service

import org.springframework.stereotype.Service
import please.review.core.domain.channel.entity.Channel
import please.review.core.domain.channel.entity.ChannelType
import please.review.core.domain.channel.repository.ChannelRepository
import please.review.exception.AlreadyRegisteredChannel

@Service
class ChannelService(
    private val channelRepository: ChannelRepository
) {

    fun register(channelType: ChannelType, externalId: String) {
        channelRepository.findByTypeAndExternalId(channelType, externalId)
            ?.let { throw AlreadyRegisteredChannel() }

        val newChannel = Channel(type = channelType, externalId = externalId)
        channelRepository.save(newChannel)
    }
}
