package idv.fan.cathaybk.ui.userdetail

import idv.fan.cathaybk.model.UserDetail
import idv.fan.cathaybk.model.UserInfo
import idv.fan.cathaybk.ui.base.BaseContract

interface UserDetailContract : BaseContract {
    interface View : BaseContract.View {
        fun setUserInfo(alUserInfo: List<UserInfo>)
        fun setUserDetail(userDetail: UserDetail)
        fun setLoadingVisibility(visibility: Int)
        fun showPopupDialog()
    }

    interface Presenter : BaseContract.Presenter<View> {}
}