package ikish.aftab.ws.android.model
data class FlightModel(
    val OriginTime: String = "",
    val DestinationTime: String = "",
    val OriginName: String = "",
    val DestinationName: String = "",
    val Time: String = "",
    val CompanyName: String = "",
    val NumberFlight: String = "",
    val Price: String = "",
    val MemberPassenger: String = "",
    val Type: String = "",
    val url: Int,
    val AirportOrigin: String,
    val AirportDestination: String,
    val OldPrice: String,
    val YoungPrice: String,
    val BabyPrice: String,

)