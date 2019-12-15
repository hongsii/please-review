package please.review.service.dto

import please.review.core.channel.domain.ChannelType

data class ChannelGithubRepo(
    val channelType: ChannelType,
    val externalId: String,
    val fullNameOfRepo: String
)