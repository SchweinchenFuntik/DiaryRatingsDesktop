package ua.funtik.ratings.view

import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.control.SelectionMode
import tornadofx.*
import ua.funtik.ratings.controller.StudentController

class StudentView : View("Student") {
    val controller: StudentController by inject()

    override val root = borderpane {

        left = listview(controller.subjects) {
            margin(10)

            isEditable = false

            selectionModel.selectionMode = SelectionMode.SINGLE

            bindSelected(controller.model)

            onUserSelect{
                center = cache { label(it.simpleName) }
            }
        }

//        center =

        bottom = hbox{
            marginFull(10)

            button("get"){
                action{
                    runLater { controller.get() }
                }
            }
        }
    }

    override val savable  = SimpleBooleanProperty(false)
    override val deletable = SimpleBooleanProperty(false)


}