package ikish.aftab.ws.android.fetchData

import android.app.Application
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import ikish.aftab.ws.android.db.*
import ikish.aftab.ws.android.model.*
import ikish.aftab.ws.android.model.invalidCode.InvalidCodeModel
import ikish.aftab.ws.android.model.refreshToken.RefreshTokenModel
import ikish.aftab.ws.android.model.sms.SmsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(private val repository: Repository, application: Application)  : AndroidViewModel(
    application
) {
    private var allPassenger: MutableList<Passenger>? = null
    private var allResidence: List<Residence>? = null
    private var allSuperViser: List<Supervisers>? = null
    private var allTransactions: List<Transactions>? = null
    private var allReserveResidence: LiveData<List<ReserveResidence>>? = null
    private var allBill: LiveData<List<Bill>>? = null
    private var allLocations: List<Locations>? = ArrayList()
    private var allLocationss: MutableLiveData<List<Locations>>? = null
    private var allRecentlyLocation: List<Locations>? = ArrayList()
    private var allPassengerFlight: List<PassengerFlight>? = null
    private var allFlightTime: LiveData<List<FlightTimeModel>>? = null
    private var allEvent: LiveData<List<EventModel>>? = null
    private var allFlight: List<FlightModel>? = null
    private var allDetailFlight: LiveData<List<DetailFlight>>? = null
    private var allOfferList: LiveData<List<OfferModel>>? = null
    private var allTourKish: LiveData<List<TourKishModel>>? = null
    private var allQuestion: LiveData<List<QuestionModel>>? = null


    private var passenger: Passenger? = null
    private var passengerr: LiveData<Passenger>? = null
    private var residence: Residence? = null
    private var reserveResidence: ReserveResidence? = null
    private var superViser: LiveData<Supervisers>? = null
    private var myLocation: LiveData<Locations>? = null
    private var detailFlight: LiveData<DetailFlight>? = null
    private var smsModel: MutableLiveData<SmsModel?>? = null
    private var invalidCodeModel: MutableLiveData<InvalidCodeModel?>? = null
    private var refreshTokenModel: MutableLiveData<RefreshTokenModel?>? = null


//    private lateinit var resultSms:LiveData<SmsModel>


    /*
      init {
            val dao = RoomDataBase.getDatabase(application.applicationContext)!!.getDao()
            repository = Repository(dao)


        }
    */

    fun getALLPassenger(): MutableList<Passenger>? {
        allPassenger = repository!!.getAllPassenger()
        return allPassenger
    }


    fun getAllResidence(): List<Residence>? {
        allResidence = repository!!.getAllResidence()
        return allResidence
    }

    fun getAllSuperViser(id: String): List<Supervisers>? {
        allSuperViser = repository!!.getAllSuperViser(id)
        return allSuperViser
    }

    fun getAllTransactions(): List<Transactions>? {
        allTransactions = repository!!.getAllTransaction()
        return allTransactions
    }

    fun getALLReserveResidence(): LiveData<List<ReserveResidence>>? {
        allReserveResidence = repository!!.getAllReserVeResidence()
        return allReserveResidence
    }

    fun getAllLocations(): List<Locations>? {

        allLocations = repository.getAllLocations()
        return allLocations
    }

    fun getAllRecentlyLocations(nr: String): List<Locations>? {

        allRecentlyLocation = repository!!.getAllRecentlyLocations(nr)
        return allRecentlyLocation
    }


    fun getAllPassengerFlight(f: String): List<PassengerFlight>? {
        allPassengerFlight = repository!!.getAllPassengerFlight(f)
        return allPassengerFlight
    }

    fun getAllBill(): LiveData<List<Bill>>? {
        allBill = repository!!.getAllBill()
        return allBill
    }

    fun getPassenger(idc: String): Passenger? {
        passenger = repository!!.getPassengerById(idc)
        return passenger
    }

    fun getMyPassenger(CN: String): Passenger? {
        passenger = repository!!.getMyPassenger(CN)
        return passenger
    }

    fun getResidence(id: String): Residence? {
        residence = repository!!.getResidence(id)
        return residence
    }

    fun getResidenceById(id: Int): Residence? {
        residence = repository!!.getResidenceById(id)
        return residence
    }

    fun getReserveResidence(id: String): ReserveResidence? {
        reserveResidence = repository!!.getReserveResidence(id)
        return reserveResidence
    }

    fun getSuperViser(id: String): LiveData<Supervisers>? {
        superViser = repository!!.getSuperViser(id)
        return superViser
    }

    fun getMyLocation(name: String): LiveData<Locations>? {
        myLocation = repository!!.getMyLocation(name)
        return myLocation
    }


    fun getDetailFlight(no: String): LiveData<DetailFlight>? {
        detailFlight = repository!!.findDetailFlightById(no)
        return detailFlight
    }


    fun deletePassenger(psg: Passenger) {
        viewModelScope.launch(Dispatchers.IO) {
            repository!!.deletePassenger(psg)
        }

    }

    fun insertPassenger(psg: Passenger) {
        viewModelScope.launch(Dispatchers.IO) {
            repository!!.insertSuperviser(psg)
        }
    }

    fun insertSuperViser(svs: Supervisers) {
        viewModelScope.launch(Dispatchers.IO) {
            repository!!.insertSuperviser(svs)
        }
    }

    fun insertReserveResidence(rvr: ReserveResidence) {
        viewModelScope.launch(Dispatchers.IO) {
            repository!!.insertReserveResidence(rvr)
        }
    }

    fun insertTransaction(trs: Transactions) {
        viewModelScope.launch(Dispatchers.IO) {
            repository!!.insertTransaction(trs)
        }
    }

    fun insertDetailFlight(df: DetailFlight) {
        viewModelScope.launch(Dispatchers.IO) {
            repository!!.insertDetailFlight(df)
        }
    }

    fun insertPassengerFlight(psf: PassengerFlight) {
        viewModelScope.launch(Dispatchers.IO) {
            repository!!.insertPassengerFlight(psf)
        }
    }


    fun insertBill(bl: Bill) {
        viewModelScope.launch(Dispatchers.IO) {
            repository!!.insertBill(bl)
        }
    }

    fun deletePassengerFlight(f: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository!!.deletePassengerFLight(f)
        }

    }

    fun deleteResidence(T: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository!!.deleteResidence(T)
        }

    }


    fun insertLocation(lct: Locations) {
        viewModelScope.launch(Dispatchers.IO) {
            repository!!.insertLocation(lct)
        }
    }


    fun insertResidence(residence: Residence) {
        viewModelScope.launch(Dispatchers.IO) {
            repository!!.insertResidence(residence)
        }
    }


    fun updatePassenger(psg: Passenger) {
        viewModelScope.launch(Dispatchers.IO) {
            repository!!.updatePassenger(psg)
        }
    }


    fun updateResidence(rst: Residence) {
        viewModelScope.launch(Dispatchers.IO) {
            repository!!.updateResidence(rst)
        }
    }

    fun updateLocations(lct: Locations) {
        viewModelScope.launch(Dispatchers.IO) {
            repository!!.updateLocation(lct)
        }
    }


    fun updateLocationRecently(tid: Int, nr: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository!!.updateLocationRecently(tid, nr)
        }
    }

    fun getAllFlightTime(): LiveData<List<FlightTimeModel>>? {
        allFlightTime = repository!!.getAllFlightTime()
        return allFlightTime
    }

    fun getAllEvent(): LiveData<List<EventModel>>? {
        allEvent = repository!!.getAllEvent()
        return allEvent
    }


    fun getAllEventTime(): LiveData<List<FlightTimeModel>>? {
        allFlightTime = repository!!.getAllEventTime()
        return allFlightTime
    }


    fun getAllFlight(): List<FlightModel>? {
        allFlight = repository!!.getAllFlight()
        return allFlight
    }

    fun getAllDetailFlight(): LiveData<List<DetailFlight>>? {
        allDetailFlight = repository!!.getAllDetailFlight()
        return allDetailFlight
    }


    fun getAllOfferList(): LiveData<List<OfferModel>>? {
        allOfferList = repository!!.getAllOffer()
        return allOfferList
    }

    fun getAllTourKish(): LiveData<List<TourKishModel>>? {
        allTourKish = repository!!.getAllTourKish()
        return allTourKish
    }

    fun getAllQuestion(): LiveData<List<QuestionModel>>? {
        allQuestion = repository!!.getAllQuestion()

        return allQuestion
    }

    fun getAllLocationss(): MutableLiveData<List<Locations>>? {
        allLocationss = repository!!.getAllLocationss()

        return allLocationss
    }


