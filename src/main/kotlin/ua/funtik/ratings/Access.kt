package ua.funtik.ratings

import ua.funtik.ratings.model.UserJson

private var id = 0
private val listAccess = enumValues<AccessType>()

enum class AccessType {
    FULL, INSERT, SELECT, SELECT_CURRENT, UPDATE, UPDATE_CURRENT, REMOVE, WRITE, WRITE_CURRENT, READ, READ_CURRENT;

    val code: Int = 1 shl id++
}

fun isAccess(access: Int, checkValue: AccessType) = access and checkValue.code == checkValue.code
fun listAccess(access: Int) = listAccess.filter { isAccess(access, it) }

val UserJson.accessList: List<AccessType>
    get() = listAccess(this.access ?: defaultAccessType())

fun defaultAccessType() = AccessType.SELECT.code + AccessType.UPDATE_CURRENT.code
