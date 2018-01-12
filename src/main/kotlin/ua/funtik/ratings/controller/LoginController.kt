package ua.funtik.ratings.controller

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import ua.funtik.ratings.CurrentUser
import ua.funtik.ratings.UserType
import ua.funtik.ratings.api.*
import ua.funtik.ratings.model.MessageJson
import ua.funtik.ratings.responseAsync
import javax.json.JsonObject

class LoginController : Controller() {
    val api: Rest by inject()

    val model = ViewModel()
    val emailProperty = model.bind { SimpleStringProperty() }
    var email by emailProperty
    val passwordProperty = model.bind { SimpleStringProperty() }
    val password by passwordProperty
    val selectUserType = SimpleObjectProperty<UserType>(UserType.Auto)
    val messagesProperty = SimpleStringProperty()
    var msg: String by messagesProperty

    val isLogin = SimpleBooleanProperty(false)

    private val path = "login"
    private val fOk: MessageJson.() -> Unit = {
        response?.let {
            if (OK == code) {
                val sz = it.size
                if (sz > 1) CurrentUser.student = (it[1] as? JsonObject)?.toModel()
                if (sz > 2) CurrentUser.lecturer = (it[2] as? JsonObject)?.toModel()
                if (sz > 0) CurrentUser.user = (it[0] as? JsonObject)?.toModel()
            } else if (code == ERROR) msg = "Нет такого пользователя или не правильный пароль."
        }
    }

    fun loginA() {
        when (selectUserType.value!!) {
            UserType.Student -> api.responseAsync(path, STUDENT, email, password, fOk = fOk)
            UserType.Lecturer -> api.responseAsync(path, NOT_LECTURER, email, password, fOk = fOk)
            UserType.Auto -> api.responseAsync(path, LOGIN, email, password, fOk = fOk)
        }
    }
}

//val r = api.post("student", it)
//if (r.ok()) CurrentUser.student = r.consume().one().toModel()
