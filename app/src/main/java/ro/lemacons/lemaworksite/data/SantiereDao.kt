package ro.lemacons.lemaworksite.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


/**
 * The Data Access Object for the Plant class.
 */
@Dao
interface SantiereDao {

    @Query("SELECT * FROM santiere ORDER BY id_santier")
    fun getList(): LiveData<List<Santiere>>

    @Insert
    fun insert(santiere: Santiere)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(santiere: List<Santiere>)

    @Query("DELETE FROM santiere")
    fun deleteAll()
}