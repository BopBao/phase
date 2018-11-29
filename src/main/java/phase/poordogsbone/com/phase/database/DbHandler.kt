package phase.poordogsbone.com.phase.database

import android.content.ContentResolver
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


const val DATABASE_VERSION = 1
const val DATABASE_NAME = "phase.db"

class DbHandler(context: Context, name: String?, factory: SQLiteDatabase.CursorFactory?,
                version: Int): SQLiteOpenHelper(context, DATABASE_NAME,
                factory, DATABASE_VERSION){

    private val contentResolver: ContentResolver

    init {
        contentResolver = context.contentResolver
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(RoutineTable.CREATE_ROUTINE_TABLE)
        db.execSQL(ExerciseTable.CREATE_EXERCISE_TABLE)
        db.execSQL(ExerciseTemplate.CREATE_EXERCISE_TEMPLATE)
        db.execSQL(RoutineInstance.CREATE_ROUTINE_INSTANCE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    //fun addPhase()
}