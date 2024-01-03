package ikish.aftab.ws.android.model.sms

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class SmsModel(

    @SerializedName("response")
    @Expose
    private var response: ResponseSms? = null,


) {


    fun getResponse(): ResponseSms? {
        return response
    }

    fun setResponse(response: ResponseSms?) {
        this.response = response
    }



}