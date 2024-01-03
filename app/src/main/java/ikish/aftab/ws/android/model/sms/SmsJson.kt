package ikish.aftab.ws.android.model.sms

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SmsJson(
    @SerializedName("mobile")
    @Expose
    private var mobile: String? = null,

    @SerializedName("uuid")
    @Expose
    private var uuid: String? = null,

    @SerializedName("token")
    @Expose
    private var token: String? = null
) {


    fun getMobile(): String? {
        return mobile
    }

    fun setMobile(mobile: String?) {
        this.mobile = mobile
    }

    fun getUuid(): String? {
        return uuid
    }

    fun setUuid(uuid: String?) {
        this.uuid = uuid
    }


    fun getToken(): String? {
        return token
    }

    fun setToken(token: String?) {
        this.token = token
    }


}