package de.westnordost.streetcompletegpx.data.download

class ConnectionException @JvmOverloads constructor(
    message: String? = null,
    cause: Throwable? = null
) : RuntimeException(message, cause)
