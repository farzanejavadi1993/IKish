package ikish.aftab.ws.android.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DetailFlight")
data class DetailFlight(
    @PrimaryKey(autoGenerate = true) var id: Int,


    @ColumnInfo(name = "ONN")//NUMBER ORDER
    var ONN: String,
    @ColumnInfo(name = "DATE")
    var DATE: String,
    @ColumnInfo(name = "CN")//COMPANY NAME
    var CN: String,
    @ColumnInfo(name = "NF")//NUMBER FLIGHT
    var NF: String,
    @ColumnInfo(name = "TOO")//TIME ORIGIN
    var TOO: String,
    @ColumnInfo(name = "TD")//TIME DESTINATION
    var TD: String,
    @ColumnInfo(name = "O")//ORIGIN
    var O: String,

    @ColumnInfo(name = "D")//DESTINATION
    var D: String,

    @ColumnInfo(name = "T")//TIME MOVE
    var T: String,
    @ColumnInfo(name = "AO")//AIRPORT ORIGIN
    var AO: String,

    @ColumnInfo(name = "AD")//AIRPORT DESTINATION
    var AD: String,
    @ColumnInfo(name = "ST")//STATUS
    var ST: String,
    @ColumnInfo(name = "TF")//TYPE FLIGHT
    var TF: String,
    @ColumnInfo(name = "SP")//SUM PRICE
    var SP: String,
    @ColumnInfo(name = "OTD")//ORIGIN TO DETIBATION
    var OTD: String,

    @ColumnInfo(name = "MEM")//NUMBER OF PASSENGER
    var MEM: String,

    )


