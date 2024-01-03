package ikish.aftab.ws.android.model.invalidCode

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class InvalidCodeJson(
    @SerializedName("mobile")
    @Expose
    private var mobile: String? = null,

    @SerializedName("code")
    @Expose
    private var code: String? = null,

    @SerializedName("uuid")
    @Expose
    private var uuid: String? = null,

    @SerializedName("clientId")
    @Expose
    private var clientId: String? = null,

    @SerializedName("clientSecret")
    @Expose
    private var clientSecret: String? = null,
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

    fun getCode(): String? {
        return code
    }

    fun setCode(code: String?) {
        this.code = code
    }

    fun getUuid(): String? {
        return uuid
    }

    fun setUuid(uuid: String?) {
        this.uuid = uuid
    }

    fun getClientId(): String? {
        return clientId
    }

    fun setClientId(clientId: String?) {
        this.clientId = clientId
    }

    fun getClientSecret(): String? {
        return clientSecret
    }

    fun setClientSecret(clientSecret: String?) {
        this.clientSecret = clientSecret
    }


    fun getToken(): String? {
        return token
    }

    fun setToken(token: String?) {
        this.token = token
    }
}