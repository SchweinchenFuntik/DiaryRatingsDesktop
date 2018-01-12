package ua.funtik.ratings.model

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections.observableList
import javafx.collections.ObservableList
import tornadofx.*
import java.time.LocalDate
import javax.json.JsonArray
import javax.json.JsonObject

class GroupJson : JsonModel{
    var id: JsonObject? = null
        private set

    val nameProperty = SimpleStringProperty()
    var name by nameProperty

    val departamentProperty = SimpleStringProperty()
    var departament by departamentProperty

    val facultProperty = SimpleStringProperty()
    var facult by facultProperty

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("id", id)
            add("name", name)
            add("departament", departament)
            add("facult", facult)
        }
    }

    override fun updateModel(json: JsonObject) {
        with(json) {
            id = jsonObject("id")
            name = string("name")
            departament = string("departament")
            facult = string("facult")
        }
    }

    override fun toString(): String {
        return "GroupJson(id=$id, nameProperty=$nameProperty, departamentProperty=$departamentProperty, facultProperty=$facultProperty)"
    }

}

class SubjectJson : JsonModel{
    var id: JsonObject? = null
        private set

    val fullNameProperty = SimpleStringProperty()
    var fullName by fullNameProperty

    val simpleNameProperty = SimpleStringProperty()
    var simpleName by simpleNameProperty

    var groups: ObservableList<GroupJson> = observableList(emptyList())
        private set

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("id", id)
            add("fullName", fullName)
            add("simpleName", simpleName)
            add("groups", groups)
        }
    }

    override fun updateModel(json: JsonObject) {
        with(json) {
            id = jsonObject("id")
            fullName = string("fullName")
            simpleName = string("simpleName")
            jsonArray("groups")?.let {
                groups = it.toModel()
            }
        }
    }

    override fun toString(): String {
        return simpleName
    }
}

class UserJson : JsonModel {

    var id: JsonObject? = null
        private set

    val fioProperty = SimpleStringProperty()
    var fio by fioProperty

    val emailProperty = SimpleStringProperty()
    var email by emailProperty

    val phoneProperty = SimpleIntegerProperty()
    var phone: Int? by phoneProperty

    val accessProperty = SimpleIntegerProperty()
    var access: Int? by accessProperty

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("id", id)
            add("fio", fio)
            add("email", email)
            add("phone", phone)
        }
    }

    override fun updateModel(json: JsonObject) {
        with(json) {
            id = jsonObject("id")
            fio = string("fio")
            email = string("email")
            phone = int("phone")
            access = int("access")
        }
    }

    override fun toString(): String {
        return fio ?: "ERROR. None FIO"
    }
}

class StudentJson : JsonModel {
    var id: JsonObject? = null
        private set

    val userProperty = SimpleObjectProperty<UserJson>()
    var user by userProperty

    val groupProperty = SimpleObjectProperty<GroupJson>()
    var group by groupProperty

    val yearStartTrainingProperty = SimpleObjectProperty<LocalDate>()
    var yearStartTraining by yearStartTrainingProperty

    val yearEndTrainingProperty = SimpleObjectProperty<LocalDate>()
    var yearEndTraining by yearEndTrainingProperty

    val courseProperty = SimpleIntegerProperty()
    var course by courseProperty

    val butgetProperty = SimpleBooleanProperty()
    var butget by butgetProperty

    override fun toJSON(json: JsonBuilder) {
        with(json){
            add("id", id)
            add("user", user)
            add("group", group)
            add("yearStartTraining", yearStartTraining)
            add("yearEndTraining", yearEndTraining)
            add("course", course)
            add("butget", butget)
        }
    }

    override fun updateModel(json: JsonObject) {
        with(json){
            id = jsonObject("id")
            jsonObject("user")?.let { user = it.toModel() }
            jsonObject("group")?.let { group = it.toModel() }
            yearStartTraining = date("yearStartTraining")
            yearEndTraining = date("yearEndTraining")
            int("course")?.let { course = it }
            boolean("butget")?.let{ butget = it }
        }
    }

    override fun toString(): String {
        return user?.toString() ?: "ERROR. None FIO"
    }

}

class LecturerJson : JsonModel {
    var id: JsonObject? = null
        private set

    val userProperty = SimpleObjectProperty<UserJson>()
    var user by userProperty

    var subjects: ObservableList<SubjectJson> = observableList(mutableListOf())
        private set

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("id", id)
            add("user", user)
            add("subject", subjects)
        }
    }

    override fun updateModel(json: JsonObject) {
        with(json) {
            id = jsonObject("id")
            jsonObject("user")?.let { user = it.toModel() }
            jsonArray("groups")?.let {
                subjects = it.toModel()
            }
        }
    }

    override fun toString(): String {
        return user?.toString() ?: "ERROR. None FIO"
    }
}

class RatingJson : JsonModel{
    var id: JsonObject? = null
        private set

    val dateProperty = SimpleObjectProperty<LocalDate>()
    var date by dateProperty

    val valueProperty = SimpleIntegerProperty()
    var value: Int? by valueProperty

    val studentProperty = SimpleObjectProperty<StudentJson>()
    var student by studentProperty

    val lecturerProperty = SimpleObjectProperty<LecturerJson>()
    var lecturer by studentProperty

    val subjectProperty = SimpleObjectProperty<SubjectJson>()
    var subject by subjectProperty

    val typeProperty = SimpleIntegerProperty()
    var type by typeProperty

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("id", id)
            add("date", date)
            add("value", value)
            add("student", student)
            add("lecturer", lecturer)
            add("subject", subject)
            add("type", type)
        }
    }

    override fun updateModel(json: JsonObject) {
        with(json) {
            id = jsonObject("id")
            value = int("value")
            date = date("date")
            jsonObject("student")?.let { student = it.toModel() }
            jsonObject("lecturer")?.let { lecturer = it.toModel() }
            jsonObject("subject")?.let { subject = it.toModel() }
            type = int("type") ?: 0
        }
    }
}

class ServerResponseJson : JsonModel {

    val codeProperty = SimpleIntegerProperty()
    var code by codeProperty

    val responseProperty = SimpleObjectProperty<JsonArray>()
    var response by responseProperty

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("code", code)
            add("response", response)
        }
    }

    override fun updateModel(json: JsonObject) {
        with(json) {
            code = int("code")!!
            response = jsonArray("response")
        }
    }
}

class MessageJson : JsonModel {
    val codeProperty = SimpleIntegerProperty(ua.funtik.ratings.api.ERROR)
    var code by codeProperty

    val responseProperty = SimpleObjectProperty<JsonArray>()
    var response by responseProperty

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("code", code)
            add("response", response)
        }
    }

    override fun updateModel(json: JsonObject) {
        with(json) {
            int("code")?.let { code = it }
            response = jsonArray("response")
        }
    }
}