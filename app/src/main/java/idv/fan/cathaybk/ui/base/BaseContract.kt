package idv.fan.cathaybk.ui.base

import androidx.fragment.app.FragmentActivity

interface BaseContract {

    interface View {
        fun getFragmentNavigation(): BaseFragment.FragmentNavigationListener
        fun getFragmentActivity(): FragmentActivity?
        fun getTAG(): String
    }

    interface Presenter<V : View> {
        fun attachView(view: V)
        fun subscribe()
        fun unsubscribe()
    }
}