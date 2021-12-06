package idv.fan.cathaybk.ui.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.socks.library.KLog
import idv.fan.cathaybk.R
import idv.fan.cathaybk.model.User
import idv.fan.cathaybk.ui.EndlessRecyclerOnScrollListener
import idv.fan.cathaybk.ui.base.BaseFragment
import idv.fan.cathaybk.ui.userdetail.UserDetailFragment
import idv.fan.cathaybk.ui.userdetail.UserDetailPresenter
import kotlinx.android.synthetic.main.fragment_list.*

class UserListFragment : BaseFragment<UserListContract.View, UserListContract.Presenter>(),
    UserListContract.View {

    private var mAdapter: UserListAdapter? = null
    private val mUserListener = object : UserListAdapter.UserListListener {
        override fun onUserClick(userLogin: String) {
            KLog.i(getTAG(), "onUserClick.userLogin: ")
            val fragment = UserDetailFragment()
            val presenter = UserDetailPresenter(userLogin)
            fragment.presenter = presenter
            mFragmentNavigationListener.pushFragment(fragment)
        }
    }

    private var mEndlessOnScrollListener: EndlessRecyclerOnScrollListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        presenter?.subscribe()
    }

    private fun initView() {
        val linearLayoutManager = LinearLayoutManager(activity)
        rv_user_list?.layoutManager = linearLayoutManager
        if (mAdapter == null) {
            mAdapter = UserListAdapter()
            mAdapter?.setListener(mUserListener)
        }
        rv_user_list?.adapter = mAdapter

        if (mEndlessOnScrollListener == null) {
            mEndlessOnScrollListener = object : EndlessRecyclerOnScrollListener(linearLayoutManager) {
                override fun onLoadMore(page: Int) {
                    presenter?.onLoadMore(page)
                }
            }
        }
        rv_user_list?.addOnScrollListener(mEndlessOnScrollListener!!)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        rv_user_list?.clearOnScrollListeners()
        mAdapter = null
    }

    override fun setUserList(alList: List<User>) {
        mAdapter?.setUserList(alList)
    }

    override fun setErrorMsgVisibility(visibility: Int) {
        tv_error?.visibility = visibility
    }

    override fun setLoadingVisibility(visibility: Int) {
        pb_loading?.visibility = visibility
    }

    override fun setListVisibility(visibility: Int) {
        rv_user_list?.visibility = visibility
    }

    override fun showToast(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

    override fun getTAG(): String {
        return UserListFragment::class.java.simpleName
    }
}