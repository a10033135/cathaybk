package idv.fan.cathaybk.net

import idv.fan.cathaybk.model.User
import idv.fan.cathaybk.model.UserDetail
import io.reactivex.Flowable
import kotlin.math.sin

class Interactorlmpl : UserInteractor {
    override fun getUsers(per_page: Int, since: Int): Flowable<List<User>> {
        return ApiService.instance.getApiService(UserInterface::class.java)
            .getUsers(per_page, since)
    }

    override fun getUserDetail(username: String): Flowable<UserDetail> {
        return ApiService.instance.getApiService(UserInterface::class.java)
            .getUserDetail("a10033135")
    }

}