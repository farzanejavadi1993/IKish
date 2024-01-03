package ikish.aftab.ws.android.fetchData


import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ikish.aftab.ws.android.classes.retrofit.API
import ikish.aftab.ws.android.db.*
import ikish.aftab.ws.android.model.*
import ikish.aftab.ws.android.model.invalidCode.InvalidCodeJson
import ikish.aftab.ws.android.model.invalidCode.InvalidCodeModel
import ikish.aftab.ws.android.model.refreshToken.RefreshTokenJason
import ikish.aftab.ws.android.model.refreshToken.RefreshTokenModel
import ikish.aftab.ws.android.model.sms.ResponseSms
import ikish.aftab.ws.android.model.sms.SmsJson
import ikish.aftab.ws.android.model.sms.SmsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.inject.Inject


class Repository @Inject constructor(var dao: TodoDao,var api: API) {



    fun getAllPassenger(): MutableList<Passenger> = dao.getAllPassenger()
    fun getPassengerById(idc: String): Passenger = dao.findPassengerById(idc)
    fun getMyPassenger(CN: String): Passenger = dao.findMyPassenger(CN)
    fun insertSuperviser(psg: Passenger) = dao.insertPassenger(psg)
    fun deletePassenger(psg: Passenger) = dao.deletePassenger(psg)

    fun updatePassenger(psg: Passenger) = dao.updatePassenger(psg)


    fun insertResidence(residence: Residence) = dao.insertResidence(residence)
    fun getAllResidence(): List<Residence> = dao.getAllResidence()
    fun getResidence(id: String): Residence = dao.findResidenceByName(id)
    fun getResidenceById(id: Int): Residence = dao.findResidenceById(id)
    fun updateResidence(rst: Residence) = dao.updateResidence(rst)

    fun getReserveResidence(id: String): ReserveResidence = dao.findReserveResidenceById(id)
    fun insertReserveResidence(rvr: ReserveResidence) = dao.insertReserveResidence(rvr)
    fun getAllReserVeResidence(): LiveData<List<ReserveResidence>> = dao.getAllReserveResidence()

    fun insertSuperviser(svs: Supervisers) = dao.insertSuperViser(svs)
    fun getAllSuperViser(id: String): List<Supervisers> = dao.getAllSuperViser(id)
    fun getSuperViser(id: String): LiveData<Supervisers> = dao.findSuperViserById(id)


    fun insertTransaction(trs: Transactions) = dao.insertTransactions(trs)
    fun getAllTransaction(): List<Transactions> = dao.getAllTransactions()

    fun getAllLocations(): List<Locations> = dao.getAllLocations()
    fun insertLocation(lct: Locations) = dao.insertLocation(lct)
    fun getAllRecentlyLocations(nr: String): List<Locations> = dao.getAllRecentlyLocations(nr)
    fun getMyLocation(name: String): LiveData<Locations> = dao.findLocationById(name)
    fun updateLocation(lct: Locations) = dao.updateLocation(lct)
    fun updateLocationRecently(tid: Int, nr: String) = dao.updateLocationRecently(tid, nr)


    fun insertDetailFlight(df: DetailFlight) = dao.insertDetailFlight(df)
    fun findDetailFlightById(NO: String) = dao.findDetailFlightById(NO)
    fun getAllDetailFlight() = dao.getAllDetailFlight()


    fun insertPassengerFlight(psgF: PassengerFlight) = dao.insertPassengerFlight(psgF)


    fun getAllPassengerFlight(f: String): List<PassengerFlight> = dao.getAllPassengerFlight(f)
    fun deletePassengerFLight(f: String) = dao.deletePassengerFLight(f)
    fun deleteResidence(T: String) = dao.deleteResidence(T)


