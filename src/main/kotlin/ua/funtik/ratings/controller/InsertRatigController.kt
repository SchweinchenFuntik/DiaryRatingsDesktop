package ua.funtik.ratings.controller

import tornadofx.*
import ua.funtik.ratings.model.RatingModel

class InsertRatigController : Controller() {
    val api: Rest by inject()

    val data: LecturerController by inject()

    val ratingsModel: RatingModel by inject()



}