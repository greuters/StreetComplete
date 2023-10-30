package de.westnordost.streetcompletegpx

import android.app.Application
import android.content.ComponentCallbacks2
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import de.westnordost.streetcompletegpx.data.CacheTrimmer
import de.westnordost.streetcompletegpx.data.CleanerWorker
import de.westnordost.streetcompletegpx.data.Preloader
import de.westnordost.streetcompletegpx.data.dbModule
import de.westnordost.streetcompletegpx.data.download.downloadModule
import de.westnordost.streetcompletegpx.data.download.tiles.DownloadedTilesController
import de.westnordost.streetcompletegpx.data.edithistory.EditHistoryController
import de.westnordost.streetcompletegpx.data.edithistory.editHistoryModule
import de.westnordost.streetcompletegpx.data.maptiles.maptilesModule
import de.westnordost.streetcompletegpx.data.messages.messagesModule
import de.westnordost.streetcompletegpx.data.meta.metadataModule
import de.westnordost.streetcompletegpx.data.osm.created_elements.createdElementsModule
import de.westnordost.streetcompletegpx.data.osm.edits.elementEditsModule
import de.westnordost.streetcompletegpx.data.osm.geometry.elementGeometryModule
import de.westnordost.streetcompletegpx.data.osm.mapdata.mapDataModule
import de.westnordost.streetcompletegpx.data.osm.osmquests.osmQuestModule
import de.westnordost.streetcompletegpx.data.osmApiModule
import de.westnordost.streetcompletegpx.data.osmnotes.edits.noteEditsModule
import de.westnordost.streetcompletegpx.data.osmnotes.notequests.osmNoteQuestModule
import de.westnordost.streetcompletegpx.data.osmnotes.notesModule
import de.westnordost.streetcompletegpx.data.overlays.overlayModule
import de.westnordost.streetcompletegpx.data.quest.questModule
import de.westnordost.streetcompletegpx.data.upload.uploadModule
import de.westnordost.streetcompletegpx.data.urlconfig.urlConfigModule
import de.westnordost.streetcompletegpx.data.user.UserLoginStatusController
import de.westnordost.streetcompletegpx.data.user.achievements.achievementsModule
import de.westnordost.streetcompletegpx.data.user.statistics.statisticsModule
import de.westnordost.streetcompletegpx.data.user.userModule
import de.westnordost.streetcompletegpx.data.visiblequests.questPresetsModule
import de.westnordost.streetcompletegpx.overlays.overlaysModule
import de.westnordost.streetcompletegpx.quests.oneway_suspects.data.trafficFlowSegmentsModule
import de.westnordost.streetcompletegpx.quests.questsModule
import de.westnordost.streetcompletegpx.screens.main.mainModule
import de.westnordost.streetcompletegpx.screens.main.map.mapModule
import de.westnordost.streetcompletegpx.screens.measure.arModule
import de.westnordost.streetcompletegpx.screens.settings.ResurveyIntervalsUpdater
import de.westnordost.streetcompletegpx.screens.settings.settingsModule
import de.westnordost.streetcompletegpx.util.CrashReportExceptionHandler
import de.westnordost.streetcompletegpx.util.getDefaultTheme
import de.westnordost.streetcompletegpx.util.getSelectedLocale
import de.westnordost.streetcompletegpx.util.getSystemLocales
import de.westnordost.streetcompletegpx.util.ktx.addedToFront
import de.westnordost.streetcompletegpx.util.ktx.nowAsEpochMilliseconds
import de.westnordost.streetcompletegpx.util.setDefaultLocales
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit

class StreetCompleteApplication : Application() {

    private val preloader: Preloader by inject()
    private val crashReportExceptionHandler: CrashReportExceptionHandler by inject()
    private val resurveyIntervalsUpdater: ResurveyIntervalsUpdater by inject()
    private val downloadedTilesController: DownloadedTilesController by inject()
    private val prefs: SharedPreferences by inject()
    private val editHistoryController: EditHistoryController by inject()
    private val userLoginStatusController: UserLoginStatusController by inject()
    private val cacheTrimmer: CacheTrimmer by inject()

