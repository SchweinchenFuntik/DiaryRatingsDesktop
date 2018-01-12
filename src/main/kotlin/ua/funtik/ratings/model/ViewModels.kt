package ua.funtik.ratings.model

import tornadofx.*

infix fun <T> ItemViewModel<T>.selectModel(t: T?){
    this.item = t
}

class UserModel : ItemViewModel<UserJson>(){
    val fio = bind(UserJson::fioProperty)
    val email = bind(UserJson::emailProperty)
    val phone = bind(UserJson::phoneProperty)
}

class StudentModel : ItemViewModel<StudentJson>(){
    val yearStartTraining = bind(StudentJson::yearStartTrainingProperty)
    val yearEndTraining = bind(StudentJson::yearEndTrainingProperty)
    val butget = bind(StudentJson::butget)
}

class LecturerModel : ItemViewModel<LecturerJson>() {
    val subjects = bind(LecturerJson::subjects)
}

class SubjectModel : ItemViewModel<SubjectJson>(){
    val fullName = bind(SubjectJson::fullNameProperty)
    val simpleName = bind(SubjectJson::simpleNameProperty)
    val groups = bind(SubjectJson::groups)
}

class GroupModel : ItemViewModel<GroupJson>() {
    val name = bind(GroupJson::nameProperty)
    val departament = bind(GroupJson::departamentProperty)
    val facul = bind(GroupJson::facultProperty)
}

class RatingModel : ItemViewModel<RatingJson>() {
    val date = bind(RatingJson::dateProperty)
    val value = bind(RatingJson::valueProperty)
    val student = bind(RatingJson::studentProperty)
    val subject = bind(RatingJson::subjectProperty)
    val lecturer = bind(RatingJson::lecturerProperty)
    val type = bind(RatingJson::typeProperty)
}