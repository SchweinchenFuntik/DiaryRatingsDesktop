package ua.funtik.ratings.view

import tornadofx.*
import ua.funtik.ratings.controller.TableController
import ua.funtik.ratings.margin
import ua.funtik.ratings.marginFull
import ua.funtik.ratings.model.UserJson
import ua.funtik.ratings.model.UserModel
import ua.funtik.ratings.padding

class MainView : View("Hello TornadoFX") {
    val controller: TableController by inject()
    var tableModel: TableViewEditModel<UserJson> by singleAssign()
    val userModelModel: UserModel by inject()

    val loginView: LoginView by inject()

    override val root = borderpane {
        padding(7)

        center = tableview(controller.users) {
            margin(20 ,1, left = 5)

            isEditable = true

            column("FIO", UserJson::fioProperty).makeEditable()
            column("Email", UserJson::emailProperty).makeEditable()
            column("Phone", UserJson::phoneProperty).makeEditable()

            enableCellEditing()
            enableDirtyTracking()

            bindSelected(userModelModel)

            tableModel = editModel
        }

        bottom = hbox(10) {
            marginFull(10)

            button("Commit").action {
                    tableModel.items.asSequence()
                            .filter { it.value.isDirty }
                            .forEach { it.value.commit() }
            }
            button("Rollback").action {
                    tableModel.items.asSequence()
                            .filter { it.value.isDirty }
                            .forEach { it.value.rollback() }
            }

            button("Login").action{ loginView.openModal() }
        }
    }
}