    fun insertBill(bl: Bill) = dao.insertBill(bl)
    fun getAllBill(): LiveData<List<Bill>> = dao.getAllBill()
    fun getAllFlightTime(): LiveData<List<FlightTimeModel>> {
        val flightTimeModel = FlightTimeModel(
            dayWeek = "دوشنبه",
            NAMEMounth = "اردیبهشت",
            NumberMounth = "12"
        )
        val flightTimeModel1 = FlightTimeModel(
            dayWeek = "سه شنبه",
            NAMEMounth = "اردیبهشت",
            NumberMounth = "13"
        )
        val flightTimeModel2 = FlightTimeModel(
            dayWeek = "چهارشنبه",
            NAMEMounth = "اردیبهشت",
            NumberMounth = "14"
        )
        val flightTimeModel3 = FlightTimeModel(
            dayWeek = "پنجشنبه",
            NAMEMounth = "اردیبهشت",
            NumberMounth = "15"
        )
        val flightTimeModel4 = FlightTimeModel(
            dayWeek = "جمعه",
            NAMEMounth = "اردیبهشت",
            NumberMounth = "16"
        )
        val list: MutableList<FlightTimeModel> =
            mutableListOf()        // or, use `arrayListOf`
        list.add(flightTimeModel)
        list.add(flightTimeModel1)
        list.add(flightTimeModel2)
        list.add(flightTimeModel3)
        list.add(flightTimeModel4)


        var mutableList: MutableLiveData<List<FlightTimeModel>> = MutableLiveData()
        mutableList.value = list!!

        return mutableList
    }


    fun getAllEventTime(): LiveData<List<FlightTimeModel>> {
        val eventTimeModel = FlightTimeModel(
            dayWeek = "1400",
            NAMEMounth = "",
            NumberMounth = "فروردین"
        )
        val eventTimeModel1 = FlightTimeModel(
            dayWeek = "1400",
            NAMEMounth = "",
            NumberMounth = "اردیبهشت"
        )
        val eventTimeModel2 = FlightTimeModel(
            dayWeek = "1400",
            NAMEMounth = "",
            NumberMounth = "خرداد"
        )
        val eventTimeModel3 = FlightTimeModel(
            dayWeek = "1400",
            NAMEMounth = "",
            NumberMounth = "تیر"
        )
        val eventTimeModel4 = FlightTimeModel(
            dayWeek = "1400",
            NAMEMounth = "",
            NumberMounth = "مرداد"
        )
        val list: MutableList<FlightTimeModel> =
            mutableListOf()        // or, use `arrayListOf`
        list.add(eventTimeModel)
        list.add(eventTimeModel1)
        list.add(eventTimeModel2)
        list.add(eventTimeModel3)
        list.add(eventTimeModel4)


        var mutableList: MutableLiveData<List<FlightTimeModel>> = MutableLiveData()
        mutableList.value = list!!

        return mutableList
    }


    fun getAllEvent(): LiveData<List<EventModel>> {
        val eventModel = EventModel(
            title = "شروع جشنواره بهاره کیش",
            description = "از ۸ اردیبهشت تا ۱۵ اردیبهشت",
            day = "چهارشنبه",
            date = "08"
        )

        val eventModel1 = EventModel(
            title = "شروع جشنواره بهاره کیش",
            description = "از ۸ اردیبهشت تا ۱۵ اردیبهشت",
            day = "چهارشنبه",
            date = "12"
        )


        val eventModel2 = EventModel(
            title = "شروع جشنواره بهاره کیش",
            description = "از ۸ اردیبهشت تا ۱۵ اردیبهشت",
            day = "چهارشنبه",
            date = "18"
        )

        val eventModel3 = EventModel(
            title = "شروع جشنواره بهاره کیش",
            description = "از ۸ اردیبهشت تا ۱۵ اردیبهشت",
            day = "چهارشنبه",
            date = "20"
        )

        val list: MutableList<EventModel> =
            mutableListOf()        // or, use `arrayListOf`
        list.add(eventModel)
        list.add(eventModel1)
        list.add(eventModel2)
        list.add(eventModel3)


        var mutableList: MutableLiveData<List<EventModel>> = MutableLiveData()
        mutableList.value = list!!

        return mutableList
    }


