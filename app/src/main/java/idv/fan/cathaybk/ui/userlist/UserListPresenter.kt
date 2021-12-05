package idv.fan.cathaybk.ui.userlist

import android.view.View
import com.socks.library.KLog
import idv.fan.cathaybk.model.User
import idv.fan.cathaybk.net.Interactorlmpl
import idv.fan.cathaybk.net.RxSubscriber
import idv.fan.cathaybk.net.SwitchSchedulers
import idv.fan.cathaybk.net.UserInteractor
import idv.fan.cathaybk.ui.base.BasePresenter

class UserListPresenter : BasePresenter<UserListContract.View>(), UserListContract.Presenter {

    private val TAG = UserListPresenter::class.java.simpleName
    private var mAlUserList = listOf<User>()
    private val mUserInteractor: UserInteractor by lazy { Interactorlmpl() }

    enum class ViewStatus { LOADING, SUCCESS, ERROR }

    private fun setViewStatus(status: ViewStatus) {
        KLog.i(TAG, "setViewStatus: $status")
        view?.setLoadingVisibility(View.GONE)
        view?.setErrorMsgVisibility(View.GONE)
        view?.setListVisibility(View.GONE)
        when (status) {
            ViewStatus.LOADING -> {
                view?.setLoadingVisibility(View.VISIBLE)
            }
            ViewStatus.SUCCESS -> {
                view?.setListVisibility(View.VISIBLE)
                view?.setUserList(mAlUserList)
            }
            ViewStatus.ERROR -> {
                view?.setErrorMsgVisibility(View.VISIBLE)
            }
        }
    }

    override fun subscribe() {
        if (mAlUserList.isEmpty()) {
            getUserListByApi()
        } else {
            setViewStatus(ViewStatus.SUCCESS)
        }
    }

    private fun getUserListByApi() {
        setViewStatus(ViewStatus.LOADING)
        val getUserListSubscriber = object : RxSubscriber<List<User>>() {
            override fun _onNext(list: List<User>?) {
                mAlUserList = list ?: listOf()
                setViewStatus(ViewStatus.SUCCESS)
            }

            override fun _onError(code: Int, msg: String?) {
                KLog.e(TAG, msg)
                setViewStatus(ViewStatus.ERROR)
            }

        }
        mUserInteractor
            .getUsers(20, 0)
            .compose(SwitchSchedulers.applyFlowableSchedulers())
            .onBackpressureBuffer()
            .subscribe(getUserListSubscriber)

        getUserListSubscriber.add(compositeDisposable)
    }

}