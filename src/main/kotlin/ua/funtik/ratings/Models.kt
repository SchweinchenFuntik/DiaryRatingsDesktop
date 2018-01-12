package ua.funtik.ratings

import tornadofx.*
import ua.funtik.ratings.model.*

object Models : Component() {
    val groupModel: GroupModel by inject()
    val userModel: UserModel by inject()
    val subjectModel: SubjectModel by inject()
    val studentModel: StudentModel by inject()
    val lecturerModel: LecturerModel by inject()
    val ratingModel: RatingModel by inject()
}