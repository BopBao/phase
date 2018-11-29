package phase.poordogsbone.com.phase.fragments

import android.database.Cursor
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import phase.poordogsbone.com.phase.MainActivity
import phase.poordogsbone.com.phase.provider.MyContentProvider
import phase.poordogsbone.com.phase.R
import phase.poordogsbone.com.phase.adapter.CalendarAdapter
import phase.poordogsbone.com.phase.database.RoutineInstance
import java.text.DateFormatSymbols
import java.util.*

/** Schedule for Routines */

class CalendarScroll: Fragment(), LoaderManager.LoaderCallbacks<Cursor>{
    private val dateFormatSymbols: DateFormatSymbols = DateFormatSymbols(Locale.getDefault())
    private val months: Array<String> = dateFormatSymbols.months
    private var days: Int = 0
    private var cal: Calendar = Calendar.getInstance()
    private var day = cal.get(Calendar.DAY_OF_MONTH)
    private val currentMonth = cal.get(Calendar.MONTH)
    private var month = cal.get(Calendar.MONTH)
    private val currentYear = cal.get(Calendar.YEAR)
    private var year = cal.get(Calendar.YEAR)
    private lateinit var adapter: CalendarAdapter

    init {
        this.loaderManager.initLoader(0, null, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_calendar_scroll, container, false)
        val monthSpinner = root.findViewById(R.id.month_spinner) as Spinner
        val recyclerView = root.findViewById(R.id.calendar_recycler) as RecyclerView
        val addButton = root.findViewById(R.id.add) as FloatingActionButton

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        context?.let {
            adapter = CalendarAdapter(days, it, month, year)
            recyclerView.adapter = adapter

            monthSpinner.adapter = ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, months)
            monthSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(parent: AdapterView<*>?, selectedView: View?, position: Int, id: Long) {
                    setDate(position, year)
            }}
            monthSpinner.setSelection(month)
        }

        addButton.setOnClickListener{
            context?.let {context -> if(context is MainActivity){ context.launchCreateExercise() } }
        }
        return root
    }

    override fun onCreateLoader(token: Int, args: Bundle?): Loader<Cursor> {
        val selection = "${RoutineInstance.YEAR_KEY} =? AND ${RoutineInstance.MONTH_KEY} =?"
        val selectionArgs = arrayOf(year.toString(), month.toString())
        val sortOrder = "${RoutineInstance.DATE_KEY} ASC"
        context?.let { context ->
            return CursorLoader(context, MyContentProvider.ROUTINE_INSTANCE_URI,
                null, selection, selectionArgs, sortOrder)}
    }

    override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor?) {
        cursor?.let { c ->  adapter.changeCursor(c)}
    }

    override fun onLoaderReset(loader: Loader<Cursor>) { }

    private fun setDate(month: Int?, year: Int?){
        month?.let { this.month = month }
        year?.let { this.year = year }

        if(currentMonth == month && currentYear == year){
            cal.set(this.year, this.month, day)
        }else{
            cal.set(this.year, this.month, 1)
        }
        days = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        adapter.changeDate(this.year, this.month, days)
        adapter.notifyDataSetChanged()
    }

    companion object {
        fun newInstance(): CalendarScroll{
            return CalendarScroll()
        }
    }
}