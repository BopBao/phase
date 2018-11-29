package phase.poordogsbone.com.phase.fragments

import android.content.ContentValues
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import phase.poordogsbone.com.phase.R
import phase.poordogsbone.com.phase.database.ExerciseTable
import phase.poordogsbone.com.phase.provider.ProviderOperationsHandler

class CreateExercise: Fragment(){
    private val numRange = arrayOf(1..10)
    private var rpe = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.create_exercise, container, false)
        val rpeSpinner = root.findViewById(R.id.rpe) as Spinner
        val createButton = root.findViewById(R.id.create_button) as Button
        val nameEditText = root.findViewById(R.id.name) as EditText
        val setsEditText = root.findViewById(R.id.sets) as EditText
        val repsEditText = root.findViewById(R.id.reps) as EditText
        val restEditText = root.findViewById(R.id.rest) as EditText

        context?.let { context ->
            rpeSpinner.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, numRange)
            rpeSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    rpe = position + 1
                }
            }
        }

        createButton.setOnClickListener {view->
            view.isClickable = false
            val nameEmpty = isEmpty(nameEditText)
            val setsEmpty = isEmpty(setsEditText)
            val repsEmpty = isEmpty(repsEditText)
            if(nameEmpty || setsEmpty || repsEmpty){
                view.isClickable = true
            }else{
                val values = ContentValues()
                values.put(ExerciseTable.NAME_KEY, nameEditText.text.toString())
                values.put(ExerciseTable.SETS_KEY, setsEditText.text.toString().toInt())
                values.put(ExerciseTable.REPS_KEY, repsEditText.text.toString().toInt())
                values.put(ExerciseTable.REST_KEY, restEditText.text.toString().toInt())
                values.put(ExerciseTable.RPE_KEY, rpe)
                context?.let { context ->
                    val operationsHandler = ProviderOperationsHandler(context)
                    operationsHandler.insertExercise(values)
                }
            }
        }
        return root
    }

    companion object {
        fun newInstance(): CreateExercise{
            return CreateExercise()
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