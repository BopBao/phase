package phase.poordogsbone.com.phase.database

class RoutineInstance {

    companion object {
        const val ROUTINE_INSTANCE_TABLE = "phase_instance"
        const val ID_KEY = "_id"
        const val DATE_KEY = "date"
        const val YEAR_KEY = "year"
        const val MONTH_KEY = "month"
        const val COMPLETED_KEY = "completed"
        const val ROUTINE_ID_KEY = "phase_id"
        const  val CREATE_ROUTINE_INSTANCE_TABLE = " CREATE TABLE " + ROUTINE_INSTANCE_TABLE + "(" +
                ID_KEY + " INTEGER PRIMARY KEY, " +
                DATE_KEY + " REAL, " +
                YEAR_KEY + " INTEGER, " +
                MONTH_KEY + " INTEGER, " +
                COMPLETED_KEY + " INTEGER, " +
                ROUTINE_ID_KEY + " INTEGER, " +
                " FOREIGN KEY (" + ROUTINE_ID_KEY + ") REFERENCES " + RoutineTable.ROUTINE_TABLE + "(" + RoutineTable.ID_KEY +
                ") ON DELETE CASCADE ON UPDATE CASCADE);"
    }
}