package br.com.testesicredi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.testesicredi.data.repository.EventsRepository
import br.com.testesicredi.model.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventsViewModel : ViewModel() {
    private val eventsRepository = EventsRepository().makeRequest()
    val eventsResponse: MutableLiveData<ArrayList<Event>> = MutableLiveData()

    fun getAllEvents() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = eventsRepository.getAllEvents()

                eventsResponse.postValue(response)
            } catch (exception: Exception) {
                println("exception $exception")

                eventsResponse.postValue(null)
            }
        }
    }
}