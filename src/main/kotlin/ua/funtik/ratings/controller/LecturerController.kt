package ua.funtik.ratings.controller

import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections.observableArrayList
import tornadofx.*
import ua.funtik.ratings.Cache
import ua.funtik.ratings.model.*
import ua.funtik.ratings.view.InsertRatingView

class LecturerController : Controller(){
    val api: Rest by inject()
    val data: Cache by inject()
    val insertDialog: InsertRatingView by inject()
    val ratingsModel: RatingModel by inject()

    val ratings = observableArrayList<RatingJson>()
    val groups = observableArrayList<GroupJson>()
    val subjects = observableArrayList<SubjectJson>()
    val students = observableArrayList<StudentJson>()

    val selectSubjectProperty = SimpleObjectProperty<SubjectJson>()
    var selectSubject by selectSubjectProperty
    val selectGroupProperty = SimpleObjectProperty<GroupJson>()
    var selectGroup by selectGroupProperty
    val selectStudentProperty = SimpleObjectProperty<StudentJson>()
    var selectStudent by selectStudentProperty

    init {
        selectSubjectProperty.addListener { _, old, new ->
            new?.let { subject ->
                takeIf { old != subject }.run {
                    val list = data.groups.filter { subject.groups.contains(it) }
                    groups.setAll(list)
                }
                return@addListener
            }
            groups.clear()
        }
        selectGroupProperty.addListener { _, old, new ->
            new?.let { group ->
                takeIf { old != group }.run {
                    students.clear()
                    ratings.setAll(data.ratings.filter { it?.subject == selectSubject && it?.student?.group == group })
                    ratings.forEach { students += it.student }
                }
                return@addListener
            }
            ratings.clear()
        }
    }

    fun isSelected() = (selectGroup != null && selectSubject != null)

    fun showInsertRatings() {
        ratingsModel.selectModel(RatingJson())
        insertDialog.openModal(resizable = false, escapeClosesWindow = true)
    }

    fun update() = data.updateLecturer()

    fun edit(){
        insertDialog.openModal(resizable = false, escapeClosesWindow = true)
    }
}