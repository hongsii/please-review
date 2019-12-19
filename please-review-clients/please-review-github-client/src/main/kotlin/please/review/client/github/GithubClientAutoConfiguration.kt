package please.review.client.github

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate
import please.review.client.github.extensions.jackson.objectMapper

@Configuration
@EnableConfigurationProperties(GithubClientProperties::class)
@ConditionalOnBean(GithubClientProperties::class)
@ConditionalOnClass(GithubClient::class)
class GithubClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    fun githubRestTemplate(): RestTemplate = RestTemplate()
        .apply {
            messageConverters
                .apply {
                    removeIf { it.javaClass == MappingJackson2HttpMessageConverter::class.java }
                    add(MappingJackson2HttpMessageConverter().apply { objectMapper = objectMapper() })
                }
        }

    @Bean
    @ConditionalOnMissingBean
    fun githubClient(githubClientProperties: GithubClientProperties, githubRestTemplate: RestTemplate): GithubClient =
        GithubClient(githubClientProperties.baseUrl, githubClientProperties.accessToken, githubRestTemplate)
}
