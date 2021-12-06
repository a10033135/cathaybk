package idv.fan.cathaybk.net

import android.text.TextUtils
import java.util.*
import kotlin.collections.HashMap

object Utils {

    fun getTokenHeader(token: String): HashMap<String, String> {
        val headers: HashMap<String, String> = hashMapOf()
        headers["Accept"] = "application/vnd.github.v3+json"
        headers["Authorization"] = "token $token"
        return headers
    }

}