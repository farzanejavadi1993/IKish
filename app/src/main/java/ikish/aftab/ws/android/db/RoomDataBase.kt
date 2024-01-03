package ikish.aftab.ws.android.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ikish.aftab.ws.android.fetchData.TodoDao


@Database( entities = [
    Passenger::class ,
    ReserveResidence::class,
    Residence::class,
    Transactions::class,
    Locations::class,
    PassengerFlight::class,
    DetailFlight::class,
    Supervisers::class,
    Bill::class,
], version = 3,
    )

abstract class RoomDataBase : RoomDatabase(){

abstract  fun todoDao():TodoDao


}
