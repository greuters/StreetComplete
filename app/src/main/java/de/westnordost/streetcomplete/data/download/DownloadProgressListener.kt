package de.westnordost.streetcompletegpx.data.download

interface DownloadProgressListener {
    fun onStarted() {}
    fun onError(e: Exception) {}
    fun onFinished() {}
    fun onSuccess() {}
}
