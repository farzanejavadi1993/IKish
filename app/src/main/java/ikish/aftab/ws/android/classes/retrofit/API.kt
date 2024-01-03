package ikish.aftab.ws.android.classes.retrofit

import ikish.aftab.ws.android.model.invalidCode.InvalidCodeJson
import ikish.aftab.ws.android.model.invalidCode.InvalidCodeModel
import ikish.aftab.ws.android.model.refreshToken.RefreshTokenJason
import ikish.aftab.ws.android.model.refreshToken.RefreshTokenModel
import ikish.aftab.ws.android.model.sms.SmsJson
import ikish.aftab.ws.android.model.sms.SmsModel
import retrofit2.Call
import retrofit2.http.*

interface API {


    /* @POST
     fun getResultSms(@Body() smsJason: SmsJson, @Url url: String): Call<SmsModel>*/

    /* @POST
     fun getResultInvalidCode(@Body() invalidCodeModel: InvalidCodeJson, @Url url: String): Call<InvalidCodeModel>*/


    @POST
    fun getResultSms(
        @Body() smsJason: SmsJson,
        @Url url: String
    ): Call<SmsModel>


    @POST
    fun getResultInvalidCode(
        @Body() invalidCodeModel: InvalidCodeJson,
        @Url url: String
    ): Call<InvalidCodeModel>


    @POST
    fun getResultRefreshToken(
        @Body() refreshTokenJason: RefreshTokenJason,
        @Url url: String
    ): Call<RefreshTokenModel>


}