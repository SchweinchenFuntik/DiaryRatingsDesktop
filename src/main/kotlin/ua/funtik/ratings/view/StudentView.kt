package ua.funtik.ratings.view

import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.control.SelectionMode
import tornadofx.*
import ua.funtik.ratings.controller.StudentController
import ua.funtik.ratings.margin
import ua.funtik.ratings.marginFull
import ua.funtik.ratings.model.RatingJson

class StudentView : View("Student") {
    val controller: StudentController by inject()

    override val root = borderpane {

        left = listview(controller.subjects) {
            margin(10)

            isEditable = false

            selectionModel.selectionMode = SelectionMode.SINGLE

            bindSelected(controller.modelSubject)

            onUserSelect {
                controller.selectSubject(it)
                // center = cache { label(it.simpleName) }
            }
        }

        center = tableview(controller.ratings) {
            selectionModel.selectionMode = SelectionMode.SINGLE

            column("Оценка", RatingJson::valueProperty)
            column("Дата", RatingJson::dateProperty)
            column("Тип", RatingJson::typeProperty)
            column("Преподаватель", RatingJson::lecturerProperty)

        }

        bottom = hbox {
            marginFull(10)

            button("Update").action {
                runAsyncWithProgress { controller.update() }
            }
        }
    }

    override val savable = SimpleBooleanProperty(false)
    override val deletable = SimpleBooleanProperty(false)


}