package please.review.core.domain.channel.repository

import org.springframework.data.jpa.repository.JpaRepository
import please.review.core.domain.channel.entity.Channel
import please.review.core.domain.channel.entity.ChannelType

interface ChannelRepository : JpaRepository<Channel, Long> {

    fun findByTypeAndExternalId(type: ChannelType, externalId: String): Channel?
}
