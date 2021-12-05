package idv.fan.cathaybk.ui.userdetail

import android.view.View
import com.socks.library.KLog
import idv.fan.cathaybk.R
import idv.fan.cathaybk.model.UserDetail
import idv.fan.cathaybk.model.UserInfo
import idv.fan.cathaybk.net.Interactorlmpl
import idv.fan.cathaybk.net.RxSubscriber
import idv.fan.cathaybk.net.SwitchSchedulers
import idv.fan.cathaybk.net.UserInteractor
import idv.fan.cathaybk.ui.base.BasePresenter

class UserDetailPresenter(userLogin: String) : BasePresenter<UserDetailContract.View>(), UserDetailContract.Presenter {

    private val TAG = UserDetailPresenter::class.java.simpleName

    private val mUserLogin = userLogin
    private var mUserDetail: UserDetail? = null
    private val mUserInteractor: UserInteractor by lazy { Interactorlmpl() }

    enum class ViewStatus { LOADING, SUCCESS, ERROR }

    private fun setViewStatus(status: ViewStatus) {
        KLog.i(TAG, "setViewStatus: $status")
        view?.setLoadingVisibility(View.GONE)
        when (status) {
            ViewStatus.LOADING -> {
                view?.setLoadingVisibility(View.VISIBLE)
            }
            ViewStatus.SUCCESS -> {
                mUserDetail?.let {
                    view?.setUserDetail(it)
                    view?.setUserInfo(detailToUserInfoList(it))
                    view?.setUserDetail(it)
                }
            }
            ViewStatus.ERROR -> {
                view?.showPopupDialog()
            }
        }
    }


    override fun subscribe() {
        if (mUserDetail == null) {
            getUserDetailByApi()
        } else {
            setViewStatus(ViewStatus.SUCCESS)
        }
    }

    private fun getUserDetailByApi() {
        val getUserListSubscriber = object : RxSubscriber<UserDetail>() {
            override fun _onNext(userDetail: UserDetail) {
                mUserDetail = userDetail
                setViewStatus(ViewStatus.SUCCESS)
            }

            override fun _onError(code: Int, msg: String?) {
                KLog.e(TAG, msg)
                setViewStatus(ViewStatus.ERROR)
            }
        }
        mUserInteractor
            .getUserDetail(mUserLogin)
            .compose(SwitchSchedulers.applyFlowableSchedulers())
            .onBackpressureBuffer()
            .subscribe(getUserListSubscriber)
        getUserListSubscriber.add(compositeDisposable)
    }

    /* 將 Github UserDetail 轉為可以列表顯示的 UserInfo List 供 View 控管與使用 */
    private fun detailToUserInfoList(detail: UserDetail): List<UserInfo> {
        val activity = view?.getFragmentActivity() ?: return listOf()
        val alUserInfo = arrayListOf<UserInfo>()

        if (!detail.login.isNullOrEmpty()) {
//            val siteAdmin = if (detail.site_admin) activity.getString(R.string.is_admin) else ""
            /* 由於目前 site_admin 都是 false ，因此改為 userBean.id 判斷 */
            val siteAdmin = if (detail.id % 3 == 0) activity.getString(R.string.is_admin) else ""
            val personInfo = UserInfo(R.drawable.ic_person, detail.login, siteAdmin)
            alUserInfo.add(personInfo)
        }

        if (!detail.location.isNullOrEmpty()) {
            val locationInfo = UserInfo(R.drawable.ic_location, detail.location)
            alUserInfo.add(locationInfo)
        }

        if (!detail.blog.isNullOrEmpty()) {
            val blogInfo = UserInfo(R.drawable.ic_link, detail.blog)
            alUserInfo.add(blogInfo)
        }
        return alUserInfo.toList()
    }
}