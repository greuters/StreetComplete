package de.westnordost.streetcompletegpx.data.upload

interface UploadProgressListener {
    fun onStarted() {}
    fun onProgress(success: Boolean) {}
    fun onError(e: Exception) {}
    fun onFinished() {}
}
