package please.review.channel.service.dto

import please.review.client.github.model.PullRequest
import please.review.core.channel.domain.ChannelType

data class ChannelUser(
    val channelType: ChannelType,
    val externalId: String,
    val githubUserName: String
)

data class Review(
    val channelUser: ChannelUser,
    val repoPullRequests: List<RepoPullRequest>
)

data class RepoPullRequest(
    val owner: String,
    val name: String,
    val pullRequests: List<PullRequest>
)