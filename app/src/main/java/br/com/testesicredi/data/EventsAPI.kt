package br.com.testesicredi.data

import br.com.testesicredi.model.Event
import br.com.testesicredi.model.EventDetails
import retrofit2.http.GET
import retrofit2.http.Path

interface EventsAPI {

    @GET("events/")
    suspend fun getAllEvents(): ArrayList<Event>

    @GET("events/{id}")
    suspend fun getEventDetailsById(@Path("id") id: String): EventDetails
}