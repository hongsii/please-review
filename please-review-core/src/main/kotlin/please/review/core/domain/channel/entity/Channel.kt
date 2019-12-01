package please.review.core.domain.channel.entity

import please.review.core.domain.BaseEntity
import javax.persistence.*

@Entity
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

    @OneToMany(
        mappedBy = "channel", fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL], orphanRemoval = true
    )
    val githubRepos: MutableSet<GithubRepo> = mutableSetOf()

    fun addRepo(githubRepo: GithubRepo) {
        githubRepos.add(githubRepo)
        githubRepo.channel = this
    }
}