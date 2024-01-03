package ikish.aftab.ws.android.model.invalidCode

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class ResponseInvalidCode(
    @SerializedName("user")
    @Expose
    private var user: User? = null,

    @SerializedName("tokens")
    @Expose
    private var tokens: Tokens? = null
) {



    fun getUser(): User? {
        return user
    }

    fun setUser(user: User?) {
        this.user = user
    }

    fun getTokens(): Tokens? {
        return tokens
    }

    fun setTokens(tokens: Tokens?) {
        this.tokens = tokens
    }
}