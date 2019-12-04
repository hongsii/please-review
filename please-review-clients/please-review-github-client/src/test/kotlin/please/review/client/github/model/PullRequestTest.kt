package please.review.client.github.model

import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import please.review.client.github.extensions.jackson.objectMapper

internal class PullRequestTest {

    @Test
    fun `JSON으로 객체를 생성한다`() {
        val json = """
        {
           "url":"https://octocoders.github.io/api/v3/repos/Codertocat/Hello-World/pulls/2",
           "id":2,
           "node_id":"MDExOlB1bGxSZXF1ZXN0Mg==",
           "html_url":"https://octocoders.github.io/Codertocat/Hello-World/pull/2",
           "diff_url":"https://octocoders.github.io/Codertocat/Hello-World/pull/2.diff",
           "patch_url":"https://octocoders.github.io/Codertocat/Hello-World/pull/2.patch",
           "issue_url":"https://octocoders.github.io/api/v3/repos/Codertocat/Hello-World/issues/2",
           "number":2,
           "state":"open",
           "locked":false,
           "title":"Update the README with new information.",
           "user":{
              "login":"Codertocat",
              "id":4,
              "node_id":"MDQ6VXNlcjQ=",
              "avatar_url":"https://octocoders.github.io/avatars/u/4?",
              "gravatar_id":"",
              "url":"https://octocoders.github.io/api/v3/users/Codertocat",
              "html_url":"https://octocoders.github.io/Codertocat",
              "followers_url":"https://octocoders.github.io/api/v3/users/Codertocat/followers",
              "following_url":"https://octocoders.github.io/api/v3/users/Codertocat/following{/other_user}",
              "gists_url":"https://octocoders.github.io/api/v3/users/Codertocat/gists{/gist_id}",
              "starred_url":"https://octocoders.github.io/api/v3/users/Codertocat/starred{/owner}{/repo}",
              "subscriptions_url":"https://octocoders.github.io/api/v3/users/Codertocat/subscriptions",
              "organizations_url":"https://octocoders.github.io/api/v3/users/Codertocat/orgs",
              "repos_url":"https://octocoders.github.io/api/v3/users/Codertocat/repos",
              "events_url":"https://octocoders.github.io/api/v3/users/Codertocat/events{/privacy}",
              "received_events_url":"https://octocoders.github.io/api/v3/users/Codertocat/received_events",
              "type":"User",
              "site_admin":false
           },
           "body":"This is a pretty simple change that we need to pull into master.",
           "assignees": [
              {
                 "login":"other_user",
                 "id":1,
                 "node_id":"MDQ6VXNlcjE=",
                 "avatar_url":"https://github.com/images/error/other_user_happy.gif",
                 "gravatar_id":"",
                 "url":"https://api.github.com/users/other_user",
                 "html_url":"https://github.com/other_user",
                 "followers_url":"https://api.github.com/users/other_user/followers",
                 "following_url":"https://api.github.com/users/other_user/following{/other_user}",
                 "gists_url":"https://api.github.com/users/other_user/gists{/gist_id}",
                 "starred_url":"https://api.github.com/users/other_user/starred{/owner}{/repo}",
                 "subscriptions_url":"https://api.github.com/users/other_user/subscriptions",
                 "organizations_url":"https://api.github.com/users/other_user/orgs",
                 "repos_url":"https://api.github.com/users/other_user/repos",
                 "events_url":"https://api.github.com/users/other_user/events{/privacy}",
                 "received_events_url":"https://api.github.com/users/other_user/received_events",
                 "type":"User",
                 "site_admin":false
              }
           ], 
           "requested_reviewers":[
              {
                 "login":"other_user",
                 "id":1,
                 "node_id":"MDQ6VXNlcjE=",
                 "avatar_url":"https://github.com/images/error/other_user_happy.gif",
                 "gravatar_id":"",
                 "url":"https://api.github.com/users/other_user",
                 "html_url":"https://github.com/other_user",
                 "followers_url":"https://api.github.com/users/other_user/followers",
                 "following_url":"https://api.github.com/users/other_user/following{/other_user}",
                 "gists_url":"https://api.github.com/users/other_user/gists{/gist_id}",
                 "starred_url":"https://api.github.com/users/other_user/starred{/owner}{/repo}",
                 "subscriptions_url":"https://api.github.com/users/other_user/subscriptions",
                 "organizations_url":"https://api.github.com/users/other_user/orgs",
                 "repos_url":"https://api.github.com/users/other_user/repos",
                 "events_url":"https://api.github.com/users/other_user/events{/privacy}",
                 "received_events_url":"https://api.github.com/users/other_user/received_events",
                 "type":"User",
                 "site_admin":false
              }
           ],
           "created_at":"2019-05-15T19:38:02Z",
           "updated_at":"2019-05-15T19:38:02Z"
        }
        """

        val actual = objectMapper().readValue<PullRequest>(json)

        assertThat(actual).isNotNull
        assertThat(actual).hasNoNullFieldsOrProperties().isNotNull
    }
}