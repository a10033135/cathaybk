package idv.fan.cathaybk.ui

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Warren on 16/5/16.
 */
abstract class EndlessRecyclerOnScrollListener(private val mLinearLayoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {
    private var previousTotal = 0 // The total number of items in the dataset after the last load
    private var loading = true // True if we are still waiting for the last set of data to load.
    private val visibleThreshold = 5 // The minimum amount of items to have below your current scroll position before loading more.
    var firstVisibleItem = 0
    var visibleItemCount = 0
    var totalItemCount = 0
    private var current_page = 1

    fun resetPage() {
        current_page = 1
        totalItemCount = mLinearLayoutManager.itemCount
        previousTotal = totalItemCount
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition()
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        visibleItemCount = recyclerView.childCount
        totalItemCount = mLinearLayoutManager.itemCount
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition()
        if (previousTotal > totalItemCount) {
            previousTotal = 0
        }
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false
                previousTotal = totalItemCount
            }
        }
        if (!loading && totalItemCount - visibleItemCount
            <= firstVisibleItem + visibleThreshold
        ) { // End has been reached
// Do something
            current_page++
            onLoadMore(current_page)
            loading = true
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            onScrollIdle(firstVisibleItem)
        } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
            onScrolling()
        }
    }

    abstract fun onLoadMore(page: Int)
    open fun onScrolling() {}
    open fun onScrollIdle(firstVisibleItem: Int) {}

}