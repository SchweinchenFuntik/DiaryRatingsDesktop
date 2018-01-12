package ua.funtik.ratings.view

import tornadofx.*
import ua.funtik.ratings.controller.LecturerController

class InsertRatingView : View("Добавить оценку") {
    val controller: LecturerController by inject()

    override val root = form {
        fieldset("Оценка") {

            field("Оценка:") {
                textfield().bind(controller.ratingsModel.value)
            }
            field("Дата:") {
                datepicker().bind(controller.ratingsModel.date)
            }
            field("Предмет:") {
                combobox(controller.selectSubjectProperty, controller.subjects)
            }
            field("Тип:") {
                textfield().bind(controller.ratingsModel.type)
            }
        }
        fieldset("Студент") {
            field("Группа:") {
                combobox(controller.selectGroupProperty, controller.groups)
            }
            field("ФИО:") {
                combobox(controller.selectStudentProperty, controller.students)
            }
        }
        fieldset {
            buttonbar(forceLabelIndent = false) {
                button("Поставить").action { controller.ratingsModel.commit() }
                button("Отмена").action {
                    controller.ratingsModel.rollback()
                    close()
                }
            }
        }
    }
}