package ua.funtik.ratings.view

import tornadofx.*
import ua.funtik.ratings.UserType
import ua.funtik.ratings.controller.LoginController

class LoginView : View("Login") {
    val controller: LoginController by inject()

    init {
        primaryStage.isResizable = false
        controller.isLogin.addListener { _, _, newValue ->  primaryStage.isResizable = newValue }
    }

    override val root = form {

        fieldset {
            field("Email:") {
                textfield(controller.emailProperty).required()
            }
            field("Password:") {
                passwordfield(controller.passwordProperty).required()
            }
            field("Тип учетной записи:"){
                combobox(controller.selectUserType, UserType.values().toList())
            }
            field(forceLabelIndent = false) {
                label(controller.messagesProperty)
            }

            buttonbar(forceLabelIndent = true) {
                button("Login") {
                    enableWhen(controller.model.valid)
                    isDefaultButton = true
                    action {
                        runAsyncWithProgress { controller.loginA() }
                    }
                }
            }
        }
    }

    override fun onDock() {
        controller.passwordProperty.value = ""
        controller.model.clearDecorators()
    }
}
