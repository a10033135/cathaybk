package idv.fan.cathaybk.net

import idv.fan.cathaybk.model.User
import idv.fan.cathaybk.model.UserDetail
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserInterface {

    /**
     * @param per_page 每頁顯示幾個 user 資料
     * @param since 從第幾個 user 開始擷取資料
     * */
    @GET("users")
    fun getUsers(
        @Query("per_page") per_page: Int,
        @Query("since") since: Int
    ): Flowable<List<User>>

    /**
     * @param userlogin 使用 users 內的 login 作為索引參數
     * */
    @GET("users/{userlogin}")
    fun getUserDetail(
        @Path("userlogin") userlogin: String
    ): Flowable<UserDetail>
}