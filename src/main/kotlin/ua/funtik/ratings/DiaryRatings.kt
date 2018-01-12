package ua.funtik.ratings

import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.Alert
import tornadofx.*
import ua.funtik.ratings.model.LecturerJson
import ua.funtik.ratings.model.MessageJson
import ua.funtik.ratings.model.StudentJson
import ua.funtik.ratings.model.UserJson
import ua.funtik.ratings.view.LecturerView
import ua.funtik.ratings.view.LoginView
import ua.funtik.ratings.view.SelectedUserTypeView
import ua.funtik.ratings.view.StudentView

class DiaryRatings: App(LoginView::class, Styles::class){
    val api: Rest by inject()
    val loginView: LoginView by inject()
    val studentView: StudentView by inject()

    init {
//        println("http://${config["server.url"]}:${config["server.port"]}")
        api.baseURI = "http://localhost:8080/api"
        Rest.useApacheHttpClient()

        CurrentUser.userProperty.addListener { _, ov, nv ->
            if(ov == nv) return@addListener
            val view = find(primaryView)
            if(nv == null) {
                Alert(Alert.AlertType.ERROR, "Не удалось выполнить вход").showAndWait()
            } else {
                if (CurrentUser.student != null && CurrentUser.lecturer != null){
                    view.replaceWith<SelectedUserTypeView>(sizeToScene = true, centerOnScreen = true)
                } else if (CurrentUser.student != null) {
                    view.replaceWith<StudentView>(sizeToScene = true, centerOnScreen = true)
                } else if (CurrentUser.lecturer != null) {
                    view.replaceWith<LecturerView>(sizeToScene = true, centerOnScreen = true)
                } else {

                }
            }
        }
    }
}

object CurrentUser : Component(){

    val userProperty = SimpleObjectProperty<UserJson>()
    var user by userProperty

    val studentProperty = SimpleObjectProperty<StudentJson?>()
    var student by studentProperty

    val lecturerProperty = SimpleObjectProperty<LecturerJson?>()
    var lecturer by lecturerProperty
}

object Response : Component() {
    val api: Rest by inject()

    inline fun responseAsync(path: String, code: Int, vararg db: Any): MessageJson {
        return api.post(path, message(code, db.toList()))
                .consume()
                .one()
                .toModel()

    }
    inline fun responseAsync(path: String, code: Int, vararg db: Any, fOk: MessageJson.() -> Unit) {
        api.post(path, message(code, db.toList()))
                .consume()
                .one()
                .toModel<MessageJson>()
                .fOk()
    }
}

fun main(args: Array<String>) {
    launch<DiaryRatings>(args)
}
