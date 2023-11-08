package de.westnordost.streetcompletegpx.data.osm.edits.upload

import de.westnordost.streetcompletegpx.data.osm.edits.ElementEdit
import de.westnordost.streetcompletegpx.data.osm.edits.ElementEditAction
import de.westnordost.streetcompletegpx.data.osm.edits.upload.changesets.OpenChangesetsManager
import de.westnordost.streetcompletegpx.data.osm.mapdata.MapDataApi
import de.westnordost.streetcompletegpx.data.osm.mapdata.MapDataChanges
import de.westnordost.streetcompletegpx.data.osm.mapdata.MapDataController
import de.westnordost.streetcompletegpx.data.osm.mapdata.MapDataUpdates
import de.westnordost.streetcompletegpx.data.upload.ConflictException
import de.westnordost.streetcompletegpx.testutils.any
import de.westnordost.streetcompletegpx.testutils.mock
import de.westnordost.streetcompletegpx.testutils.on
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mockito.doThrow
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

class ElementEditUploaderTest {

    private lateinit var changesetManager: OpenChangesetsManager
    private lateinit var mapDataApi: MapDataApi
    private lateinit var mapDataController: MapDataController
    private lateinit var uploader: ElementEditUploader

    @BeforeTest fun setUp() {
        changesetManager = mock()
        mapDataApi = mock()
        mapDataController = mock()

        uploader = ElementEditUploader(changesetManager, mapDataApi, mapDataController)
    }

    @Test
    fun `passes on conflict exception`() {
        val edit: ElementEdit = mock()
        val action: ElementEditAction = mock()
        on(edit.action).thenReturn(action)
        on(action.createUpdates(any(), any())).thenReturn(MapDataChanges())
        on(mapDataApi.uploadChanges(anyLong(), any(), any())).thenThrow(ConflictException())

        assertFailsWith<ConflictException> {
            uploader.upload(edit, { mock() })
        }
    }

    @Test
    fun `passes on element conflict exception`() {
        val edit: ElementEdit = mock()
        val action: ElementEditAction = mock()
        on(edit.action).thenReturn(action)
        on(action.createUpdates(any(), any())).thenReturn(MapDataChanges())

        on(changesetManager.getOrCreateChangeset(any(), any())).thenReturn(1)
        on(changesetManager.createChangeset(any(), any())).thenReturn(1)
        on(mapDataApi.uploadChanges(anyLong(), any(), any()))
            .thenThrow(ConflictException())

        assertFailsWith<ConflictException> {
            uploader.upload(edit, { mock() })
        }
    }

    @Test fun `handles changeset conflict exception`() {
        val edit: ElementEdit = mock()
        val action: ElementEditAction = mock()
        on(edit.action).thenReturn(action)
        on(action.createUpdates(any(), any())).thenReturn(MapDataChanges())

        on(changesetManager.getOrCreateChangeset(any(), any())).thenReturn(1)
        on(changesetManager.createChangeset(any(), any())).thenReturn(1)
        doThrow(ConflictException()).doAnswer { MapDataUpdates() }
            .on(mapDataApi).uploadChanges(anyLong(), any(), any())

        uploader.upload(edit, { mock() })
    }
}
