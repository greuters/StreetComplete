package de.westnordost.streetcompletegpx.data.download

class QueryTooBigException @JvmOverloads constructor(
    message: String? = null,
    cause: Throwable? = null
) : RuntimeException(message, cause)
