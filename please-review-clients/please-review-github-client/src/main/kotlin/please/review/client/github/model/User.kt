package please.review.client.github.model

data class User(
    val login: String,
    val id: Long,
    val type: String,
    val email: String = "",
    val avatarUrl: String = ""
)