package ua.funtik.ratings.view

import javafx.beans.property.SimpleObjectProperty
import tornadofx.*
import ua.funtik.ratings.CurrentUser
import ua.funtik.ratings.UserType
import kotlin.system.exitProcess

class SelectedUserTypeView : View("Select user type") {
    val selectUserType = SimpleObjectProperty<UserType>()
    val list = mutableListOf<UserType>()

    init {
        with(CurrentUser) {
            if(student != null ) list += UserType.Student
            if(lecturer != null ) list += UserType.Lecturer
        }
        selectUserType.value = list[0]
    }

    override val root = vbox {
        hbox {
            label("Тип пользователя: ")
            combobox(selectUserType, list)
        }
        button("Выбрать").action {
            when(selectUserType.value){
                UserType.Student -> replaceWith<StudentView>(sizeToScene = true, centerOnScreen = true)
                UserType.Lecturer -> replaceWith<LecturerView>(sizeToScene = true, centerOnScreen = true)
                else -> exitProcess(0)
            }
        }
    }
}