package phase.poordogsbone.com.phase.database

class ExerciseTable {
    var id: Int = 0
    var name: String = ""
    var sets: Int = 0
    var reps: Int = 0
    var rest: Int = 0
    var RPE: Int = 0

    constructor(id: Int, name: String, sets: Int, reps: Int, rest: Int, RPE: Int){
        this.id = id
        this.name = name
        this.sets = sets
        this.reps = reps
        this.rest = rest
        this.RPE = RPE
    }

    companion object {
        const val EXERCISE_TABLE = "exercise_table"
        const val ID_KEY = "_id"
        const val NAME_KEY = "name"
        const val SETS_KEY = "sets"
        const val REPS_KEY = "reps"
        const val REST_KEY = "rest"
        const val RPE_KEY = "rpe"
        const val CREATE_EXERCISE_TABLE = " CREATE TABLE " + EXERCISE_TABLE + "(" +
                ID_KEY + " INTEGER PRIMARY KEY, " +
                NAME_KEY + " TEXT, " +
                SETS_KEY + " INTEGER, " +
                REPS_KEY + " INTEGER, " +
                REST_KEY + " INTEGER, " +
                RPE_KEY + " INTEGER);"
    }
}