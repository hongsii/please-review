package please.review.client.github.model

import java.time.LocalDateTime

data class Repository(
    val id: Long,
    val url: String,
    val owner: User,
    val name: String,
    val fullName: String,
    val private: Boolean,
    val description: String,
    val fork: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val pushedAt: LocalDateTime
)