package ro.lemacons.lemaworksite.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ro.lemacons.lemaworksite.data.AppDatabase
import ro.lemacons.lemaworksite.data.Santiere
import ro.lemacons.lemaworksite.data.SantiereDao
import ro.lemacons.lemaworksite.data.SantiereRepository
import kotlin.coroutines.CoroutineContext

class SantiereViewModel(application: Application): AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val santiereRepository: SantiereRepository
    val allSantiere: LiveData<List<Santiere>>

    init {
        val santiereDao = AppDatabase.getInstance(application, scope).santiereDao()
        santiereRepository = SantiereRepository(santiereDao)
        allSantiere = santiereRepository.allSantiere
    }

    fun insert(santiere: Santiere) = scope.launch(Dispatchers.IO) {
        santiereRepository.insert(santiere)
    }

    fun insertAll(santiere: List<Santiere>) = scope.launch(Dispatchers.IO) {
        santiereRepository.insertAll(santiere)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

}