package please.review.core.channel.domain

import please.review.core.channel.exception.AlreadyAddedGithubRepo
import please.review.core.common.domain.BaseEntity
import javax.persistence.*

@Entity
// FIXME: 컬럼명 에러 해결
//@Table(
//    uniqueConstraints = [
//        UniqueConstraint(name = "UQ_type_external_id", columnNames = ["type", "externalId"])
//    ]
//)
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
    private val githubRepos: MutableList<GithubRepo> = mutableListOf()

    constructor(type: ChannelType, externalId: String, githubRepos: List<GithubRepo>)
            : this(type = type, externalId = externalId) {
        this.githubRepos.addAll(githubRepos)
    }

    fun addGithubRepo(githubRepo: GithubRepo) {
        if (githubRepos.contains(githubRepo)) {
            throw AlreadyAddedGithubRepo(githubRepo)
        }
        githubRepos.add(githubRepo)
        githubRepo.channel = this
    }

    fun getGithubRepos() = githubRepos.toList()
}