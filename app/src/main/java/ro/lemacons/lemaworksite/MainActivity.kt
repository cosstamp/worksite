package ro.lemacons.lemaworksite

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.persistence.room.Update
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import ro.lemacons.lemaworksite.adapters.SantiereListAdapter
import ro.lemacons.lemaworksite.data.AppDatabase
import ro.lemacons.lemaworksite.data.Santiere
import ro.lemacons.lemaworksite.utilities.URL_Text
import ro.lemacons.lemaworksite.viewmodel.SantiereViewModel
import ro.lemacons.lemaworksite.workers.UpdateDatabase
import java.net.URL
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {
    private lateinit var santiereViewModel: SantiereViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        santiereViewModel = ViewModelProviders.of(this).get(SantiereViewModel::class.java)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = SantiereListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        santiereViewModel.allSantiere.observe(this, Observer { santiere->
            // Update the cached copy of the words in the adapter.
            santiere?.let { adapter.setSantiere(it) }
        })

        // Set an on refresh listener for swipe refresh layout
        swiperefresh.setOnRefreshListener {

            /***  Logic to set Data while creating worker **/
            val compressionWork = OneTimeWorkRequest.Builder(UpdateDatabase::class.java)
            val data = Data.Builder()
                //Add parameter in Data class. just like bundle. You can also add Boolean and Number in parameter.
            data.putString("file_path", "put_file_path_here")
                //Set Input Data
            compressionWork.setInputData(data.build())
                //enque worker
            WorkManager.getInstance().enqueue(compressionWork.build())
            Log.d("Update","Inside main")
            // Hide swipe to refresh icon animation
            swiperefresh.isRefreshing = false
        }

    }
}
