package ua.funtik.ratings

import javafx.beans.property.StringProperty
import javafx.collections.ObservableList
import javafx.geometry.Insets
import javafx.scene.Node
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Region
import tornadofx.*
import ua.funtik.ratings.model.RatingJson
import ua.funtik.ratings.model.ServerResponseJson
import ua.funtik.ratings.model.StudentJson
import ua.funtik.ratings.model.SubjectJson
import javax.json.JsonObject

fun Region.padding(value: Double){
    padding = Insets(value)
}

fun Region.padding(value: Int) = padding(value.toDouble())

fun Node.margin(top: Number = 0.0, right: Number = 0.0, bottom: Number = 0.0, left: Number = 0.0)
        = BorderPane.setMargin(this, Insets(top.toDouble(), right.toDouble(), bottom.toDouble(), left.toDouble()))

fun Node.marginFull(value: Number) = BorderPane.setMargin(this, Insets(value.toDouble()))

inline fun checkResponse(r: Rest.Response, msg: StringProperty? = null, fOk: Rest.Response.() -> Unit) {
    when(r.statusCode) {
        200 -> r.fOk()
        404 -> msg?.value = "Errors connected server"
        500 -> msg?.value = "Errors server"
    }
}

fun Rest.subjects(student: StudentJson, list: ObservableList<SubjectJson>, msg: StringProperty? = null){
    checkResponse(post("student/subjects", student.id), msg) {
        list.setAll(consume().list().toModel())
    }
}

//!!!!!!!!!!!!!!!
fun Rest.ratings(student: StudentJson, subject: SubjectJson, list: ObservableList<RatingJson>, msg: StringProperty? = null){

    checkResponse(post("api/student/subject/ratings", student.id), msg) { // !!!!!
        list.setAll(consume().list().toModel())
    }
}

fun serverResponse(response: List<Any>, code: Int = OK): ServerResponseJson {
    val builder = JsonBuilder()
    builder.add("code", code)
    builder.add("response", response)
    return builder.build().toModel()
}
