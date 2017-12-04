package ua.funtik.ratings.model

import tornadofx.*

infix fun <T> ItemViewModel<T>.selectModel(t: T){
    this.item = t
}

open class UserModel(userJson: UserJson?) : ItemViewModel<UserJson>(){
    constructor() : this(null)

    val fio = bind(UserJson::fioProperty)
    val email = bind(UserJson::emailProperty)
    val phone = bind(UserJson::phoneProperty)
}

class StudentModel(studentJson: StudentJson?) : ItemViewModel<StudentJson>(){
    constructor() : this(null)

    val yearStartTraining = bind(StudentJson::yearStartTrainingProperty)
    val yearEndTraining = bind(StudentJson::yearEndTrainingProperty)
    val butget = bind(StudentJson::butget)
}

class SubjectModel(subjectJson: SubjectJson?) : ItemViewModel<SubjectJson>(){
    constructor() : this(null)

    val fullName = bind(SubjectJson::fullNameProperty)
    val simpleName = bind(SubjectJson::simpleNameProperty)
    val groups = bind(SubjectJson::groups)
}