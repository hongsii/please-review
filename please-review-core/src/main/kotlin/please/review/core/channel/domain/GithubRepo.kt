package please.review.core.channel.domain

import please.review.core.common.domain.BaseEntity
import javax.persistence.*

@Entity
data class GithubRepo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Embedded
    val fullName: GithubRepoFullName,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], optional = false)
    @JoinColumn(nullable = false, foreignKey = ForeignKey(name = "fk_channel_id"))
    val channel: Channel
) : BaseEntity() {

    fun isSameFullName(other: GithubRepo) = this.fullName == other.fullName

    companion object {

        fun from(fullName: String, channel: Channel) =
            GithubRepo(fullName = GithubRepoFullName.from(fullName), channel = channel)
    }
}
