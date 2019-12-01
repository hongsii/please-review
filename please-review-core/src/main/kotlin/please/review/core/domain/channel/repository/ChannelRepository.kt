package please.review.core.domain.channel.repository

import org.springframework.data.jpa.repository.JpaRepository
import please.review.core.domain.channel.entity.Channel

interface ChannelRepository : JpaRepository<Channel, Long>
