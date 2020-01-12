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
                it.fullName.run {
                    val requestedPullRequests = pullRequestService.getRequestedReviewToUser(
                        owner, name, channelUser.githubUserName
                    )
                    RepoPullRequest(owner, name, requestedPullRequests)
                }
            }
            .let { Review(channelUser, it) }
    }
}
