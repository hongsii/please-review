package please.review.channel.service

import org.springframework.stereotype.Service
import please.review.channel.service.dto.ChannelUser
import please.review.channel.service.dto.RepoPullRequest
import please.review.channel.service.dto.Review
import please.review.github.service.PullRequestService

@Service
class ChannelPullRequestService(
    private val channelService: ChannelService,
    private val pullRequestService: PullRequestService
) {

    fun getReview(channelUser: ChannelUser): Review {
        val channel = channelUser.run { channelService.getChannel(channelType, externalId) }

        return channel.getGithubRepos()
            .map {
                val requestedPullRequests = pullRequestService.getRequestedReviewToUser(
                    it.owner, it.name, channelUser.githubUserName
                )
                RepoPullRequest(it.owner, it.name, requestedPullRequests)
            }
            .let { Review(channelUser, it) }
    }
}
