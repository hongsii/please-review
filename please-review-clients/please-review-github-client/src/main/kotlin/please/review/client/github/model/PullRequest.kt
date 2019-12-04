package please.review.client.github.model

import java.time.LocalDateTime

data class PullRequest(
    val url: String,
    val id: Int,
    val state: PullRequestState,
    val title: String,
    val body: String,
    val assignees: List<User> = emptyList(),
    val requestedReviewers: List<User> = emptyList(),
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
