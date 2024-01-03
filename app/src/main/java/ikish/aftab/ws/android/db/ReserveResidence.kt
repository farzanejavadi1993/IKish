package ikish.aftab.ws.android.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reserve_residence")
data class ReserveResidence(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "DATE")
    var DATE: String,
    @ColumnInfo(name = "Name")
    var Name: String,


    @ColumnInfo(name = "T")
    var T: String,
    @ColumnInfo(name = "PR")
    var PR: String,
    @ColumnInfo(name = "DC")
    var DC: String,
    @ColumnInfo(name = "CO")
    var CO: String,
    @ColumnInfo(name = "PPR")
    var PPR: String,
    @ColumnInfo(name = "POS")
    var POS: String,
)
