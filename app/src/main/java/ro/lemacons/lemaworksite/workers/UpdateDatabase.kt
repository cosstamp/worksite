package ro.lemacons.lemaworksite.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import ro.lemacons.lemaworksite.data.AppDatabase
import ro.lemacons.lemaworksite.data.Santiere
import ro.lemacons.lemaworksite.data.SantiereDao
import ro.lemacons.lemaworksite.utilities.URL_Text
import ro.lemacons.lemaworksite.viewmodel.SantiereViewModel
import java.net.URL

class UpdateDatabase (context: Context,
                               workerParams: WorkerParameters)
    : Worker(context, workerParams) {
    private lateinit var santiereViewModel: SantiereViewModel
    override fun doWork(): Result {

        val santierType = object : TypeToken<List<Santiere>>() {}.type

        return try {

            Log.d("Update","Inside Update")
            val listSantiere : MutableList<Santiere> =
                Gson().fromJson(URL(URL_Text).readText(),santierType)
            //santiereViewModel.insertAll(listSantiere)
            Log.d("Update","Inside Update 1")
            Result.SUCCESS
        } catch (e: Exception) {
            Result.FAILURE
        }
    }
}