package ua.funtik.ratings.controller

import javafx.collections.FXCollections.observableArrayList
import tornadofx.*
import ua.funtik.ratings.CurrentUser
import ua.funtik.ratings.model.RatingJson
import ua.funtik.ratings.model.RatingModel
import ua.funtik.ratings.model.SubjectJson
import ua.funtik.ratings.model.SubjectModel

class StudentController : Controller() {
    val api: Rest by inject()
    val modelSubject: SubjectModel by inject()
    val modelRatings: RatingModel by inject()
    val subjects = observableArrayList<SubjectJson>()
    val ratings = observableArrayList<RatingJson>()

    init {
//        CurrentUser.student?.let { api.subjects(it, subjects) }
    }

    fun selectSubject(subject: SubjectJson){
        ratings.setAll(ratings.filter { it.subject == subject })
    }

    fun update() {
        with(CurrentUser) {
//            checkResponse(api.post("test2", serverResponse(listOf(user, student!!)))) {
//                val v = consume().one()
//                val j = v.toModel<ServerResponseJson>()
//                if (j.code == OK) {
//                    val us = j.response?.toModel<UserJson>()
//                    us?.forEach { println(it.fio) }
//                }
//            }
        }
    }
}

