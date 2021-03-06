package please.review.common.extensions

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Logger declaration
 */
inline fun <reified R : Any> R.logger(): Logger =
    LoggerFactory.getLogger(this::class.java.name.substringBefore("\$Companion"))
