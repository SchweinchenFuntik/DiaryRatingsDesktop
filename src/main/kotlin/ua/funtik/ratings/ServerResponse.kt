package ua.funtik.ratings

const val OK = 0
const val Error = Int.MIN_VALUE


class ServerResponse(var code: Int, var response: List<Any>)