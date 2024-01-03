package ikish.aftab.ws.android.fetchData

import androidx.lifecycle.LiveData
import androidx.room.*
import ikish.aftab.ws.android.db.*

@Dao
interface TodoDao {
    @Query("SELECT * FROM Passenger")
    fun getAllPassenger(): MutableList<Passenger>

    @Query("SELECT * FROM Passenger WHERE IDC =:idc")
    fun findPassengerById(idc: String): Passenger


    @Query("SELECT * FROM Passenger WHERE CN =:idc")
    fun findMyPassenger(idc: String): Passenger

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPassenger(vararg psg: Passenger)

    @Delete
    fun deletePassenger(psg: Passenger)


    @Update
    fun updatePassenger(vararg psg: Passenger)


    @Query("SELECT * FROM Residence")
    fun getAllResidence(): List<Residence>

    @Query("SELECT * FROM Residence WHERE NM =:id")
    fun findResidenceByName(id: String): Residence



    @Query("SELECT * FROM Residence WHERE id =:id")
    fun findResidenceById(id: Int): Residence




    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertResidence(vararg residence: Residence)

    @Update
    fun updateResidence(vararg rsd: Residence)
    @Query("DELETE FROM residence WHERE T = :T")
    fun deleteResidence(T:String)




    @Query("SELECT * FROM RESERVE_RESIDENCE WHERE Name =:id")
    fun findReserveResidenceById(id: String): ReserveResidence

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReserveResidence(vararg reserveResidence: ReserveResidence)

    @Query("SELECT * FROM reserve_residence")
    fun getAllReserveResidence(): LiveData<List<ReserveResidence>>


    @Query("SELECT * FROM Supervisers WHERE ID =:id")
    fun findSuperViserById(id: String): LiveData<Supervisers>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSuperViser(vararg psg: Supervisers)

    @Query("SELECT * FROM Supervisers  WHERE GID =:id")
    fun getAllSuperViser(id: String): List<Supervisers>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransactions(vararg transactions: Transactions)

    @Query("SELECT * FROM Transactions")
    fun getAllTransactions(): List<Transactions>


    @Query("SELECT * FROM Locations")
    fun getAllLocations():List<Locations>

    @Query("SELECT * FROM Locations WHERE NR =:nr")
    fun getAllRecentlyLocations(nr: String): List<Locations>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocation(vararg locations: Locations)

    @Query("SELECT * FROM Locations WHERE N =:name")
    fun getAllMyLocationName(name: String): LiveData<List<Locations>>

    @Query("SELECT * FROM Locations WHERE N =:name")
    fun findLocationById(name: String): LiveData<Locations>

    @Update
    fun updateLocation(vararg lct: Locations)


    @Query("UPDATE Locations SET NR = :nr WHERE id = :tid")
    fun updateLocationRecently(tid: Int, nr: String)


    @Query("SELECT * FROM PassengerFlight WHERE F =:f")
    fun getAllPassengerFlight(f: String): List<PassengerFlight>

    @Query("DELETE FROM PassengerFlight WHERE F = :f")
    fun deletePassengerFLight(f:String)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailFlight(vararg df: DetailFlight)

    @Query("SELECT * FROM DetailFlight WHERE ONN =:no")
    fun findDetailFlightById(no: String): LiveData<DetailFlight>

    @Query("SELECT * FROM DetailFlight ")
    fun getAllDetailFlight(): LiveData<List<DetailFlight>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPassengerFlight(vararg psgf: PassengerFlight)




    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBill(vararg bl: Bill)


    @Query("SELECT * FROM Bill")
    fun getAllBill(): LiveData<List<Bill>>




}