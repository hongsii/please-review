package please.review.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import please.review.core.channel.domain.Channel
import please.review.core.channel.domain.ChannelType
import please.review.core.channel.domain.GithubRepo
import please.review.core.channel.repository.ChannelRepository
import please.review.exception.AlreadyRegisteredChannel

@Service
class ChannelService(
    private val channelRepository: ChannelRepository,
    private val githubRepoValidator: GithubRepoValidator
) {

    fun register(channelType: ChannelType, externalId: String) {
        channelRepository.findByTypeAndExternalId(channelType, externalId)
            ?.let { throw AlreadyRegisteredChannel() }

        val newChannel =
            Channel(type = channelType, externalId = externalId)
        channelRepository.save(newChannel)
    }

    @Transactional
    fun addGitRepo(channelType: ChannelType, externalId: String, fullRepoName: String) {
        val channel = channelRepository.findByTypeAndExternalId(channelType, externalId)
            ?: Channel(type = channelType, externalId = externalId)

        val newGithubRepo = GithubRepo.from(fullRepoName, channel)
        githubRepoValidator.validate(newGithubRepo)
        channel.addGithubRepo(newGithubRepo)
        channelRepository.save(channel)
    }
}
