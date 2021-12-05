package idv.fan.cathaybk

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ncapdevi.fragnav.FragNavController
import com.socks.library.KLog
import idv.fan.cathaybk.ui.userlist.UserListFragment
import idv.fan.cathaybk.ui.base.BaseFragment
import idv.fan.cathaybk.ui.userlist.UserListPresenter

class MainActivity : AppCompatActivity(), BaseFragment.FragmentNavigationListener {

    private val TAG = MainActivity::class.java.simpleName

    private val mFragNavController: FragNavController by lazy {
        FragNavController(
            supportFragmentManager,
            R.id.container
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        KLog.init(true)
        initFragNavController(savedInstanceState)
    }

    override fun onBackPressed() {
        KLog.i(TAG, "onBackPressed")
        if (!mFragNavController.isRootFragment) {
            mFragNavController.popFragment()
        } else {
            KLog.i(TAG, "isRootFragment")
            super.onBackPressed()
        }
    }

    private fun initFragNavController(savedInstanceState: Bundle?) {
        val userListFragment = UserListFragment()
        userListFragment.presenter = UserListPresenter()
        mFragNavController.rootFragments = listOf(userListFragment)
        mFragNavController.initialize(FragNavController.TAB1, savedInstanceState)
    }

    override fun getCurrentFragment(): Fragment? {
        mFragNavController.currentDialogFrag?.let { currentFrag ->
            KLog.i(TAG, "getCurrentFragment: ${currentFrag::class.java.simpleName}")
        }
        return mFragNavController.currentFrag
    }

    override fun pushFragment(fragment: Fragment) {
        KLog.i(TAG, "pushFragment: ${fragment::class.java.simpleName}")
        mFragNavController.pushFragment(fragment)
    }

    override fun popFragment() {
        KLog.i(TAG, "popFragment")
        mFragNavController.popFragment()
    }
}