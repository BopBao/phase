package phase.poordogsbone.com.phase.adapter

import android.content.Context
import android.database.Cursor
import android.database.DataSetObserver
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_calendar_item.view.*
import phase.poordogsbone.com.phase.MainActivity
import phase.poordogsbone.com.phase.R
import java.util.*

class CalendarAdapter(var items: Int, val context: Context,
                      private val year: Int, private val month: Int): RecyclerView.Adapter<CalendarAdapter.Holder>(){

    private var currentMonth = this.month
    private var currentYear = this.year
    private var cursor: Cursor? = null
    private val dataSetObserver: DataSetObserver?

    init {
        dataSetObserver = this.NotifyingDataSetObserver()
        cursor?.registerDataSetObserver(dataSetObserver)
    }

    override fun getItemCount(): Int {
        return items
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val day = position + 1
        var hasRoutine = false
        holder.dayTextView.text = "$day"
        cursor?.let { cursor ->
            cursor.moveToFirst()
            val cal = Calendar.getInstance()
            cal.timeInMillis = cursor.getLong(1)
            while(cursor.moveToNext()){
                if(cal.get(Calendar.DAY_OF_MONTH) < day){
                    continue
                }else if (cal.get(Calendar.DAY_OF_MONTH) == day){
                    holder.nameTextView
                    hasRoutine = true
                }else if(cal.get(Calendar.DAY_OF_MONTH) > day){
                    break
                }
            }
        }
        holder.root.setOnClickListener{
            if(hasRoutine){
                if(context is MainActivity){
                    context.launchViewRoutine()
                }
            }else{
                if(context is MainActivity){
                    /** Launch Something to Choose A Routine */
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(context).inflate(R.layout.fragment_calendar_item, parent, false))
    }

    class Holder(view: View): RecyclerView.ViewHolder(view){
        val nameTextView = view.name as TextView
        val dayTextView = view.day as TextView
        val root = view
    }

    fun setCursor(cursor: Cursor) {
        this.cursor = cursor
    }

    fun changeCursor(cursor: Cursor) {
        val old = swapCursor(cursor)
        old?.close()
    }

    private fun swapCursor(newCursor: Cursor): Cursor? {
        if (newCursor === cursor) {
            return null
        }
        val oldCursor = cursor
        if (oldCursor != null && dataSetObserver != null) {
            oldCursor.unregisterDataSetObserver(dataSetObserver)
        }
        cursor = newCursor
        if (cursor != null) {
            if (dataSetObserver != null) {
                cursor!!.registerDataSetObserver(dataSetObserver)
            }
            notifyDataSetChanged()
        }
        return oldCursor
    }

    private inner class NotifyingDataSetObserver : DataSetObserver() {
        override fun onChanged() {
            super.onChanged()
            notifyDataSetChanged()
        }
    }

    fun changeDate(year: Int, month: Int, days: Int){
        currentMonth = month
        currentYear = year
        items = days
    }
}