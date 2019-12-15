package please.review.core.channel.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import please.review.core.channel.domain.Channel
import please.review.core.channel.domain.ChannelType
import please.review.core.channel.domain.GithubRepo

@DataJpaTest
@ExtendWith(SpringExtension::class)
internal class ChannelRepositoryTest(
    @Autowired
    val channelRepository: ChannelRepository
) {

    @Test
    fun insert() {
        val new = Channel(
            type = ChannelType.TALK,
            externalId = "1"
        )

        val saved = channelRepository.save(new)

        assertThat(channelRepository.findAll())
            .hasSize(1)
            .first().hasNoNullFieldsOrProperties().isEqualTo(saved)
    }

    @Test
    fun insertWithRelation() {
        val new = Channel(
            type = ChannelType.TALK,
            externalId = "1"
        )
        new.addRepo(GithubRepo(owner = "dog", name = "cat", channel = new))

        val saved = channelRepository.save(new)

        assertThat(channelRepository.findAll()).hasSize(1)
            .first()
            .hasNoNullFieldsOrProperties().isEqualTo(saved)
    }

    @Test
    fun update() {
        val new = Channel(
            type = ChannelType.TALK,
            externalId = "1"
        )
        val saved = channelRepository.save(new)

        val updated = channelRepository.save(saved.copy(externalId = "test id"))

        assertThat(channelRepository.findAll())
            .hasSize(1)
            .first().isEqualTo(updated)
    }

    @Test
    fun delete() {
        val new = Channel(
            type = ChannelType.TALK,
            externalId = "1"
        )
        val saved = channelRepository.save(new)

        channelRepository.delete(saved)

        assertThat(channelRepository.findAll()).isEmpty()
    }
}