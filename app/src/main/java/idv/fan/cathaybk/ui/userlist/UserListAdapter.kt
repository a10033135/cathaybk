package idv.fan.cathaybk.ui.userlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding.view.RxView
import com.socks.library.KLog
import idv.fan.cathaybk.R
import idv.fan.cathaybk.model.User

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    private var mAlUser: List<User> = listOf()
    private var TAG = UserListAdapter::class.java.simpleName
    private var mListener: UserListListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val itemview =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_user_list, parent, false)
        return UserListViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        mAlUser.getOrNull(position)?.let { userBean ->
            val context = holder.itemView.context
            RxView.clicks(holder.itemView).subscribe {
                mListener?.onUserClick(userBean)
            }
            holder.tvUserName.text = userBean.login
            /* 由於目前 site_admin 都是 false ，因此改為 userBean.id 判斷 */
//            holder.tvIsSiteAdmin.visibility = if (userBean.site_admin) View.VISIBLE else View.GONE
            holder.tvIsSiteAdmin.visibility = if (userBean.id % 3 == 0) View.VISIBLE else View.GONE
            Glide.with(context)
                .load(userBean.avatar_url)
                .circleCrop()
                .into(holder.ivUser)
        }

    }

    override fun getItemCount(): Int {
        return mAlUser.size
    }

    fun setUserList(alUser: List<User>) {
        KLog.i(TAG, "user.size: ${alUser.size}")
        mAlUser = alUser
        notifyDataSetChanged()
    }

    fun setListener(listener: UserListListener) {
        KLog.i(TAG, "setListener")
        mListener = listener
    }

    class UserListViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val tvUserName = itemview.findViewById<TextView>(R.id.tv_user_name)
        val tvIsSiteAdmin = itemview.findViewById<TextView>(R.id.tv_badge)
        val ivUser = itemview.findViewById<ImageView>(R.id.iv_user_img)
    }

    interface UserListListener {
        fun onUserClick(user: User)
    }
}