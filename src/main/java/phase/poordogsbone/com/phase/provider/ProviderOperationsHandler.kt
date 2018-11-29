package phase.poordogsbone.com.phase.provider

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import phase.poordogsbone.com.phase.database.ExerciseTable
import phase.poordogsbone.com.phase.database.ExerciseTemplate
import phase.poordogsbone.com.phase.database.RoutineInstance
import phase.poordogsbone.com.phase.database.RoutineTable
import phase.poordogsbone.com.phase.handlers.ListenInsertHandler

class ProviderOperationsHandler(context: Context){
    private val queryHandler = context.contentResolver
    private val listenInsertHandler: ListenInsertHandler = ListenInsertHandler(context.contentResolver)

    fun insertExercise(values: ContentValues){
        queryHandler.insert(MyContentProvider.EXERCISE_URI, values)
    }

    fun deleteExercise(_id: Int){
        val selection = "${ExerciseTable.ID_KEY} =?"
        val selectionArgs = arrayOf(_id.toString())
        queryHandler.delete(MyContentProvider.EXERCISE_URI, selection, selectionArgs)
    }

    fun updateExercise(_id: Int, values: ContentValues){
        val selection = "${ExerciseTable.ID_KEY} =?"
        val selectionArgs = arrayOf(_id.toString())
        queryHandler.update(MyContentProvider.EXERCISE_URI, values, selection, selectionArgs)
    }

    fun insertRoutine(values: ContentValues, exercisesList: ArrayList<Int>){
        class Listener : ListenInsertHandler.AsyncQueryListener{
            override fun onInsertComplete(token: Int, cookie: Any, uri: Uri){
                val routineId = uri.lastPathSegment
                for(i in exercisesList){
                    val exerciseValues = ContentValues()
                    exerciseValues.put(ExerciseTemplate.KEY_EXERCISE_ID, i)
                    exerciseValues.put(ExerciseTemplate.ROUTINE_ID_KEY, routineId)
                    queryHandler.insert(MyContentProvider.EXERCISE_TEMPLATE_URI, exerciseValues)
                }
            }
        }
        val listener = Listener()
        listenInsertHandler.setQueryListener(listener)
        listenInsertHandler.startInsert(0, null, MyContentProvider.ROUTINE_URI, values)
    }

    fun deleteRoutine(_id: Int){
        val selection = "${RoutineTable.ID_KEY} =?"
        val selectionArgs = arrayOf(_id.toString())
        queryHandler.delete(MyContentProvider.ROUTINE_URI, selection, selectionArgs)
    }

    fun updateRoutine(_id: Int, values: ContentValues){
        val selection = "${RoutineTable.ID_KEY} =?"
        val selectionArgs = arrayOf(_id.toString())
        queryHandler.update(MyContentProvider.ROUTINE_URI, values, selection, selectionArgs)
    }

    fun insertRoutineInstance(values: ContentValues){
        queryHandler.insert(MyContentProvider.ROUTINE_INSTANCE_URI, values)
    }

    fun deleteRoutineInstance(_id: Int){
        val selection = "${RoutineInstance.ID_KEY} =?"
        val selectionArgs = arrayOf(_id.toString())
        queryHandler.delete(MyContentProvider.ROUTINE_INSTANCE_URI, selection, selectionArgs)
    }

    fun updateRoutineInstance(_id: Int, values: ContentValues){
        val selection = "${RoutineInstance.ID_KEY} =?"
        val selectionArgs = arrayOf(_id.toString())
        queryHandler.update(MyContentProvider.ROUTINE_INSTANCE_URI, values, selection, selectionArgs)
    }

    fun insertExerciseTemplate(values: ContentValues){
        queryHandler.insert(MyContentProvider.EXERCISE_TEMPLATE_URI, values)
    }

    fun deleteExerciseTemplate(_id: Int){
        val selection = "${ExerciseTemplate.KEY_ID} =?"
        val selectionArgs = arrayOf(_id.toString())
        queryHandler.delete(MyContentProvider.EXERCISE_TEMPLATE_URI, selection, selectionArgs)
    }

    fun updateExerciseTemplate(_id: Int, values: ContentValues){
        val selection = "${ExerciseTemplate.KEY_ID} =?"
        val selectionArgs = arrayOf(_id.toString())
        queryHandler.update(MyContentProvider.EXERCISE_TEMPLATE_URI, values, selection, selectionArgs)
    }
}