package please.review.common.extensions

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

/**
 * Custom object mapper
 */
fun objectMapper(): ObjectMapper = ObjectMapper()
    .apply {
        this.propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }
    .registerKotlinModule()
