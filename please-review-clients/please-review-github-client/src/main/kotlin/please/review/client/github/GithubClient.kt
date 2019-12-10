package please.review.client.github

import org.springframework.beans.factory.InitializingBean
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import please.review.client.github.model.PullRequest
import please.review.client.github.model.Repository
import please.review.common.extensions.logger

class GithubClient(
    private val baseUrl: String,
    private val accessToken: String,
    private val restTemplate: RestTemplate
) : InitializingBean {

    private lateinit var defaultHttpEntity: HttpEntity<Any>

    fun getRepository(owner: String, name: String, type: String = "all") =
        get<Repository>("/repos/$owner/$name?type=$type")
            .run { body!! }

    fun getPullRequests(owner: String, name: String, state: String = "all") =
        get<List<PullRequest>>("/repos/$owner/$name/pulls?state=$state")
            .run { body ?: emptyList() }

    private inline fun <reified T> get(path: String) =
        restTemplate.exchange<T>("$baseUrl$${appendPrefixSlash(path)}", HttpMethod.GET, defaultHttpEntity)

    private fun appendPrefixSlash(url: String) = if (url.startsWith("/")) url else "/$url"

    override fun afterPropertiesSet() {
        log.info("Initializing github client with properties -> baseUrl: $baseUrl, accessToken : $accessToken")
        defaultHttpEntity = initializeHttpEntityWithAccessToken()
    }

    private fun initializeHttpEntityWithAccessToken() = HttpHeaders()
        .apply { add("Authorization", "Bearer $accessToken") }
        .let { HttpEntity<Any>(it) }

    companion object {
        val log = logger()
    }
}
