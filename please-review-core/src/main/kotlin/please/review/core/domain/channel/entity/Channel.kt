package please.review.core.domain.channel.entity

import please.review.core.domain.BaseEntity
import javax.persistence.*

@Entity
data class Channel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Enumerated(EnumType.STRING)
    val type: ChannelType,

    val externalId: String = ""
) : BaseEntity()