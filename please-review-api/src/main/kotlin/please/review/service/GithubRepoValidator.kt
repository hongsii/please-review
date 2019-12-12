package please.review.service

import please.review.core.channel.domain.GithubRepo

interface GithubRepoValidator {

    fun validate(githubRepo: GithubRepo)
}
