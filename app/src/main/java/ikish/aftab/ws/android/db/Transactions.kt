package ikish.aftab.ws.android.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transactions(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "CO")
    var CO: String,
    @ColumnInfo(name = "DATE")
    var DATE: String,
    @ColumnInfo(name = "PR")
    var PR: String,


    )