    fun getAllFlight(): List<FlightModel> {

        val flightModel = FlightModel(
            OriginTime = "16:00",
            DestinationTime = "18:00",
            OriginName = "مشهد",
            Time = "2 ساعت",
            CompanyName = "ایران ایر / Airbus 300-700",
            NumberFlight = "12031",
            DestinationName = "کیش",
            Price = "430،000 تومان",
            MemberPassenger = "12 نفر",
            Type = "اکونومی",
            url = 0,
            AirportOrigin = "فرودگاه هاشمی نژاد مشهد",
            AirportDestination = "فرودگاه بین المللی کیش",
            OldPrice = "430000",
            YoungPrice = "330000",
            BabyPrice = "120000",
        )
        val flightMode2 = FlightModel(
            OriginTime = "12:30",
            DestinationTime = "16:00",
            OriginName = "تهران",
            Time = "3 ساعت و 30 دقیقه",
            CompanyName = "ایران ایر / Airbus 300-700",
            NumberFlight = "85637",
            Price = "780،000 تومان",
            DestinationName = "اهواز",
            MemberPassenger = "22 نفر",
            Type = "اکونومی",
            url = 0,
            AirportOrigin = "فرودگاه امام خمینی ",
            AirportDestination = "فرودگاه اهواز",
            OldPrice = "780000",
            YoungPrice = "650000",
            BabyPrice = "300000",
        )
        val flightMode3 = FlightModel(
            OriginTime = "9:00",
            DestinationTime = "13:40",
            OriginName = "شیراز",
            Time = "4 ساعت و 40 دقیقه",
            CompanyName = "ایران ایر / Airbus 300-700",
            NumberFlight = "78523",
            Price = "900،000 تومان",
            DestinationName = "ساری",
            MemberPassenger = "6 نفر",
            Type = "اکونومی",
            url = 0,
            AirportOrigin = "فرودگاه شیراز ",
            AirportDestination = "فرودگاه ساری",
            OldPrice = "900000",
            YoungPrice = "750000",
            BabyPrice = "420000",
        )
        val flightMode4 = FlightModel(
            OriginTime = "8:30",
            DestinationTime = "11:00",
            OriginName = "مشهد",
            Time = "2 ساعت و 30 دقیقه",
            CompanyName = "ایران ایر / Airbus 300-700",
            NumberFlight = "78965",
            Price = "700،000 تومان",
            DestinationName = "تهران",
            MemberPassenger = "6 نفر",
            Type = "اکونومی",
            url = 0,
            AirportOrigin = "فرودگاه شهید هاشمی نژاد ",
            AirportDestination = "فرودگاه مهر آباد",
            OldPrice = "700000",
            YoungPrice = "600000",
            BabyPrice = "250000",
        )
        val flightMode5 = FlightModel(
            OriginTime = "19:00",
            DestinationTime = "20:40",
            OriginName = "ارومیه",
            Time = "2 ساعت و 40 دقیقه",
            CompanyName = "ایران ایر / Airbus 300-700",
            NumberFlight = "85963",
            Price = "600،000 تومان",
            DestinationName = "سنندج",
            MemberPassenger = "6 نفر",
            Type = "اکونومی",
            url = 0,
            AirportOrigin = "فرودگاه ارومیه ",
            AirportDestination = "فرودگاه سنندج",
            OldPrice = "600000",
            YoungPrice = "550000",
            BabyPrice = "220000",
        )
        val list: MutableList<FlightModel> =
            mutableListOf()        // or, use `arrayListOf`
        list.add(flightModel)
        list.add(flightMode2)
        list.add(flightMode3)
        list.add(flightMode4)
        list.add(flightMode5)


        var mutableList: List<FlightModel> = ArrayList()
        mutableList = list!!

        return mutableList
    }


    fun getAllOffer(): LiveData<List<OfferModel>> {

        val offerModel = OfferModel(
            title = "تخفیف ویژه در پاساژ پارس کیش",
            description = "تا ۵۰٪ تخفیف در تمامی پاساژ همراه با قرعه کشی روزانه یک دستگاه ۲۰۶ "
        )
        val offerModel1 = OfferModel(
            title = "تخفیف ویژه در پاساژ پارس کیش",
            description = "تا ۵۰٪ تخفیف در تمامی پاساژ همراه با قرعه کشی روزانه یک دستگاه ۲۰۶ "
        )
        val list: MutableList<OfferModel> =
            mutableListOf(offerModel)
        list.add(offerModel)
        list.add(offerModel1)


        var mutableList: MutableLiveData<List<OfferModel>> = MutableLiveData()
        mutableList.value = list!!



        return mutableList
    }


