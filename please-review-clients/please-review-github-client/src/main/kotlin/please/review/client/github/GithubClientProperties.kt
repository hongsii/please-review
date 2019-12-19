package please.review.client.github

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "github")
@ConditionalOnProperty(prefix = "github", name = ["base-url", "access-token"])
data class GithubClientProperties(val baseUrl: String, val accessToken: String)
