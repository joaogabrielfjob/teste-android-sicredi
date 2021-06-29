package br.com.testesicredi.data

import br.com.testesicredi.model.Event
import retrofit2.http.GET

interface EventsAPI {

    @GET("events/")
    suspend fun getAllEvents(): ArrayList<Event>
}