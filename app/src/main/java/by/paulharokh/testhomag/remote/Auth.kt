package by.paulharokh.testhomag.remote

data class Auth(
    val login: String,
    val password: String
)

data class AuthAnswer(
    val status: Boolean,
    val id: String,
    val message: String
)