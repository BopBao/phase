package phase.poordogsbone.com.phase.fragments

import android.content.ContentValues
import android.database.Cursor
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import phase.poordogsbone.com.phase.R
import phase.poordogsbone.com.phase.database.RoutineTable
import phase.poordogsbone.com.phase.provider.MyContentProvider
import phase.poordogsbone.com.phase.provider.ProviderOperationsHandler

class CreateRoutine: Fragment(), LoaderManager.LoaderCallbacks<Cursor>{
    lateinit var searchableList: ArrayList<String>
    private val exerciseList = ArrayList<Int>()

    init {
        this.loaderManager.initLoader(0, null, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.create_routine, container, false)
        val nameEditText = root.findViewById(R.id.name) as EditText
        val createButton = root.findViewById(R.id.create_button) as Button

        createButton.setOnClickListener { view->
            view.isClickable = false
            val nameEmpty = isEmpty(nameEditText)
            if(nameEmpty){
                view.isClickable = true
            }else{
                val values = ContentValues()
                values.put(RoutineTable.TITLE_KEY, nameEditText.text.toString())
                context?.let { context ->
                    val operationsHandler = ProviderOperationsHandler(context)
                    operationsHandler.insertRoutine(values, exerciseList)
                }
            }
        }
        return root
    }

    override fun onCreateLoader(token: Int, args: Bundle?): Loader<Cursor> {
        context?.let { context->
            return CursorLoader(context, MyContentProvider.EXERCISE_URI, null,
                null, null, null)
        }
    }

    override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor?) {
        cursor?.let { c->
            generateSequence {
                if(c.moveToNext()){
                    cursor.getString(1)
                    val exercise = "${cursor.getString(1)} ${getString(R.string.sets)}:${cursor.getInt(2)} ${getString(R.string.reps)}:${cursor.getInt(3)}"
                    searchableList.add(exercise)
                }
            }
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {

    }

    companion object {
        fun newInstance(): CreateRoutine {
            return CreateRoutine()
        }
    }

    private fun isEmpty(editText: EditText): Boolean{
        if(editText.text.isNullOrEmpty()){
            editText.error = getString(R.string.empty_error)
            return true
        }
        return false
    }
}