package ikish.aftab.ws.android.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "residence")
data class Residence(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "NM")
    var NM: String?="",//NAME RESIDENCE
    @ColumnInfo(name = "Ab")
    var Ab: String?="",//ABOUT RESIDENCE
    @ColumnInfo(name = "TY")//TYPE RESIDENCE
    var TY: String?="",
    @ColumnInfo(name = "ST")//STATUS RESIDENCE
    var ST: String?="",
    @ColumnInfo(name = "PO")//POSSIBILITY RESIDENCE
    var PO: String?="",
    @ColumnInfo(name = "AR")//AREA RESIDENCE
    var AR: String?="",
    @ColumnInfo(name = "CAR")//COMPLETE AREA RESIDENCE
    var CAR: String?="",
    @ColumnInfo(name = "TO")//TOILET  RESIDENCE
    var TO: String?="",
    @ColumnInfo(name = "ADD")//ADDRESS  RESIDENCE
    var ADD: String?="",
    @ColumnInfo(name = "TEL")//TEL  RESIDENCE
    var TEL: String?="",
    @ColumnInfo(name = "lat")
    var lat: String?="",
    @ColumnInfo(name = "lng")
    var lng: String?="",
    @ColumnInfo(name = "IMG")
    var IMG: String?="",//IMAGE RESIDENCE
    @ColumnInfo(name = "RUL")
    var RUL: String?="",//RULES RESIDENCE
    @ColumnInfo(name = "PR")
    var PR: String,//Price RESIDENCE
    @ColumnInfo(name = "DS")
    var DS: String?="",//DISCOUNT RESIDENCE
    @ColumnInfo(name = "ME")
    var ME: String?="",//ME CREATE RESIDENCE
    @ColumnInfo(name = "NOP")
    var NOP: String?="",//NUMBER OF PASSENGER
    @ColumnInfo(name = "RAT")
    var RAT: String?="",
    @ColumnInfo(name = "HN")
    var HN: String?="",//HOST RESIDENCE

    @ColumnInfo(name = "T")
    var T: String?="", //2 ویلا     3خودرو
)