//    fun getResultSms(mobile:String ,uuid:String): LiveData<SmsModel>? {
//
//
//
//
//        val call: Call<LiveData<SmsModel>> =
//           repository!!.getResultSms("09348491215", "0b91b4f6e4f997cb3df86a5c8d2fd40c")
//
//        call.enqueue(object : Callback<LiveData<SmsModel>?> {
//            override fun onResponse(call: Call<LiveData<SmsModel>?>, response: Response<LiveData<SmsModel>?>) {
//                val gson = Gson()
//                resultSms = response.body()!!
//
//
//            }
//
//            override fun onFailure(call: Call<LiveData<SmsModel>?>, t: Throwable) {
//
//                var p=0
//            }
//        })
//
//
//
//
//
//
//
//
//
//        return resultSms
//    }


    fun getResultSms(mobile: String, uuid: String, token: String): MutableLiveData<SmsModel?>? {

        smsModel = repository!!.getResultSms(mobile, uuid, token)!!
        return smsModel!!
    }


    fun getResultInvalidCode(
        mobile: String,
        code: String,
        uuid: String,
        clientId: String,
        clientSecret: String,
        token: String
    ): MutableLiveData<InvalidCodeModel?>? {
        if (invalidCodeModel == null)
            invalidCodeModel =
                repository!!.getResultInvalidCode(
                    mobile,
                    code,
                    uuid,
                    clientId,
                    clientSecret,
                    token
                )!!
        return invalidCodeModel!!
    }

    fun getResultRefreshToken(
        refreshToken: String,
        uuid: String,
        clientId: String,
        clientSecret: String
    ): MutableLiveData<RefreshTokenModel?>? {
        if (refreshTokenModel == null)
            refreshTokenModel =
                repository!!.getResultRefreshToken(refreshToken, uuid, clientId, clientSecret)!!
        return refreshTokenModel!!
    }

}