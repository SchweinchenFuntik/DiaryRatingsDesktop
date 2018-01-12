package ua.funtik.ratings

import javafx.collections.FXCollections.observableArrayList
import javafx.collections.ObservableList
import tornadofx.*
import ua.funtik.ratings.api.DATA
import ua.funtik.ratings.model.*
import javax.json.JsonArray

class Cache : Component(), ScopedInstance {
    val api: Rest by inject()

    val ratings = observableArrayList<RatingJson>()
    val groups = observableArrayList<GroupJson>()
    val subjects = observableArrayList<SubjectJson>()
    val students = observableArrayList<StudentJson>()
    val lecturers = observableArrayList<LecturerJson>()
    val users = observableArrayList<UserJson>()

    fun updateLecturers() = lecturers.update("lecturers")
    fun updateUsers() = users.update("users")
    fun updateStudents() = students.update("students")
    fun updateGroups() = groups.update("groups")
    fun updateSubjects() = subjects.update("subjects")
    fun updateRatings() = ratings.update("ratings")

    private inline fun <reified T : JsonModel> ObservableList<T>.update(table: String) {
        Response.responseAsync("data", DATA, CurrentUser.user, table).responseOk {
            (this[0] as? JsonArray)?.let { this@update.setAll(it.toModel()) }
        }
    }

    fun updateLecturer() {
        Response.responseAsync("data", DATA, CurrentUser.lecturer!!, "lecturer").responseOk {
            this[0].isJsonArray()?.let { subjects.setAll(it.toModel()) }
            this[1].isJsonArray()?.let { groups.setAll(it.toModel()) }
            this[2].isJsonArray()?.let { ratings.setAll(it.toModel()) }
            this[3].isJsonArray()?.let { students.setAll(it.toModel()) }
        }
    }

    fun clear() {
        ratings.clear()
        subjects.clear()
        groups.clear()
        students.clear()
        lecturers.clear()
        users.clear()
    }
}