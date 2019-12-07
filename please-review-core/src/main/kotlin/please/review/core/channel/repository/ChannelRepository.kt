package please.review.core.channel.repository

import org.springframework.data.jpa.repository.JpaRepository
import please.review.core.channel.domain.Channel
import please.review.core.channel.domain.ChannelType

interface ChannelRepository : JpaRepository<Channel, Long> {

    fun findByTypeAndExternalId(type: ChannelType, externalId: String): Channel?
}
