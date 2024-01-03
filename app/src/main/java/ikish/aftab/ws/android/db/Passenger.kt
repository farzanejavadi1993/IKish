package ikish.aftab.ws.android.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "passenger")
data class Passenger(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "FN") var FN: String,
    @ColumnInfo(name = "LN") var LN: String,
    @ColumnInfo(name = "EFN") var EFN: String,
    @ColumnInfo(name = "ELN") var ELN: String,
    @ColumnInfo(name = "NC") var NC: String,
    @ColumnInfo(name = "PC") var PC: String,
    @ColumnInfo(name = "GD") var GD: String,
    @ColumnInfo(name = "CON") var CON: String,
    @ColumnInfo(name = "MOB") var MOB: String,
    @ColumnInfo(name = "CN") var CN: String,
    @ColumnInfo(name = "IDC") var IDC: String,
    @ColumnInfo(name = "BRD") var BRD: String,
)