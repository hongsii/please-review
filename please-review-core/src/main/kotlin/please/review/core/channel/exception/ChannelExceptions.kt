package please.review.core.channel.exception

import please.review.core.channel.domain.GithubRepo

class AlreadyAddedGithubRepo(val githubRepo: GithubRepo, message: String = "") : Exception(message) {


    constructor(githubRepo: GithubRepo) : this(githubRepo, "이미 등록된 저장소입니다. ${githubRepo.getFullName()}")
}
