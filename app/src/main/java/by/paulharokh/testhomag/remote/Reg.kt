package by.paulharokh.testhomag.remote

data class Reg(
    val NAME: String,
    val PERSONAL_GENDER: String,
    val PERSONAL_BIRTHDAY: String,
    val PERSONAL_MOBILE: String,
    val EMAIL: String,
    val WORK_COMPANY: String,
    val UF_SERVICE_NUMBER: String,
    val LOGIN: String,
    val PASSWORD: String,
    val CONFIRM_PASSWORD: String
)

data class RegAnswer(
    var USER_ID: String,
    var MESSAGE: String,
    var STATUS: Boolean
)