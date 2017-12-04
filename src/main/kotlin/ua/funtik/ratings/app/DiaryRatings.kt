package ua.funtik.ratings.app

import javafx.beans.property.SimpleObjectProperty
import tornadofx.*
import ua.funtik.ratings.model.LecturerJson
import ua.funtik.ratings.model.StudentJson
import tornadofx.getValue
import tornadofx.setValue
import ua.funtik.ratings.model.UserJson
import ua.funtik.ratings.view.LoginView

class DiaryRatings: App(LoginView::class, Styles::class){
    val api: Rest by inject()

    init {
//        println("http://${config["server.url"]}:${config["server.port"]}")
        api.baseURI = "http://localhost:8080/api" //
        Rest.useApacheHttpClient()
    }
}

object currentUser{
    val userProperty = SimpleObjectProperty<UserJson>()
    var user by userProperty

    val studentProperty = SimpleObjectProperty<StudentJson?>()
    var student by studentProperty

    val lecturerProperty = SimpleObjectProperty<LecturerJson?>()
    var lecturer by lecturerProperty
}

fun main(args: Array<String>) {
    launch<DiaryRatings>(args)
}
