package please.review.client.github

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "github")
data class GithubClientProperties(val baseUrl: String, val accessToken: String)
