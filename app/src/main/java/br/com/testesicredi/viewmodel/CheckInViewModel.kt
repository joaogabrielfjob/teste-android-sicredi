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

    fun checkIn(eventCheckIn: EventCheckIn) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                eventsRepository.checkIn(eventCheckIn)
                checkInResponse.postValue(true)
            } catch (excetpion: Exception) {
                println("exception $excetpion")

                checkInResponse.postValue(null)
            }
        }
    }
}