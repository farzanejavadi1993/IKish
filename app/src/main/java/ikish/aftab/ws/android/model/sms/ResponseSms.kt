package ikish.aftab.ws.android.model.sms

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class ResponseSms(
    @SerializedName("isRegistered")
    @Expose
    private var isRegistered: Boolean? = null,

    @SerializedName("sent_sms (ONLYSHOWINDEMO_FOROTP)")
    @Expose
    private var sentSmsONLYSHOWINDEMOFOROTP: Int? = null,

    @SerializedName("captha")
    @Expose
    private var captha: Boolean? = null
) {


    fun getIsRegistered(): Boolean? {
        return isRegistered
    }

    fun setIsRegistered(isRegistered: Boolean?) {
        this.isRegistered = isRegistered
    }

    fun getSentSmsONLYSHOWINDEMOFOROTP(): Int? {
        return sentSmsONLYSHOWINDEMOFOROTP
    }

    fun setSentSmsONLYSHOWINDEMOFOROTP(sentSmsONLYSHOWINDEMOFOROTP: Int?) {
        this.sentSmsONLYSHOWINDEMOFOROTP = sentSmsONLYSHOWINDEMOFOROTP
    }

    fun getCaptha(): Boolean? {
        return captha
    }

    fun setCaptha(captha: Boolean?) {
        this.captha = captha
    }
}