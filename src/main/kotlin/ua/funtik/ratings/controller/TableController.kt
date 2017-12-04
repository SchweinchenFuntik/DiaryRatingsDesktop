package ua.funtik.ratings.controller

import tornadofx.*
import ua.funtik.ratings.model.UserJson
import javax.json.JsonObject

class TableController : Controller(){
    val api: Rest by inject()
    val users = api.get("users").list().toModel<UserJson>()
}