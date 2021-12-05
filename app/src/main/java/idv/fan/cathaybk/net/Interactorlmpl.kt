package idv.fan.cathaybk.net

import idv.fan.cathaybk.model.User
import idv.fan.cathaybk.model.UserDetail
import io.reactivex.Flowable
import kotlin.math.sin

class Interactorlmpl : UserInteractor {

    /**
     * @param per_page 每頁顯示幾個 user 資料
     * @param since 從第幾個 user 開始擷取資料
     * */
    override fun getUsers(per_page: Int, since: Int): Flowable<List<User>> {
        return ApiService.instance.getApiService(UserInterface::class.java)
            .getUsers(per_page, since)
    }

    /**
     * @param userlogin 使用 users 內的 login 作為索引參數
     * */
    override fun getUserDetail(userLogin: String): Flowable<UserDetail> {
        return ApiService.instance.getApiService(UserInterface::class.java)
            .getUserDetail(userLogin)
    }

}