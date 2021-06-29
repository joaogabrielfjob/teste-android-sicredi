package br.com.testesicredi.viewmodel

import androidx.lifecycle.MutableLiveData
import br.com.testesicredi.data.repository.EventsRepository
import br.com.testesicredi.model.EventDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventDetailsViewModel {
    private val eventsRepository = EventsRepository().makeRequest()
    val eventResponse = MutableLiveData<EventDetails>()

    fun getEventDetails(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = eventsRepository.getEventDetailsById(id)

                eventResponse.postValue(response)
            } catch (exception: Exception) {
                println("exception $exception")

                eventResponse.postValue(null)
            }
        }
    }
}