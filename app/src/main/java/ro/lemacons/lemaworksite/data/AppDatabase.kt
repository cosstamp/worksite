package ro.lemacons.lemaworksite.data

import android.arch.lifecycle.ViewModelProviders
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ro.lemacons.lemaworksite.MainActivity
import ro.lemacons.lemaworksite.utilities.DATABASE_NAME
import ro.lemacons.lemaworksite.utilities.URL_Text
import ro.lemacons.lemaworksite.viewmodel.SantiereViewModel
import ro.lemacons.lemaworksite.workers.UpdateDatabase
import java.net.URL
import java.util.concurrent.Executors


/**
 * The Room database for this app
 */
@Database(entities = [Santiere::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun santiereDao(): SantiereDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context, scope).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context, scope: CoroutineScope): AppDatabase {

            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                       //val request = OneTimeWorkRequestBuilder<UpdateDatabase>().build()
                       // WorkManager.getInstance().enqueue(request)
                       // Log.d("database", " onCreate - true")
                    }
                })
                .addCallback(SantiereDatabaseCallback(scope))
                .build()
        }
    }

    private class SantiereDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            instance?.let { database ->
                scope.launch(Dispatchers.IO) {
                    Log.d("database", " onOpen - true")
                    populateDatabase(database.santiereDao())
                }
            }
        }
        fun populateDatabase(santiereDao: SantiereDao) {
            val santierType = object : TypeToken<List<Santiere>>() {}.type
            val listSantiere : MutableList<Santiere> =
            Gson().fromJson(URL(URL_Text).readText(),santierType)
            santiereDao.insertAll(listSantiere)

            Log.d("database", " populate - true")
        }
    }

}