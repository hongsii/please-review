package please.review.core.channel.domain

import please.review.core.exception.InvalidGithubRepoFullNameException
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class GithubRepoFullName(
    @Column(nullable = false)
    val owner: String,

    @Column(nullable = false)
    val name: String
) {

    companion object {

        private const val FULL_NAME_SEPARATOR = "/"

        fun from(fullName: String) =
            fullName.split(FULL_NAME_SEPARATOR)
                .filter(String::isNotBlank)
                .takeIf { it.size == 2 }
                ?.let { GithubRepoFullName(it[0], it[1]) }
                ?: throw InvalidGithubRepoFullNameException(FULL_NAME_SEPARATOR)
    }
}
