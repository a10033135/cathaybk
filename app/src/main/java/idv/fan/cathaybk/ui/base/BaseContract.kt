package idv.fan.cathaybk.ui.base

interface BaseContract {

    interface View {
        fun getFragmentNavigation(): BaseFragment.FragmentNavigationListener
    }

    interface Presenter<V : View> {
        fun attachView(view: V)
        fun subscribe()
        fun unsubscribe()
    }
}