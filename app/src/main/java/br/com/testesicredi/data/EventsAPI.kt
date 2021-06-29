package br.com.testesicredi.data

import br.com.testesicredi.model.Event
import br.com.testesicredi.model.EventCheckIn
import br.com.testesicredi.model.EventDetails
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventsAPI {

    @GET("events/")
    suspend fun getAllEvents(): ArrayList<Event>

    @GET("events/{id}")
    suspend fun getEventDetailsById(@Path("id") id: String): EventDetails

    @POST("checkin/")
    suspend fun checkIn(@Body eventCheckIn: EventCheckIn)
}