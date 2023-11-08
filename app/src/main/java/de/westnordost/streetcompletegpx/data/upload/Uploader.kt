package de.westnordost.streetcompletegpx.data.upload

import android.util.Log
import de.westnordost.streetcompletegpx.ApplicationConstants
import de.westnordost.streetcompletegpx.data.download.tiles.DownloadedTilesController
import de.westnordost.streetcompletegpx.data.download.tiles.enclosingTilePos
import de.westnordost.streetcompletegpx.data.osm.edits.upload.ElementEditsUploader
import de.westnordost.streetcompletegpx.data.osm.mapdata.LatLon
import de.westnordost.streetcompletegpx.data.osmnotes.edits.NoteEditsUploader
import de.westnordost.streetcompletegpx.data.user.AuthorizationException
import de.westnordost.streetcompletegpx.data.user.UserLoginStatusSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

class Uploader(
    private val noteEditsUploader: NoteEditsUploader,
    private val elementEditsUploader: ElementEditsUploader,
    private val downloadedTilesController: DownloadedTilesController,
    private val userLoginStatusSource: UserLoginStatusSource,
    private val versionIsBannedChecker: VersionIsBannedChecker,
    private val mutex: Mutex
) {
    var uploadedChangeListener: OnUploadedChangeListener? = null

    private val bannedInfo by lazy { versionIsBannedChecker.get() }

    private val uploadedChangeRelay = object : OnUploadedChangeListener {
        override fun onUploaded(questType: String, at: LatLon) {
            uploadedChangeListener?.onUploaded(questType, at)
        }

        override fun onDiscarded(questType: String, at: LatLon) {
            invalidateArea(at)
            uploadedChangeListener?.onDiscarded(questType, at)
        }
    }

    init {
        noteEditsUploader.uploadedChangeListener = uploadedChangeRelay
        elementEditsUploader.uploadedChangeListener = uploadedChangeRelay
    }

    suspend fun upload() {
        val banned = withContext(Dispatchers.IO) { bannedInfo }
        if (banned is IsBanned) {
            throw VersionBannedException(banned.reason)
        }

        // let's fail early in case of no authorization
        if (!userLoginStatusSource.isLoggedIn) {
            throw AuthorizationException("User is not authorized")
        }

        Log.i(TAG, "Starting upload")

        mutex.withLock {
            // element edit and note edit uploader must run in sequence because the notes may need
            // to be updated if the element edit uploader creates new elements to which notes refer
            elementEditsUploader.upload()
            noteEditsUploader.upload()
        }

        Log.i(TAG, "Finished upload")
    }

    private fun invalidateArea(pos: LatLon) {
        // called after a conflict. If there is a conflict, the user is not the only one in that
        // area, so best invalidate all downloaded quests here and redownload on next occasion
        val tile = pos.enclosingTilePos(ApplicationConstants.DOWNLOAD_TILE_ZOOM)
        downloadedTilesController.invalidate(tile)
    }

    companion object {
        private const val TAG = "Upload"
    }
}
