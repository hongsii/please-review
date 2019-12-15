package please.review.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import please.review.core.channel.domain.Channel
import please.review.core.channel.domain.GithubRepo
import please.review.core.channel.repository.ChannelRepository
import please.review.service.dto.ChannelGithubRepo

@Service
class ChannelService(
    private val channelRepository: ChannelRepository
) {

    @Transactional
    fun addGithubRepo(channelGithubRepo: ChannelGithubRepo): Channel =
        channelGithubRepo
            .run {
                channelRepository.findByTypeAndExternalId(channelType, externalId)
                    ?: Channel(type = channelType, externalId = externalId)
            }
            .apply {
                val githubRepo = GithubRepo.from(channelGithubRepo.fullNameOfRepo, this)
                addRepo(githubRepo)
                channelRepository.save(this)
            }
}
