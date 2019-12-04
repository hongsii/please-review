package please.review.client.github.model

import com.fasterxml.jackson.annotation.JsonCreator

enum class PullRequestState {

    OPEN, CLOSED;

    companion object {
        @JvmStatic
        @JsonCreator
        fun of(state: String) = valueOf(state.toUpperCase())
    }
}