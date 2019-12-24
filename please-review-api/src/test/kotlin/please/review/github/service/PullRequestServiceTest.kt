package please.review.github.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import please.review.client.github.GithubClient
import please.review.client.github.model.PullRequest
import please.review.client.github.model.PullRequestState
import please.review.client.github.model.User
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
internal class PullRequestServiceTest {

    @InjectMocks
    private lateinit var pullRequestService: PullRequestService

    @Mock
    private lateinit var githubClient: GithubClient

    @Test
    fun `사용자에게 리뷰 요청된 PR을 조회한다`() {
        val owner = "red"
        val name = "please-review"
        val userName = "red"
        val expected = PullRequest(
            requestedReviewers = listOf(
                User(login = "red", id = 1, type = "User"),
                User(login = "bean", id = 2, type = "User")
            ),
            url = "https://octocoders.github.io/api/v3/repos/Codertocat/Hello-World/pulls/2",
            id = 1,
            state = PullRequestState.OPEN,
            title = "PR1",
            body = "머지해주세요",
            createdAt = LocalDateTime.now(), updatedAt = LocalDateTime.now()
        )
        val unexpected = PullRequest(
            requestedReviewers = listOf(
                User(login = "bean", id = 2, type = "User")
            ),
            url = "https://octocoders.github.io/api/v3/repos/Codertocat/Hello-World/pulls/2",
            id = 1,
            state = PullRequestState.OPEN,
            title = "PR2",
            body = "머지해주세요",
            createdAt = LocalDateTime.now(), updatedAt = LocalDateTime.now()
        )
        given(githubClient.getPullRequests(owner, name, "open"))
            .willReturn(listOf(expected, unexpected))

        val actual = pullRequestService.getRequestedReviewToUser(owner, name, userName)

        assertThat(actual)
            .hasSize(1)
            .isEqualTo(listOf(expected))
    }
}