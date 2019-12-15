package please.review.core.channel.domain

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
    private val githubRepos: MutableSet<GithubRepo> = mutableSetOf()

    fun addRepo(githubRepo: GithubRepo) {
        githubRepos.add(githubRepo)
    }

    fun getGithubRepos() = githubRepos.toList()
}