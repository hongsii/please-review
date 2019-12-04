package please.review.client.github.model

import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import please.review.client.github.extensions.jackson.objectMapper

internal class UserTest {

    @Test
    fun `JSON으로 객체를 생성한다`() {
        val json = """
        {
           "login":"octocat",
           "id":1,
           "node_id":"MDQ6VXNlcjE=",
           "avatar_url":"https://github.com/images/error/octocat_happy.gif",
           "gravatar_id":"",
           "url":"https://api.github.com/users/octocat",
           "html_url":"https://github.com/octocat",
           "followers_url":"https://api.github.com/users/octocat/followers",
           "following_url":"https://api.github.com/users/octocat/following{/other_user}",
           "gists_url":"https://api.github.com/users/octocat/gists{/gist_id}",
           "starred_url":"https://api.github.com/users/octocat/starred{/owner}{/repo}",
           "subscriptions_url":"https://api.github.com/users/octocat/subscriptions",
           "organizations_url":"https://api.github.com/users/octocat/orgs",
           "repos_url":"https://api.github.com/users/octocat/repos",
           "events_url":"https://api.github.com/users/octocat/events{/privacy}",
           "received_events_url":"https://api.github.com/users/octocat/received_events",
           "type":"User",
           "site_admin":false,
           "name":"monalisa octocat",
           "company":"GitHub",
           "blog":"https://github.com/blog",
           "location":"San Francisco",
           "email":"octocat@github.com",
           "hireable":false,
           "bio":"There once was...",
           "public_repos":2,
           "public_gists":1,
           "followers":20,
           "following":0,
           "created_at":"2008-01-14T04:33:35Z",
           "updated_at":"2008-01-14T04:33:35Z"
        }
        """

        val actual = objectMapper().readValue<User>(json)

        assertThat(actual).isNotNull
        assertThat(actual).hasNoNullFieldsOrProperties().isNotNull
    }
}