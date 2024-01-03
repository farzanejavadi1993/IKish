package ikish.aftab.ws.android.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PassengerFlight")
data class PassengerFlight(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "N")//NAME
    var N: String,
    @ColumnInfo(name = "RA")//RANGE AGE
    var RA: String,
    @ColumnInfo(name = "P")//PRICE
    var P: String,
    @ColumnInfo(name = "F")//FLIGHT REFRENCE
    var F: String,
    )
