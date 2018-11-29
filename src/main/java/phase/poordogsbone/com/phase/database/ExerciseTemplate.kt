package phase.poordogsbone.com.phase.database

class ExerciseTemplate {
    var id: Int = 0
    var exercise_id: Int = 0
    var phase_id: Int = 0

    constructor(id: Int, exercise_id: Int, phase_id: Int){
        this.id = id
        this.exercise_id = exercise_id
        this.phase_id = phase_id
    }

    companion object {
        const val EXERCISE_TEMPLATE_TABLE = "exercise_template"
        const val KEY_ID = "_id"
        const val KEY_EXERCISE_ID = "exercise_id"
        const val ROUTINE_ID_KEY = "phase_id"
        const val CREATE_EXERCISE_TEMPLATE = " CREATE TABLE " + EXERCISE_TEMPLATE_TABLE + "(" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_EXERCISE_ID + "INTEGER, " +
                ROUTINE_ID_KEY + " INTEGER, " +
                " FOREIGN KEY (" + KEY_EXERCISE_ID + ") REFERENCES " + ExerciseTable.EXERCISE_TABLE + "(" + ExerciseTable.ID_KEY +
                ") ON DELETE CASCADE ON UPDATE CASCADE, " +
                " FOREIGN KEY (" + ROUTINE_ID_KEY + ") REFERENCES " + RoutineTable.ROUTINE_TABLE + "(" + RoutineTable.ID_KEY +
                ") ON DELETE CASCADE ON UPDATE CASCADE);"
    }
}