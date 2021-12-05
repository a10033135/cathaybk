package idv.fan.cathaybk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ncapdevi.fragnav.FragNavController
import com.socks.library.KLog
import idv.fan.cathaybk.ui.ListFragment
import idv.fan.cathaybk.ui.base.BaseFragment

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
        initFragNavController(savedInstanceState)
    }

    private fun initFragNavController(savedInstanceState: Bundle?) {
        mFragNavController.rootFragments = listOf(ListFragment())
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
    }

    override fun popFragment() {
        KLog.i(TAG, "popFragment")
        mFragNavController.popFragment()
    }
}