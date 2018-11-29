package phase.poordogsbone.com.phase.database

class RoutineTable {
    var id: Int = 0
    var title: String = ""

    constructor(id: Int, title: String){
        this.id = id
        this.title = title
    }

    companion object {
        const val ROUTINE_TABLE = "routine"
        const val ID_KEY = "_id"
        const val TITLE_KEY = "title"
        const val CREATE_ROUTINE_TABLE = ("CREATE TABLE " + ROUTINE_TABLE +
                "(" + ID_KEY + " INTEGER PRIMARY KEY, " +
                TITLE_KEY + " TEXT);"
                )
    }


}