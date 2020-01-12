package please.review.core.channel.domain

import please.review.core.common.domain.BaseEntity
import please.review.core.exception.DuplicatedGithubRepoException
import javax.persistence.*

@Entity
@Table(
    uniqueConstraints = [
        UniqueConstraint(name = "uq_channel_type_external_id", columnNames = ["type", "externalId"])
    ]
)
data class Channel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val type: ChannelType,

    @Column(nullable = false)
    val externalId: String
) : BaseEntity() {

    @OneToMany(mappedBy = "channel", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    private val githubRepos: MutableSet<GithubRepo> = mutableSetOf()

    fun addRepo(githubRepo: GithubRepo) = githubRepos.add(
        githubRepo
            .takeIf { githubRepos.none { it.isEqualTo(githubRepo) } }
            ?: throw DuplicatedGithubRepoException()
    )

    fun getGithubRepos() = githubRepos.toList()
}