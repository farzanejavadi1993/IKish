package ikish.aftab.ws.android.model.invalidCode

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class Tokens(
    @SerializedName("access_token")
    @Expose
    private var accessToken: String? = null,

    @SerializedName("refresh_token")
    @Expose
    private var refreshToken: String? = null
) {


    fun getAccessToken(): String? {
        return accessToken
    }

    fun setAccessToken(accessToken: String?) {
        this.accessToken = accessToken
    }

    fun getRefreshToken(): String? {
        return refreshToken
    }

    fun setRefreshToken(refreshToken: String?) {
        this.refreshToken = refreshToken
    }

}