    fun getAllTourKish(): MutableLiveData<List<TourKishModel>> {


        //clear offerList
        val tourKishModel = TourKishModel(
            title = "ساحل زیبای مرجانی",
            description = "تور خانوادگی سه ستاره و اقتصادی همراه با برنامه\u200Cهای دیدنی… "
        )
        val tourKishModel1 = TourKishModel(
            title = "ساحل زیبای مرجانی",
            description = "تور خانوادگی سه ستاره و اقتصادی همراه با برنامه\u200Cهای دیدنی… "
        )
        val tourKishModel2 = TourKishModel(
            title = "ساحل زیبای مرجانی",
            description = "تور خانوادگی سه ستاره و اقتصادی همراه با برنامه\u200Cهای دیدنی… "
        )
        val list: MutableList<TourKishModel> =
            mutableListOf(tourKishModel)        // or, use `arrayListOf`
        list.add(tourKishModel)
        list.add(tourKishModel1)
        list.add(tourKishModel2)
        //add offerList

        var mutableList: MutableLiveData<List<TourKishModel>> = MutableLiveData()
        mutableList.value = list!!



        return mutableList
    }

    fun getAllLocationss(): MutableLiveData<List<Locations>> {


        //clear offerList
        val location1 = Locations(
            1,
            "مشهد",
            "1"
        )
        val location2 = Locations(
            1,
            "تهران",
            ""
        )
        val location3 = Locations(
            1,
            "شیراز",
            ""
        )
        val location4 = Locations(
            1,
            "ساری",
            ""
        )
        val location5 = Locations(
            1,
            "رشت",
            ""
        )


        val list: MutableList<Locations> =
            mutableListOf(location1)
        list.add(location2)
        list.add(location3)
        list.add(location4)
        list.add(location5)
        //add offerList

        var mutableList: MutableLiveData<List<Locations>> = MutableLiveData()
        mutableList.value = list!!



        return mutableList
    }

    fun getAllQuestion(): MutableLiveData<List<QuestionModel>> {


        //clear offerList
        val questionModel = QuestionModel(
            title = "سوال شماره یک سوال یک شماره یک",
            description = "شما هنوز هیچ مسافری ثبت نکرده\u200Cاید. برای شروع روی دکمه ثبت مسافر بزنید. شما هنوز هیچ مسافری ثبت نکرده\u200Cاید. برای شروع روی دکمه ثبت مسافر بزنید. شما هنوز هیچ مسافری ثبت نکرده\u200Cاید. برای شروع روی دکمه ثبت مسافر بزنید."
        )
        val questionModel1 = QuestionModel(
            title = "سوال شماره یک سوال یک شماره یک",
            description = "شما هنوز هیچ مسافری ثبت نکرده\u200Cاید. برای شروع روی دکمه ثبت مسافر بزنید. شما هنوز هیچ مسافری ثبت نکرده\u200Cاید. برای شروع روی دکمه ثبت مسافر بزنید. شما هنوز هیچ مسافری ثبت نکرده\u200Cاید. برای شروع روی دکمه ثبت مسافر بزنید."
        )
        val questionModel2 = QuestionModel(
            title = "سوال شماره یک سوال یک شماره یک",
            description = "شما هنوز هیچ مسافری ثبت نکرده\u200Cاید. برای شروع روی دکمه ثبت مسافر بزنید. شما هنوز هیچ مسافری ثبت نکرده\u200Cاید. برای شروع روی دکمه ثبت مسافر بزنید. شما هنوز هیچ مسافری ثبت نکرده\u200Cاید. برای شروع روی دکمه ثبت مسافر بزنید."
        )
        val list: MutableList<QuestionModel> =
            mutableListOf(questionModel)        // or, use `arrayListOf`
        list.add(questionModel)
        list.add(questionModel1)
        list.add(questionModel2)
        //add offerList

        var mutableList: MutableLiveData<List<QuestionModel>> = MutableLiveData()
        mutableList.value = list!!



        return mutableList
    }


    /* fun getResultSms(): LiveData<SmsModel> {

         var smsModel:LiveData<SmsModel>?=null


        val call: Call<LiveData<SmsModel>> =
             App.api.getResultSms("09348491215", "0b91b4f6e4f997cb3df86a5c8d2fd40c")

         call.enqueue(object : Callback<LiveData<SmsModel>?> {
             override fun onResponse(call: Call<LiveData<SmsModel>?>, response: Response<LiveData<SmsModel>?>) {
                 val gson = Gson()
                  smsModel = response.body()!!


             }

             override fun onFailure(call: Call<LiveData<SmsModel>?>, t: Throwable) {

                 var p=0
             }
         })



         return smsModel!!


     }*/

    /* fun getResultSms(mobile:String ,uuid:String): Call<LiveData<SmsModel>> {

         var smsModel:Call<LiveData<SmsModel>>?=api.getResultSms(mobile,uuid)

         return smsModel!!


     }*/

