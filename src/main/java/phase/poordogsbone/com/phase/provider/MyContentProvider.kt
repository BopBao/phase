package phase.poordogsbone.com.phase.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import phase.poordogsbone.com.phase.database.*
import java.lang.IllegalArgumentException

const val ROUTINES = 1
const val EXERCISE_TEMPLATE = 2
const val EXERCISE = 3
const val ROUTINE_INSTANCE = 4
const val UNKNOWN_URI = "Unknown Uri: "
const val AUTHORITY = "phase.poordogsbone.com.phase.provider.MyContentProvider"
const val BASE_URI = "content://$AUTHORITY/"

class MyContentProvider: ContentProvider(){

    private lateinit var myDB: DbHandler

    private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

            init{
                sUriMatcher.addURI(
                    AUTHORITY, RoutineTable.ROUTINE_TABLE,
                    ROUTINES
                )
                sUriMatcher.addURI(
                    AUTHORITY, ExerciseTable.EXERCISE_TABLE,
                    EXERCISE
                )
                sUriMatcher.addURI(
                    AUTHORITY, ExerciseTemplate.EXERCISE_TEMPLATE_TABLE,
                    EXERCISE_TEMPLATE
                )
                sUriMatcher.addURI(
                    AUTHORITY, RoutineInstance.ROUTINE_INSTANCE_TABLE,
                    ROUTINE_INSTANCE
                )
            }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val uriType = sUriMatcher.match(uri)
        val sqlDB = myDB.writableDatabase
        val id: Long
        when(uriType){
            ROUTINES -> id = sqlDB.insert(RoutineTable.ROUTINE_TABLE, null, values)
            EXERCISE_TEMPLATE -> id = sqlDB.insert(ExerciseTemplate.EXERCISE_TEMPLATE_TABLE, null, values)
            EXERCISE -> id = sqlDB.insert(ExerciseTable.EXERCISE_TABLE, null, values)
            ROUTINE_INSTANCE -> id = sqlDB.insert(RoutineInstance.ROUTINE_INSTANCE_TABLE, null, values)
            else -> throw IllegalArgumentException("$UNKNOWN_URI $uri")
        }

        context?.contentResolver?.notifyChange(uri, null)
        return Uri.parse(RoutineTable.ROUTINE_TABLE + "/" + id)
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?,
                       sortOrder: String?): Cursor? {
        val queryBuilder = SQLiteQueryBuilder()
        val uriType = sUriMatcher.match(uri)

        when(uriType) {
            ROUTINES -> queryBuilder.tables = RoutineTable.ROUTINE_TABLE
            EXERCISE_TEMPLATE -> queryBuilder.tables = ExerciseTemplate.EXERCISE_TEMPLATE_TABLE
            EXERCISE -> queryBuilder.tables = ExerciseTable.EXERCISE_TABLE
            ROUTINE_INSTANCE -> queryBuilder.tables = RoutineInstance.ROUTINE_INSTANCE_TABLE
            else -> throw IllegalArgumentException("$UNKNOWN_URI $uri")
        }
            val cursor: Cursor = queryBuilder.query(myDB.readableDatabase, projection,
            selection, selectionArgs, null, null, sortOrder)
            cursor.setNotificationUri(context?.contentResolver, uri)
            return cursor
    }

    override fun onCreate(): Boolean {
        context?.let{
            myDB = DbHandler(it, null, null, 1)
        }
        return false
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        val uriType = sUriMatcher.match(uri)
        val rowsUpdated: Int
        val sqlDB = myDB.writableDatabase

        when(uriType){
            ROUTINES -> rowsUpdated = sqlDB.update(RoutineTable.ROUTINE_TABLE, values, selection, selectionArgs)
            EXERCISE_TEMPLATE -> rowsUpdated = sqlDB.update(ExerciseTemplate.EXERCISE_TEMPLATE_TABLE, values, selection, selectionArgs)
            EXERCISE -> rowsUpdated = sqlDB.update(ExerciseTable.EXERCISE_TABLE, values, selection, selectionArgs)
            ROUTINE_INSTANCE -> rowsUpdated = sqlDB.update(RoutineInstance.ROUTINE_INSTANCE_TABLE, values, selection, selectionArgs)
            else -> throw IllegalArgumentException("$UNKNOWN_URI $uri")
        }
        context?.contentResolver?.notifyChange(uri, null)
        return rowsUpdated
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val uriType = sUriMatcher.match(uri)
        val rowsDeleted: Int
        val sqlDB = myDB.writableDatabase

        when(uriType){
            ROUTINES -> rowsDeleted = sqlDB.delete(RoutineTable.ROUTINE_TABLE, selection, selectionArgs)
            EXERCISE_TEMPLATE -> rowsDeleted = sqlDB.delete(ExerciseTemplate.EXERCISE_TEMPLATE_TABLE, selection, selectionArgs)
            EXERCISE -> rowsDeleted = sqlDB.delete(ExerciseTable.EXERCISE_TABLE, selection, selectionArgs)
            ROUTINE_INSTANCE -> rowsDeleted = sqlDB.delete(RoutineInstance.ROUTINE_INSTANCE_TABLE, selection, selectionArgs)
            else -> throw IllegalArgumentException("$UNKNOWN_URI $uri")
        }
        context?.contentResolver?.notifyChange(uri, null)
        return rowsDeleted
    }

    override fun getType(p0: Uri): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        val ROUTINE_URI: Uri = Uri.parse("$BASE_URI${RoutineTable.ROUTINE_TABLE}")
        val ROUTINE_INSTANCE_URI: Uri = Uri.parse("$BASE_URI${RoutineInstance.ROUTINE_INSTANCE_TABLE}")
        val EXERCISE_URI: Uri = Uri.parse("$BASE_URI${ExerciseTable.EXERCISE_TABLE}")
        val EXERCISE_TEMPLATE_URI: Uri = Uri.parse("$BASE_URI${ExerciseTemplate.EXERCISE_TEMPLATE_TABLE}")
    }
}