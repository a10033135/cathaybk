package idv.fan.cathaybk.ui.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import idv.fan.cathaybk.R
import idv.fan.cathaybk.model.User
import idv.fan.cathaybk.ui.base.BaseContract

interface UserListContract : BaseContract {
    interface View : BaseContract.View {
        fun setUserList(alList: List<User>)
        fun setErrorMsgVisibility(visibility: Int)
        fun setLoadingVisibility(visibility: Int)
        fun setListVisibility(visibility: Int)
        fun showToast(msg: String)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun onLoadMore(page: Int)
    }
}