package idv.fan.cathaybk.model

data class UserDetail(
    val avatar_url: String?,
    val blog: String?,
    val id: Int,
    val location: String?,
    val login: String?,
    val name: String?,
    val site_admin: Boolean,
    val bio: String?
)