    fun getResultSms(mobile: String, uuid: String, token: String): MutableLiveData<SmsModel?>? {
         val smsJson = SmsJson()
         smsJson.setMobile(mobile)
         smsJson.setUuid(uuid)
         smsJson.setToken(token)


        var md5: String? = md5("Send_SMS_OTP/" + uuid + "0.0.0.0.1")
        var url = "send-SMS/" + md5
        val mutableLiveData: MutableLiveData<SmsModel?> =
            MutableLiveData<SmsModel?>()

        val call: Call<SmsModel> = api.getResultSms(smsJson, url)
        call.enqueue(object : Callback<SmsModel?> {
            override fun onResponse(call: Call<SmsModel?>, response: Response<SmsModel?>) {
                if (response.raw().code==200){
                    mutableLiveData.setValue(response.body())
                }else if (response.raw().code==403){
                    mutableLiveData.setValue(null)
                }
               /* if (response.body() != null) {

                } else {
                    mutableLiveData.setValue(null)
                }*/
            }

            override fun onFailure(call: Call<SmsModel?>, t: Throwable) {

            }
        })
        return mutableLiveData
    }

    fun getResultInvalidCode(
        mobile: String,
        code: String,
        uuid: String,
        clientId: String,
        clientSecret: String,
        token: String
    ): MutableLiveData<InvalidCodeModel?>? {
          val invalidCodeJson = InvalidCodeJson()
          invalidCodeJson.setMobile(mobile)
          invalidCodeJson.setUuid(uuid)
          invalidCodeJson.setCode(code)
          invalidCodeJson.setClientId(clientId)
          invalidCodeJson.setClientSecret(clientSecret)
          invalidCodeJson.setToken(token)

        var md5: String? = md5("Sign_In_With_OTP/" + uuid + "0.0.0.0.1")
        var url = "signin-by-SMS/" + md5

        val mutableLiveData: MutableLiveData<InvalidCodeModel?> =
            MutableLiveData<InvalidCodeModel?>()

        val call: Call<InvalidCodeModel> =
            api.getResultInvalidCode(invalidCodeJson, url)
        call.enqueue(object : Callback<InvalidCodeModel?> {
            override fun onResponse(
                call: Call<InvalidCodeModel?>,
                response: Response<InvalidCodeModel?>
            ) {
                if (response.body() != null) {
                    mutableLiveData.setValue(response.body())
                }else{
                    mutableLiveData.setValue(null)
                }
            }

            override fun onFailure(call: Call<InvalidCodeModel?>, t: Throwable) {
                mutableLiveData.setValue(null)
            }
        })
        return mutableLiveData
    }

    fun getResultRefreshToken(
        refreshToken: String,
        uuid: String,
        clientId: String,
        clientSecret: String
    ): MutableLiveData<RefreshTokenModel?>? {
        val refreshTokenJason = RefreshTokenJason()
        refreshTokenJason.setUuid(uuid)
        refreshTokenJason.setRefreshToken(refreshToken)
        refreshTokenJason.setClientId(clientId)
        refreshTokenJason.setClientSecret(clientSecret)


        var md5: String? = md5("RefreshToken/" + uuid + "0.0.0.0.1")
        var url = "reassign/" + md5

        val mutableLiveData: MutableLiveData<RefreshTokenModel?> =
            MutableLiveData<RefreshTokenModel?>()

        val call: Call<RefreshTokenModel> = api.getResultRefreshToken(refreshTokenJason, url)
        call.enqueue(object : Callback<RefreshTokenModel?> {
            override fun onResponse(
                call: Call<RefreshTokenModel?>,
                response: Response<RefreshTokenModel?>
            ) {
                if (response.body() != null) {
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<RefreshTokenModel?>, t: Throwable) {}
        })
        return mutableLiveData
    }


    fun md5(s: String): String? {
        val MD5 = "MD5"
        try {
            // Create MD5 Hash
            val digest: MessageDigest = MessageDigest
                .getInstance(MD5)
            digest.update(s.toByteArray())
            val messageDigest: ByteArray = digest.digest()

            // Create Hex String
            val hexString = StringBuilder()
            for (aMessageDigest in messageDigest) {
                var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                while (h.length < 2) h = "0$h"
                hexString.append(h)
            }
            return hexString.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ""
    }

}
