package ro.lemacons.lemaworksite.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy


/**
 * The Data Access Object for the Plant class.
 */
@Dao
interface SantiereDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(santiere: List<Santiere>)
}