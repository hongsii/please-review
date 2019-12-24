package please.review.github.service

import org.springframework.stereotype.Service
import please.review.client.github.GithubClient
import please.review.client.github.model.PullRequest

@Service
class PullRequestService(
    private val githubClient: GithubClient
) {

    fun getRequestedReviewToUser(owner: String, name: String, userName: String): List<PullRequest> =
        githubClient.getPullRequests(owner, name, "open")
            .filter { it.isReviewer(userName) }
}
