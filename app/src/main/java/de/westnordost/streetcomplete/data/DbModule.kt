package de.westnordost.streetcompletegpx.data

import android.database.sqlite.SQLiteOpenHelper
import de.westnordost.streetcompletegpx.ApplicationConstants
import org.koin.dsl.module

val dbModule = module {
    single<Database> { AndroidDatabase(get()) }
    single<SQLiteOpenHelper> { StreetCompleteSQLiteOpenHelper(get(), ApplicationConstants.DATABASE_NAME) }
}
