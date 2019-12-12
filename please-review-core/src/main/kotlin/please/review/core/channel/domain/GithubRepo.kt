package please.review.core.channel.domain

import please.review.core.common.domain.BaseEntity
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
    @JoinColumn(nullable = false, foreignKey = ForeignKey(name = "FK_channel_id"))
    var channel: Channel? = null
) : BaseEntity() {

    fun getFullName() = owner + FULL_NAME_SEPARATOR + name

    companion object {

        const val FULL_NAME_SEPARATOR = "/"

        fun from(fullName: String, channel: Channel): GithubRepo = fullName.split(FULL_NAME_SEPARATOR)
            .filter { it.isNotBlank() }
            .takeIf { it.size == 2 }
            ?.let { GithubRepo(owner = it[0], name = it[1], channel = channel) }
            ?: throw IllegalArgumentException("잘못된 저장소명입니다. [형식 : 소유자/저장소명]")
    }
}