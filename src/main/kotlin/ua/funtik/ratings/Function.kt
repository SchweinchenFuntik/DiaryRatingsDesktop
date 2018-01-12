package ua.funtik.ratings

import javafx.geometry.Insets
import javafx.scene.Node
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Region
import tornadofx.*
import ua.funtik.ratings.api.OK
import ua.funtik.ratings.model.*
import javax.json.JsonArray
import javax.json.JsonValue

fun Region.padding(value: Double){
    padding = Insets(value)
}

fun Region.padding(value: Int) = padding(value.toDouble())

fun Node.margin(top: Number = 0.0, right: Number = 0.0, bottom: Number = 0.0, left: Number = 0.0)
        = BorderPane.setMargin(this, Insets(top.toDouble(), right.toDouble(), bottom.toDouble(), left.toDouble()))

fun Node.marginFull(value: Number) = BorderPane.setMargin(this, Insets(value.toDouble()))

fun Rest.responseAsync(path: String, code: Int, vararg db: Any): MessageJson {
    return post(path, message(code, db.toList()))
            .consume()
            .one()
            .toModel()

}
inline fun Rest.responseAsync(path: String, code: Int, vararg db: Any, fOk: MessageJson.() -> Unit) {
    post(path, message(code, db.toList()))
            .consume()
            .one()
            .toModel<MessageJson>()
            .fOk()
}

inline fun MessageJson.checkOk(fOk: MessageJson.() -> Unit) {
    if (code == OK) fOk()
}

inline fun MessageJson.responseOk(fOk: JsonArray.() -> Unit) {
    if (code == OK) response?.fOk()
}

inline fun JsonArray.toJsonArray(i: Int) = this[i] as? JsonArray
inline fun JsonValue.isJsonArray() = this as? JsonArray



//
//fun Rest.subjects(student: StudentJson, list: ObservableList<SubjectJson>, msg: StringProperty? = null){
//    checkResponse(post("student/subjects", student.id), msg) {
//        list.setAll(consume().list().toModel())
//    }
//}
//
////!!!!!!!!!!!!!!!
//fun Rest.ratings(student: StudentJson, subject: SubjectJson, list: ObservableList<RatingJson>, msg: StringProperty? = null){
//    checkResponse(post("student/subject/ratings", student.id), msg) { // !!!!!
//        list.setAll(consume().list().toModel())
//    }
//}

fun message(code: Int = OK, response: List<Any>): MessageJson {
    val builder = JsonBuilder()
    builder.add("code", code)
    builder.add("response", response)
    return builder.build().toModel()
}

fun message(code: Int = OK, response: Any) = message(code, listOf(response))
fun message(code: Int, vararg response: Any) = message(code, response.toList())


//fun serverResponse(response: List<Any>, code: Int = OK): ServerResponseJson {
//    val builder = JsonBuilder()
//    builder.add("code", code)
//    builder.add("response", response)
//    return builder.build().toModel()
//}

val tables: Sequence<Pair<String, Tables>> = generateSequence {
    "Пользователи" to Tables.USER
    "Студенты" to Tables.STUDENT
    "Лекторы" to Tables.LECTURER
    "Предметы" to Tables.SUBJECT
    "Группы" to Tables.GROUP
    "Оценки" to Tables.RATING
}

enum class Tables {
    USER, STUDENT, GROUP, LECTURER, SUBJECT, RATING
}

fun getModel(name: String) = getModel(tables.find { it.first == name }?.second!!)

fun getModel(table: Tables): ItemViewModel<out JsonModel> {
    return when(table) {
        Tables.USER -> UserModel()
        Tables.STUDENT -> StudentModel()
        Tables.LECTURER -> LecturerModel()
        Tables.SUBJECT -> SubjectModel()
        Tables.GROUP -> GroupModel()
        Tables.RATING -> RatingModel()
    }
}