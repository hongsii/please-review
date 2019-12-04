package please.review.client.github.extensions.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import java.time.LocalDateTime
import please.review.common.extensions.objectMapper as defaultObjectMapper

/**
 * Custom object mapper for github api
 */
fun objectMapper(): ObjectMapper = defaultObjectMapper()
    .apply {
        registerModule(SimpleModule()
            .apply {
                addDeserializer(LocalDateTime::class.java, DefaultLocalDateTimeDeserializer())
            })
    }
