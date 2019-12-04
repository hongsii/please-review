package please.review.core.domain.channel.entity

import please.review.core.domain.BaseEntity
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
) : BaseEntity()