package ua.funtik.ratings.controller

import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import ua.funtik.ratings.checkResponse
import ua.funtik.ratings.view.LoginView
import ua.funtik.ratings.view.StudentView

class LoginController : Controller() {
    val api: Rest by inject()

    val model = ViewModel()
    val emailProperty = model.bind { SimpleStringProperty() }
    val passwordProperty = model.bind { SimpleStringProperty() }
    val messagesProperty = SimpleStringProperty()

    val isLogin: BooleanProperty = SimpleBooleanProperty(false)

    fun login() {
        checkResponse(api.get("login/${emailProperty.value}/${passwordProperty.value}"), messagesProperty) {
            currentUser.user = one().toModel()
            if (currentUser.user == null) {
                messagesProperty.value = "Errors, not login"
                return
            }
            with(currentUser) {
                checkResponse(api.post("student", user.id), messagesProperty) {
                    currentUser.student = consume().one().toModel()
                }
                checkResponse(api.post("lecturer", user.id), messagesProperty) {
                    currentUser.lecturer = consume().one().toModel()
                }
                if (student?.id != null && lecturer?.id != null) {
                    // показать диалог выбора типа пользователя
                    // или сделать в этом окне ????
                } else if (student?.id != null) {
                    find<LoginView>().replaceWith<StudentView>(sizeToScene = true, centerOnScreen = true)
                } else if (lecturer?.id != null) {

                } else {  /*  показать окно с макс возможностями, но учитывать права пользователя */
                }
                isLogin.value = true
            }
        }

    }
}

// POST запрос
//val r = api.post("student", it)
//if (r.ok()) currentUser.student = r.consume().one().toModel()
