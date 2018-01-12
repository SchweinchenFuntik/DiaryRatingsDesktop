package ua.funtik.ratings.view

import tornadofx.*
import ua.funtik.ratings.margin
import ua.funtik.ratings.marginFull
import ua.funtik.ratings.padding

class AdminView : View("User panel") {


    override val root = borderpane {
        padding(5)
        left = listview<String> {
            marginFull(5)
            label("asd")
            label("asd")

        }
        center = tableview<String> {
            margin(top = 5, right = 5, bottom = 5)
        }
        right = LecturerEditForm()
        bottom = hbox {
            marginFull(7)
            spacing = 10.0
            button("Вставить")
            button("Удалить")
//            button("Изменить")
            button("Занести в БД")
            button("Обновить из БД")
        }
    }
}