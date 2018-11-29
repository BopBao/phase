package phase.poordogsbone.com.phase.handlers

import android.content.AsyncQueryHandler
import android.content.ContentResolver
import android.net.Uri

import java.lang.ref.WeakReference

class ListenInsertHandler : AsyncQueryHandler {

    private var mListener: WeakReference<ListenInsertHandler.AsyncQueryListener>? = null

    interface AsyncQueryListener {
        fun onInsertComplete(token: Int, cookie: Any, uri: Uri)
    }

    constructor(cr: ContentResolver, listener: ListenInsertHandler.AsyncQueryListener) : super(cr) {
        mListener = WeakReference(listener)
    }

    constructor(cr: ContentResolver) : super(cr) {}

    /** Assign the given [ListenInsertHandler.AsyncQueryListener] to receive query events from asynchronous calls */
    fun setQueryListener(listener: ListenInsertHandler.AsyncQueryListener) {
        mListener = WeakReference(listener)
    }

    override fun onInsertComplete(token: Int, cookie: Any, uri: Uri) {
        val listener = mListener!!.get()
        listener?.onInsertComplete(token, cookie, uri)
    }
}