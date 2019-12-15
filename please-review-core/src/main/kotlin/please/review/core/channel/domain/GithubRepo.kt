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
    @JoinColumn(nullable = false, foreignKey = ForeignKey(name = "fk_channel_id"))
    val channel: Channel
) : BaseEntity() {

    companion object {

        private const val FULL_NAME_SEPARATOR = "/"

        fun from(fullName: String, channel: Channel): GithubRepo =
            fullName.split(FULL_NAME_SEPARATOR)
                .filter(String::isNotBlank)
                .takeIf { it.size == 2 }
                ?.let { GithubRepo(owner = it[0], name = it[1], channel = channel) }
                ?: throw IllegalArgumentException("잘못된 저장소입니다. 유효한 형식 : 소유자${FULL_NAME_SEPARATOR}저장소명")
    }
}
