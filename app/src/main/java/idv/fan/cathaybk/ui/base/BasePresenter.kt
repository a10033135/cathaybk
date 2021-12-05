package idv.fan.cathaybk.ui.base

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<V : BaseContract.View> : BaseContract.Presenter<V> {

    protected var view: V? = null

    @JvmField
    protected var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun attachView(view: V) {
        this.view = view
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }

}