package ikish.aftab.ws.android.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Bill")
data class Bill(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "D") var D: String,//DATE
    @ColumnInfo(name = "P") var P: String,//PRICE

    )