    private val applicationScope = CoroutineScope(SupervisorJob() + CoroutineName("Application"))

    override fun onCreate() {
        super.onCreate()

        deleteDatabase(ApplicationConstants.OLD_DATABASE_NAME)

        startKoin {
            androidContext(this@StreetCompleteApplication)
            workManagerFactory()
            modules(
                achievementsModule,
                appModule,
                createdElementsModule,
                dbModule,
                downloadModule,
                editHistoryModule,
                elementEditsModule,
                elementGeometryModule,
                mapDataModule,
                mapModule,
                mainModule,
                maptilesModule,
                metadataModule,
                noteEditsModule,
                notesModule,
                messagesModule,
                osmApiModule,
                osmNoteQuestModule,
                osmQuestModule,
                questModule,
                questPresetsModule,
                questsModule,
                settingsModule,
                statisticsModule,
                trafficFlowSegmentsModule,
                uploadModule,
                userModule,
                arModule,
                overlaysModule,
                overlayModule,
                urlConfigModule
            )
        }

        /* Force log out users who use the old OAuth consumer key+secret because it does not exist
           anymore. Trying to use that does not result in a "not authorized" API response, but some
           response the app cannot handle */
        if (!prefs.getBoolean(Prefs.OSM_LOGGED_IN_AFTER_OAUTH_FUCKUP, false)) {
            if (userLoginStatusController.isLoggedIn) {
                userLoginStatusController.logOut()
            }
        }

        setDefaultLocales()

        crashReportExceptionHandler.install()

        applicationScope.launch {
            preloader.preload()
            editHistoryController.deleteSyncedOlderThan(nowAsEpochMilliseconds() - ApplicationConstants.MAX_UNDO_HISTORY_AGE)
        }

        enqueuePeriodicCleanupWork()

        setDefaultTheme()

        resurveyIntervalsUpdater.update()

        val lastVersion = prefs.getString(Prefs.LAST_VERSION_DATA, null)
        if (BuildConfig.VERSION_NAME != lastVersion) {
            prefs.edit { putString(Prefs.LAST_VERSION_DATA, BuildConfig.VERSION_NAME) }
            if (lastVersion != null) {
                onNewVersion()
            }
        }
    }

    private fun onNewVersion() {
        // on each new version, invalidate quest cache
        downloadedTilesController.invalidateAll()
    }

    override fun onTerminate() {
        super.onTerminate()
        applicationScope.cancel()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        when (level) {
            ComponentCallbacks2.TRIM_MEMORY_COMPLETE, ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL -> {
                // very low on memory -> drop caches
                cacheTrimmer.clearCaches()
            }
            ComponentCallbacks2.TRIM_MEMORY_MODERATE, ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW -> {
                // memory needed, but not critical -> trim only
                cacheTrimmer.trimCaches()
            }
        }
    }

    private fun setDefaultLocales() {
        val locale = getSelectedLocale(this)
        if (locale != null) {
            setDefaultLocales(getSystemLocales().addedToFront(locale))
        }
    }

    private fun setDefaultTheme() {
        val theme = Prefs.Theme.valueOf(prefs.getString(Prefs.THEME_SELECT, getDefaultTheme())!!)
        AppCompatDelegate.setDefaultNightMode(theme.appCompatNightMode)
    }

    private fun enqueuePeriodicCleanupWork() {
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "Cleanup",
            ExistingPeriodicWorkPolicy.KEEP,
            PeriodicWorkRequest.Builder(
                CleanerWorker::class.java,
                1, TimeUnit.DAYS,
                1, TimeUnit.DAYS,
            ).setInitialDelay(1, TimeUnit.HOURS).build()
        )
    }
}
