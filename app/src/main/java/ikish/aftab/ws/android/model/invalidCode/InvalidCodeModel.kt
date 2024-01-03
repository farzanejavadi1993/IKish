package ikish.aftab.ws.android.model.invalidCode

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName



class InvalidCodeModel {
    @SerializedName("isSuccess")
    @Expose
    private var isSuccess: Boolean? = null

    @SerializedName("code")
    @Expose
    private var code: Int? = null

    @SerializedName("response")
    @Expose
    private var response: ResponseInvalidCode? = null

    @SerializedName("responseDate")
    @Expose
    private var responseDate: String? = null

    fun getIsSuccess(): Boolean? {
        return isSuccess
    }

    fun setIsSuccess(isSuccess: Boolean?) {
        this.isSuccess = isSuccess
    }

    fun getCode(): Int? {
        return code
    }

    fun setCode(code: Int?) {
        this.code = code
    }

    fun getResponse(): ResponseInvalidCode? {
        return response
    }

    fun setResponse(response: ResponseInvalidCode?) {
        this.response = response
    }

    fun getResponseDate(): String? {
        return responseDate
    }

    fun setResponseDate(responseDate: String?) {
        this.responseDate = responseDate
    }

}