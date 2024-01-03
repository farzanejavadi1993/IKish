package ikish.aftab.ws.android.util

import android.content.Context
import android.net.ConnectivityManager
import java.util.regex.Pattern

class Util {

    companion object {
        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
        fun toEnglishNumber(input: String): String? {
            var input = input
            val persian = arrayOf("۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹")
            val arabic = arrayOf("٠", "١", "٢", "٣", "٤", "٥", "٦", "٧", "٨", "٩")
            for (j in persian.indices) {
                if (input.contains(persian[j])) input = input.replace(persian[j], j.toString())
            }
            for (j in arabic.indices) {
                if (input.contains(arabic[j])) input = input.replace(arabic[j], j.toString())
            }
            return input
        }

        fun isValid(s: String): Boolean {
            val p = Pattern.compile("(0/9)?[0-9]{11}")
            val m = p.matcher(s)
            return m.find() && m.group() == s
        }


        fun isValidCode(s: String): Boolean {
            val p = Pattern.compile("(0/9)?[0-9]{6}")
            val m = p.matcher(s)
            return m.find() && m.group() == s
        }
    }


}