package idv.fan.cathaybk.net

import idv.fan.cathaybk.model.User
import idv.fan.cathaybk.model.UserDetail
import io.reactivex.Flowable

interface UserInteractor {

    fun getUsers(per_page: Int, since: Int): Flowable<List<User>>

    fun getUserDetail(userLogin: String): Flowable<UserDetail>
}