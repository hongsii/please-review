package please.review.channel.service

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import please.review.channel.exception.ChannelNotFoundException
import please.review.channel.service.dto.ChannelUser
import please.review.channel.service.dto.RepoPullRequest
import please.review.channel.service.dto.Review
import please.review.client.github.model.PullRequest
import please.review.client.github.model.PullRequestState
import please.review.client.github.model.User
import please.review.core.channel.domain.Channel
import please.review.core.channel.domain.ChannelType
import please.review.core.channel.domain.GithubRepo
import please.review.github.service.PullRequestService
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
internal class ChannelPullRequestServiceTest {

    @InjectMocks
    private lateinit var channelPullRequestService: ChannelPullRequestService

    @Mock
    private lateinit var channelService: ChannelService

    @Mock
    private lateinit var pullRequestService: PullRequestService

    @Test
    fun `채널에 등록된 저장소에서 사용자가 해야할 리뷰를 조회한다`() {
        val channelUser = ChannelUser(ChannelType.TALK, "1", "bean")

        val channel = Channel(1, channelUser.channelType, channelUser.externalId)
            .apply { addRepo(GithubRepo(1, "red", "please-review", this)) }
        given(channelService.getChannel(channelUser.channelType, channelUser.externalId))
            .willReturn(channel)

        val target = PullRequest(
            requestedReviewers = listOf(
                User(login = "red-bean", id = 1, type = "User"),
                User(login = "bean", id = 2, type = "User")
            ),
            url = "https://octocoders.github.io/api/v3/repos/Codertocat/Hello-World/pulls/2",
            id = 1,
            state = PullRequestState.OPEN,
            title = "PR1",
            body = "머지해주세요",
            createdAt = LocalDateTime.now(), updatedAt = LocalDateTime.now()
        )
        given(pullRequestService.getRequestedReviewToUser("red", "please-review", channelUser.githubUserName))
            .willReturn(listOf(target))

        val actual = channelPullRequestService.getReview(channelUser)

        val expected = Review(channelUser, listOf(RepoPullRequest("red", "please-review", listOf(target))))
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `리뷰 조회 시 등록된 채널이 없으면 에러가 발생한다`() {
        val channelUser = ChannelUser(ChannelType.TALK, "1", "bean")

        given(channelService.getChannel(channelUser.channelType, channelUser.externalId))
            .willThrow(ChannelNotFoundException::class.java)

        assertThatThrownBy { channelPullRequestService.getReview(channelUser) }
    }
}