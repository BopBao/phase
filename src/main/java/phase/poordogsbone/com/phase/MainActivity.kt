package phase.poordogsbone.com.phase

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import phase.poordogsbone.com.phase.fragments.CalendarScroll
import phase.poordogsbone.com.phase.fragments.CreateExercise
import phase.poordogsbone.com.phase.fragments.CreateRoutine
import phase.poordogsbone.com.phase.fragments.ViewRoutine

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        launchCalendarScroll()
    }

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }


    fun launchCalendarScroll(){
        supportFragmentManager.inTransaction {
            add(R.id.container, CalendarScroll.newInstance())
        }
    }

    fun launchCreateRoutine(){
        supportFragmentManager.inTransaction {
            add(R.id.container, CreateRoutine.newInstance())
        }
    }

    fun launchCreateExercise(){
        supportFragmentManager.inTransaction {
            add(R.id.container, CreateExercise.newInstance())
        }
    }

    fun launchViewRoutine(){
        supportFragmentManager.inTransaction {
            add(R.id.container, ViewRoutine.newInstance())
        }
    }
}
