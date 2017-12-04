package ua.funtik.ratings.view

import tornadofx.*
import ua.funtik.ratings.controller.LoginController

class LoginView : View("Login") {
    val controller: LoginController by inject()

    init {
        primaryStage.isResizable = false
        controller.isLogin.addListener{ ob, old, newValue ->  primaryStage.isResizable = newValue }
    }

    override val root = form {
        fieldset {
            field("Email:") {
                textfield(controller.emailProperty).required()
            }
            field("Password:") {
                passwordfield(controller.passwordProperty).required()
            }
            field(forceLabelIndent = false) {
                label(controller.messagesProperty)
            }
            field {
                button("Login") {
                    enableWhen(controller.model.valid)
                    isDefaultButton = true
                    action {
                        runAsync { controller.login() }
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

