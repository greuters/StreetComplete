package de.westnordost.streetcompletegpx.data.osm.edits.upload

import de.westnordost.streetcompletegpx.data.osm.edits.ElementEditAction
import de.westnordost.streetcompletegpx.data.osm.edits.ElementEditsController
import de.westnordost.streetcompletegpx.data.osm.mapdata.ElementKey
import de.westnordost.streetcompletegpx.data.osm.mapdata.ElementType
import de.westnordost.streetcompletegpx.data.osm.mapdata.MapDataApi
import de.westnordost.streetcompletegpx.data.osm.mapdata.MapDataController
import de.westnordost.streetcompletegpx.data.osm.mapdata.MapDataUpdates
import de.westnordost.streetcompletegpx.data.osmnotes.edits.NoteEditsController
import de.westnordost.streetcompletegpx.data.upload.ConflictException
import de.westnordost.streetcompletegpx.data.upload.OnUploadedChangeListener
import de.westnordost.streetcompletegpx.data.user.statistics.StatisticsController
import de.westnordost.streetcompletegpx.testutils.any
import de.westnordost.streetcompletegpx.testutils.edit
import de.westnordost.streetcompletegpx.testutils.eq
import de.westnordost.streetcompletegpx.testutils.mock
import de.westnordost.streetcompletegpx.testutils.node
import de.westnordost.streetcompletegpx.testutils.on
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoInteractions
import kotlin.test.BeforeTest
import kotlin.test.Test

class ElementEditsUploaderTest {

    private lateinit var elementEditsController: ElementEditsController
    private lateinit var mapDataController: MapDataController
    private lateinit var noteEditsController: NoteEditsController
    private lateinit var singleUploader: ElementEditUploader
    private lateinit var mapDataApi: MapDataApi
    private lateinit var statisticsController: StatisticsController

    private lateinit var uploader: ElementEditsUploader
    private lateinit var listener: OnUploadedChangeListener

    @BeforeTest fun setUp() {
        elementEditsController = mock()
        mapDataController = mock()
        noteEditsController = mock()

        singleUploader = mock()
        mapDataApi = mock()
        statisticsController = mock()

        listener = mock()

        uploader = ElementEditsUploader(elementEditsController, noteEditsController, mapDataController, singleUploader, mapDataApi, statisticsController)
        uploader.uploadedChangeListener = listener
    }

    @Test fun `cancel upload works`() = runBlocking {
        val job = launch { uploader.upload() }
        job.cancelAndJoin()
        verifyNoInteractions(elementEditsController, mapDataController, singleUploader, statisticsController)
    }

    @Test fun `upload works`() = runBlocking {
        val edit = edit()
        val updates = mock<MapDataUpdates>()

        on(elementEditsController.getOldestUnsynced()).thenReturn(edit).thenReturn(null)
        on(singleUploader.upload(any(), any())).thenReturn(updates)

        uploader.upload()

        verify(singleUploader).upload(eq(edit), any())
        verify(listener).onUploaded(any(), any())
        verify(elementEditsController).markSynced(edit, updates)
        verify(noteEditsController).updateElementIds(any())
        verify(mapDataController).updateAll(updates)

        verify(statisticsController).addOne(any(), any())
    }

    @Test fun `upload catches conflict exception`() = runBlocking {
        // edit modifies node 1 and way 1
        val node1 = node()
        val action: ElementEditAction = mock()
        on(action.elementKeys).thenReturn(listOf(
            ElementKey(ElementType.NODE, 1),
            ElementKey(ElementType.WAY, 1),
        ))
        val edit = edit(action = action)

        // ...but way 1 is gone
        on(mapDataApi.getNode(1)).thenReturn(node1)
        on(mapDataApi.getWayComplete(1)).thenReturn(null)

        // the edit is the first in the upload queue and the uploader throws a conflict exception
        on(elementEditsController.getOldestUnsynced()).thenReturn(edit).thenReturn(null)
        on(singleUploader.upload(any(), any())).thenThrow(ConflictException())

        uploader.upload()

        verify(singleUploader).upload(eq(edit), any())
        verify(listener).onDiscarded(any(), any())

        verify(elementEditsController).markSyncFailed(edit)
        verifyNoInteractions(statisticsController)

        verify(mapDataController).updateAll(eq(MapDataUpdates(
            updated = listOf(node1),
            deleted = listOf(ElementKey(ElementType.WAY, 1))
        )))
    }
}
