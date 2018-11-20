package ro.lemacons.lemaworksite.workers

import android.content.Context

import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import ro.lemacons.lemaworksite.data.Santiere
import java.lang.Exception
import java.net.URL
import java.util.concurrent.Executors

class UpdateDatabase (context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    var json: String = ""

    override fun doWork(): Result {

        val santierType = object : TypeToken<List<Santiere>>() {}.type
        var jsonReader: JsonReader? = null

        try {

            return Result.SUCCESS
        }catch (e:Exception){

        }


    }


}