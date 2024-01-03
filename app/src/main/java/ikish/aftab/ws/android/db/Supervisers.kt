package ikish.aftab.ws.android.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "supervisers")
data class Supervisers(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "GID")
    var GID: String,
    @ColumnInfo(name = "NM")
    var NM: String,


    )