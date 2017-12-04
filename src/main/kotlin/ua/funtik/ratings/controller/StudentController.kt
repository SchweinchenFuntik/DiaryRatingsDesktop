package ua.funtik.ratings.controller

import javafx.collections.FXCollections.observableArrayList
import tornadofx.*
import ua.funtik.ratings.app.currentUser
import ua.funtik.ratings.checkResponse
import ua.funtik.ratings.model.ServerResponseJson
import ua.funtik.ratings.model.SubjectJson
import ua.funtik.ratings.model.SubjectModel
import ua.funtik.ratings.model.UserJson
import ua.funtik.ratings.serverResponse

class StudentController : Controller() {
    val api: Rest by inject()
    val model: SubjectModel by inject()
    val subjects = observableArrayList<SubjectJson>()

    init {
        currentUser.student?.let { api.subjects(it, subjects) }
    }

    fun get() {
        with(currentUser) {
            checkResponse(api.post("test2", serverResponse(listOf(user, student!!)))) {
                val v = consume().one()
                val j = v.toModel<ServerResponseJson>()
                if (j.code == OK) {
                    val us = j.response?.toModel<UserJson>()
                    us?.forEach { println(it.fio) }
                }
            }
        }
    }
}

