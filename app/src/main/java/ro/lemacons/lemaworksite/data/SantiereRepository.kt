package ro.lemacons.lemaworksite.data

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread

class SantiereRepository (private val santiereDao: SantiereDao) {

    val allSantiere: LiveData<List<Santiere>> = santiereDao.getList()

    @WorkerThread
    suspend fun insert(santiere: Santiere) {
        santiereDao.insert(santiere)
    }

    @WorkerThread
    suspend fun insertAll(listSantiere: List<Santiere>) {
        santiereDao.insertAll(listSantiere)
    }
}