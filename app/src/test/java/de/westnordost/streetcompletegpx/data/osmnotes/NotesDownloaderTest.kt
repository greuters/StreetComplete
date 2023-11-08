package de.westnordost.streetcompletegpx.data.osmnotes

import de.westnordost.streetcompletegpx.testutils.any
import de.westnordost.streetcompletegpx.testutils.bbox
import de.westnordost.streetcompletegpx.testutils.eq
import de.westnordost.streetcompletegpx.testutils.mock
import de.westnordost.streetcompletegpx.testutils.note
import de.westnordost.streetcompletegpx.testutils.on
import kotlinx.coroutines.runBlocking
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.verify
import kotlin.test.BeforeTest
import kotlin.test.Test

class NotesDownloaderTest {
    private lateinit var noteController: NoteController
    private lateinit var notesApi: NotesApi

    @BeforeTest fun setUp() {
        noteController = mock()
        notesApi = mock()
    }

    @Test fun `calls controller with all notes coming from the notes api`() = runBlocking {
        val note1 = note()
        val bbox = bbox()

        on(notesApi.getAll(any(), anyInt(), anyInt())).thenReturn(listOf(note1))
        val dl = NotesDownloader(notesApi, noteController)
        dl.download(bbox)

        verify(noteController).putAllForBBox(eq(bbox), eq(listOf(note1)))
    }
}
