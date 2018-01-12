package ua.funtik.ratings.view

import javafx.scene.control.SelectionMode
import tornadofx.*
import ua.funtik.ratings.controller.LecturerController
import ua.funtik.ratings.margin
import ua.funtik.ratings.marginFull
import ua.funtik.ratings.model.RatingJson

class LecturerView : View("Lecturer") {
    val controller: LecturerController by inject()

    override val root = borderpane {
        left = vbox {
            marginFull(5)
            spacing = 7.0

            listview(controller.subjects) {
                selectionModel.selectionMode = SelectionMode.SINGLE
                controller.selectSubjectProperty.bind(selectionModel.selectedItemProperty())
            }
            listview(controller.groups) {
                selectionModel.selectionMode = SelectionMode.SINGLE
                controller.selectGroupProperty.bind(selectionModel.selectedItemProperty())
            }
        }

        center = tableview(controller.ratings) {
            margin(bottom = 5)
            selectionModel.selectionMode = SelectionMode.SINGLE

            column("Оценка", RatingJson::valueProperty)
            column("Дата", RatingJson::dateProperty)
            column("Студент", RatingJson::studentProperty)
            column("Тип", RatingJson::typeProperty)

            bindSelected(controller.ratingsModel)
        }

        bottom = hbox {
            marginFull(10)
            spacing = 10.0

            button("Поставить оценку").action {
                controller.showInsertRatings()
            }
            button("Обновить").action {
                 runAsyncWithProgress { controller.update() }
            }
            button("Редактировать").action {
//                if (controller.isSelected())
                    controller.edit()
            }
        }
    }
}
