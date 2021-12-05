package idv.fan.cathaybk.ui.userdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.socks.library.KLog
import idv.fan.cathaybk.R
import idv.fan.cathaybk.model.UserInfo

class UserInfoAdapter : RecyclerView.Adapter<UserInfoAdapter.UserInfoViewHolder>() {

    private var mAlUserInfo: List<UserInfo> = listOf()
    private var TAG = UserInfoAdapter::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserInfoViewHolder {
        val itemview =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_user_list, parent, false)
        return UserInfoViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: UserInfoViewHolder, position: Int) {
        mAlUserInfo.getOrNull(position)?.let { userBean ->
            val context = holder.itemView.context
            holder.tvUserName.text = userBean.title
            /* 由於目前 site_admin 都是 false ，因此改為 userBean.id 判斷 */
//            holder.tvIsSiteAdmin.visibility = if (userBean.site_admin) View.VISIBLE else View.GONE
            holder.tvIsSiteAdmin.visibility = if (userBean.badge.isNotEmpty()) View.VISIBLE else View.GONE
            holder.tvIsSiteAdmin.text = userBean.badge
            holder.ivUser.setImageDrawable(ContextCompat.getDrawable(context, userBean.imgRes))
        }

    }

    override fun getItemCount(): Int {
        return mAlUserInfo.size
    }

    fun setUserInfoList(alUserInfo: List<UserInfo>) {
        KLog.i(TAG, "user.size: ${alUserInfo.size}")
        mAlUserInfo = alUserInfo
        notifyDataSetChanged()
    }

    class UserInfoViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val tvUserName = itemview.findViewById<TextView>(R.id.tv_user_name)
        val tvIsSiteAdmin = itemview.findViewById<TextView>(R.id.tv_badge)
        val ivUser = itemview.findViewById<ImageView>(R.id.iv_user_img)
    }
}