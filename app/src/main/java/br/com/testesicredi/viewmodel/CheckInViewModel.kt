package br.com.testesicredi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.testesicredi.data.repository.EventsRepository
import br.com.testesicredi.model.EventCheckIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckInViewModel : ViewModel() {
    private val eventsRepository = EventsRepository().makeRequest()
    val checkInResponse = MutableLiveData<Boolean>()
    val exceptionResponse = MutableLiveData<Exception>()

    fun checkIn(eventCheckIn: EventCheckIn) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                eventsRepository.checkIn(eventCheckIn)
                checkInResponse.postValue(true)
            } catch (exception: Exception) {
                exceptionResponse.postValue(exception)
            }
        }
    }
}