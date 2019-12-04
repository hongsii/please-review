package please.review.client.github

import org.springframework.beans.factory.InitializingBean
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import please.review.client.github.model.PullRequest
import please.review.common.extensions.logger

class GithubClient(
    private val baseUrl: String,
    private val accessToken: String,
    private val restTemplate: RestTemplate
) : InitializingBean {

    fun getPullRequests(owner: String, repo: String, state: String? = "all"): List<PullRequest> {
        val httpEntity = HttpHeaders()
            .apply { add("Authorization", "Bearer $accessToken") }
            .let { HttpEntity<Any>(it) }

        val response = restTemplate.exchange<List<PullRequest>>(
            "$baseUrl/api/v3/repos/$owner/$repo/pulls?state=$state",
            HttpMethod.GET,
            httpEntity
        )
        return response.body ?: emptyList()
    }

    override fun afterPropertiesSet() {
        log.info("Initializing github client with access token : $accessToken")
    }

    companion object {
        val log = logger()
    }
}
