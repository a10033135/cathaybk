package idv.fan.cathaybk.ui.userdetail

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import idv.fan.cathaybk.R
import idv.fan.cathaybk.model.UserDetail
import idv.fan.cathaybk.model.UserInfo
import idv.fan.cathaybk.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_detail.*

class UserDetailFragment :
    BaseFragment<UserDetailContract.View, UserDetailContract.Presenter>(), UserDetailContract.View {

    private var mUserInfoAdapter: UserInfoAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initview()
        presenter?.subscribe()
    }

    private fun initview() {
        iv_close?.setOnClickListener { mFragmentNavigationListener.popFragment() }
        rv_user_info?.layoutManager = LinearLayoutManager(activity)
        if (mUserInfoAdapter == null) {
            mUserInfoAdapter = UserInfoAdapter()
        }
        rv_user_info?.adapter = mUserInfoAdapter
    }

    override fun setUserInfo(alUserInfo: List<UserInfo>) {
        mUserInfoAdapter?.setUserInfoList(alUserInfo)
    }

    override fun setUserDetail(userDetail: UserDetail) {
        activity?.let { activity ->
            border_user_info.visibility = View.VISIBLE
            tv_user_name.visibility = if (userDetail.login.isNullOrEmpty()) View.GONE else View.VISIBLE
            tv_user_name.text = userDetail.login
            tv_user_bio.visibility = if (userDetail.bio.isNullOrEmpty()) View.GONE else View.VISIBLE
            tv_user_bio.text = userDetail.bio
            if (!userDetail.avatar_url.isNullOrEmpty()) {
                Glide.with(activity)
                    .load(userDetail.avatar_url)
                    .circleCrop()
                    .into(iv_user_img)
            }
        }
    }

    override fun setLoadingVisibility(visibility: Int) {
        pb_loading.visibility = visibility
    }

    override fun showPopupDialog() {
        activity?.let { activity ->
            val builder = AlertDialog.Builder(activity)
            builder.setCancelable(false)
            builder.setMessage(R.string.net_error)
            builder.setPositiveButton(R.string.btn_ok) { dialog, which ->
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    dialog.dismiss()
                    mFragmentNavigationListener.popFragment()
                }
            }
            builder.create().show()
        }
    }

    override fun getTAG(): String {
        return UserDetailFragment::class.java.simpleName
    }

}