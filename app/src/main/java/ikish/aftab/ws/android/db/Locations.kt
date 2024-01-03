package ikish.aftab.ws.android.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Locations")
data class Locations(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "N") var N: String,
    @ColumnInfo(name = "NR") var NR: String,

)