package ikish.aftab.ws.android.model.refreshToken

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class RefreshTokenJason(
    @SerializedName("uuid")
    @Expose
    private var uuid: String? = null,

    @SerializedName("refreshToken")
    @Expose
    private var refreshToken: String? = null,

    @SerializedName("clientId")
    @Expose
    private var clientId: String? = null,

    @SerializedName("clientSecret")
    @Expose
    private var clientSecret: String? = null
) {


    fun getUuid(): String? {
        return uuid
    }

    fun setUuid(uuid: String?) {
        this.uuid = uuid
    }

    fun getRefreshToken(): String? {
        return refreshToken
    }

    fun setRefreshToken(refreshToken: String?) {
        this.refreshToken = refreshToken
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
}