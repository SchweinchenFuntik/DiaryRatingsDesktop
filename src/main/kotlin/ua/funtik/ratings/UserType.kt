package ua.funtik.ratings

enum class UserType {
   Auto, Student, Lecturer;

   override fun toString(): String {
      return when(this){
         Auto -> "Авто"
         Student -> "Студент"
         Lecturer -> "Преподаватель"
      }
   }
}