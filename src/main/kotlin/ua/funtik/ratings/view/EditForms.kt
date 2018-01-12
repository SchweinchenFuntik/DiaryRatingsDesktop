package ua.funtik.ratings.view

import javafx.collections.FXCollections.observableArrayList
import javafx.geometry.Orientation
import javafx.scene.control.ListView
import tornadofx.*
import ua.funtik.ratings.margin
import ua.funtik.ratings.model.*

abstract class TableForm<out M : ItemViewModel<out JsonModel>>(val model: M) : Form() {

    init {
        isCache = true
        margin(top = 5, bottom = 5, left = 5)
    }

    protected fun endInit() {
        fieldset {
            buttonbar(forceLabelIndent = false) {
                button("Применить").setOnAction { commit() }
                button("Отмена").setOnAction { rollback() }
            }
        }
    }

    protected open fun commit() = model.commit()
    protected open fun rollback() = model.rollback()
}

class SubjectEditForm(
        objModel: SubjectJson? = null,
        model: SubjectModel = SubjectModel()
) : TableForm<SubjectModel>(model) {
    lateinit var listView: ListView<GroupJson>

    init {
        model.selectModel(objModel)
        fieldset("Предмет") {
            field("Аббревиатура:") { textfield().bind(model.simpleName) }
            field("Имя:") { textfield().bind(model.fullName) }
        }
        fieldset("Группы") {
            field { listview(model.groups) { listView = this } }
            field(orientation = Orientation.HORIZONTAL) {
                button("+").action {
                }
                button(" - ").action {
                    val tmp = listView.selectedItem ?: listView.items[0]
                    tmp?.let { model.groups.value = observableArrayList(model.groups.value - it) }
                }
            }
        }
        endInit()
    }
}

class GroupEditForm(
        objModel: GroupJson? = null,
        model: GroupModel = GroupModel()
) : TableForm<GroupModel>(model) {
    init {
        model.selectModel(objModel)
        fieldset("Группа") {
            field("Имя:") { textfield().bind(model.name) }
            field("Факультет:") { textfield().bind(model.facul) }
            field("Кафедра:") { textfield().bind(model.departament) }
        }
        endInit()
    }
}

class UserEditForm(
        objModel: UserJson? = null,
        model: UserModel = UserModel()
) : TableForm<UserModel>(model) {
    init {
        model.selectModel(objModel)
        fieldset("Пользователь") {
            field("ФИО:") { textfield().bind(model.fio) }
            field("Email:") { textfield().bind(model.email) }
            field("Телефон:") { textfield().bind(model.phone) }
        }
        endInit()
    }
}

class StudentEditForm(
        objModel: StudentJson? = null,
        model: StudentModel = StudentModel(),
        private val modelUser: UserModel = UserModel()
) : TableForm<StudentModel>(model) {

    init {
        model.selectModel(objModel)
        modelUser.selectModel(objModel?.user)
        fieldset("Студент") {
            field("ФИО:") { textfield().bind(modelUser.fio) }
            field("Email:") { textfield().bind(modelUser.email) }
            field("Телефон:") { textfield().bind(modelUser.phone) }
            field("Начало обучения:") { datepicker().bind(model.yearStartTraining) }
            field("Окончания обучения:") { datepicker().bind(model.yearEndTraining) }
            field("Форма обучения:") { checkbox("бюджетная").bind(model.butget) }
        }
        endInit()
    }

    override fun commit() = model.commit() && modelUser.commit()
    override fun rollback() {
        model.rollback()
        modelUser.rollback()
    }
}

class LecturerEditForm(
        objModel: LecturerJson? = null,
        model: LecturerModel = LecturerModel(),
        private val modelUser: UserModel = UserModel()
) : TableForm<LecturerModel>(model) {
    lateinit var listView: ListView<SubjectJson>

    init {
        model.selectModel(objModel)
        modelUser.selectModel(objModel?.user)
        fieldset("Лектор") {
            field("ФИО:") { textfield().bind(modelUser.fio) }
            field("Email:") { textfield().bind(modelUser.email) }
            field("Телефон:") { textfield().bind(modelUser.phone) }
        }
        fieldset("Предметы") {
            field { listview(model.subjects) { listView = this } }
            field(orientation = Orientation.HORIZONTAL) {
                button("+").action {
                }
                button(" - ").action {
                    val tmp = listView.selectedItem ?: listView.items[0]
                    tmp?.let { model.subjects.value = observableArrayList(model.subjects.value - it) }
                }
            }
        }
        endInit()
    }

    override fun commit() = model.commit() && modelUser.commit()
    override fun rollback() {
        model.rollback()
        modelUser.rollback()
    }
}

class RatingEditForm(
        objModel: RatingJson? = null,
        model: RatingModel = RatingModel()
) : TableForm<RatingModel>(model) {
    init {
        model.selectModel(objModel)
        fieldset("Оценка") {
            field("Оценка:") { textfield().bind(model.value) }
            field("Дата:") { datepicker().bind(model.date) }
            field("Предмет:") { label(model.subject) }
            field("Студент:") { label(model.student) }
            field("Лектор:") { label(model.lecturer) }
            field("Тип:") { textfield().bind(model.type) }
        }
        endInit()
    }
}