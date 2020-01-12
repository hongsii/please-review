package please.review.core.channel.domain

import please.review.core.common.domain.BaseEntity
import please.review.core.exception.InvalidGithubRepoFullNameException
import javax.persistence.*

@Entity
data class GithubRepo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val owner: String,

    @Column(nullable = false)
    val name: String,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], optional = false)
    @JoinColumn(nullable = false, foreignKey = ForeignKey(name = "fk_channel_id"))
    val channel: Channel
) : BaseEntity() {

    fun isEqualTo(other: GithubRepo) = this.owner == other.owner && this.name == other.name

    companion object {

        private const val FULL_NAME_SEPARATOR = "/"

        fun from(fullName: String, channel: Channel): GithubRepo =
            fullName.split(FULL_NAME_SEPARATOR)
                .filter(String::isNotBlank)
                .takeIf { it.size == 2 }
                ?.let { GithubRepo(owner = it[0], name = it[1], channel = channel) }
                ?: throw InvalidGithubRepoFullNameException(FULL_NAME_SEPARATOR)
    }
}
