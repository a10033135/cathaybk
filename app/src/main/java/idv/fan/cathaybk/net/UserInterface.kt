package idv.fan.cathaybk.net

import idv.fan.cathaybk.model.User
import idv.fan.cathaybk.model.UserDetail
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserInterface {

    @GET("users")
    fun getUsers(
        @Query("per_page") per_page: Int,
        @Query("since") since: Int
    ): Flowable<List<User>>

    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Flowable<UserDetail>
}