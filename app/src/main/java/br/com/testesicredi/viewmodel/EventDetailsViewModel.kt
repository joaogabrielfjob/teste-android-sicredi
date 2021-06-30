package br.com.testesicredi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.testesicredi.data.repository.EventsRepository
import br.com.testesicredi.model.EventDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventDetailsViewModel : ViewModel() {
    private val eventsRepository = EventsRepository().makeRequest()
    val eventDetailsResponse = MutableLiveData<EventDetails>()
    val exceptionResponse = MutableLiveData<Exception>()

    fun getEventDetails(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = eventsRepository.getEventDetailsById(id)

                eventDetailsResponse.postValue(response)
            } catch (exception: Exception) {
                exceptionResponse.postValue(exception)
            }
        }
    }
}