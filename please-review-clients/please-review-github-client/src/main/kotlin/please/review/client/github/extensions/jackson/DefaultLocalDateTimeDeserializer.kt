package please.review.client.github.extensions.jackson

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DefaultLocalDateTimeDeserializer : JsonDeserializer<LocalDateTime>() {

    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): LocalDateTime {
        return LocalDateTime.parse(p?.text ?: "", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))
    }
}