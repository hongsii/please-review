package please.review.client.github

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.context.annotation.Import
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess
import org.springframework.web.client.RestTemplate
import please.review.client.github.model.PullRequest
import please.review.client.github.model.PullRequestState
import please.review.client.github.model.Repository
import please.review.client.github.model.User
import java.time.LocalDateTime


@RestClientTest(GithubClient::class)
@ExtendWith(SpringExtension::class)
@Import(GithubClientAutoConfiguration::class)
internal class GithubClientTest {

    @Autowired
    private lateinit var githubClient: GithubClient

    @Autowired
    private lateinit var restTemplate: RestTemplate

    private lateinit var mockServer: MockRestServiceServer

    @BeforeEach
    internal fun setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate)
    }

    @Test
    fun `소유자의 저장소를 조회한다`() {
        val owner = "red"
        val name = "please-review"
        val type = "all"
        mockServer.expect(requestTo("https://github.com/api/v3/repos/$owner/$name?type=all"))
            .andRespond(
                withSuccess(ClassPathResource("/json/repo.json", javaClass), MediaType.APPLICATION_JSON)
            )

        val repository = githubClient.getRepository(owner, name, type)

        assertThat(repository).isEqualTo(
            Repository(
                id = 1296269,
                url = "https://api.github.com/repos/octocat/Hello-World",
                owner = User(
                    login = "octocat",
                    id = 1,
                    type = "User",
                    email = "",
                    avatarUrl = "https://github.com/images/error/octocat_happy.gif"
                ),
                name = "Hello-World",
                fullName = "octocat/Hello-World",
                private = false,
                description = "This your first repo!",
                fork = false,
                createdAt = LocalDateTime.of(2011, 1, 26, 19, 1, 12),
                updatedAt = LocalDateTime.of(2011, 1, 26, 19, 14, 43),
                pushedAt = LocalDateTime.of(2011, 1, 26, 19, 6, 43)
            )
        )
    }

    @Test
    fun `소유자의 저장소의 Pull Request를 조회한다`() {
        mockServer.expect(requestTo("https://github.com/api/v3/repos/red/please-review/pulls?state=all"))
            .andRespond(
                withSuccess(ClassPathResource("/json/pull_requests.json", javaClass), MediaType.APPLICATION_JSON)
            )

        val pullRequests = githubClient.getPullRequests("red", "please-review")

        assertThat(pullRequests).hasSize(1)
            .first().isEqualTo(
                PullRequest(
                    url = "https://api.github.com/repos/octocat/Hello-World/pulls/1347",
                    id = 1,
                    state = PullRequestState.OPEN,
                    title = "Amazing new feature",
                    body = "Please pull these awesome changes in!",
                    assignees = listOf(
                        User(
                            login = "octocat",
                            id = 1,
                            type = "User",
                            email = "",
                            avatarUrl = "https://github.com/images/error/octocat_happy.gif"
                        ),
                        User(
                            login = "hubot",
                            id = 1,
                            type = "User",
                            email = "",
                            avatarUrl = "https://github.com/images/error/hubot_happy.gif"
                        )
                    ),
                    requestedReviewers = listOf(
                        User(
                            login = "other_user",
                            id = 1,
                            type = "User",
                            email = "",
                            avatarUrl = "https://github.com/images/error/other_user_happy.gif"
                        )
                    ),
                    createdAt = LocalDateTime.of(2011, 1, 26, 19, 1, 12),
                    updatedAt = LocalDateTime.of(2011, 1, 26, 19, 1, 12)
                )
            )
    }
}
