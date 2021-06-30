package br.com.testesicredi.data.repository

import br.com.testesicredi.data.EventsAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EventsRepository {

    fun makeRequest(): EventsAPI {
        val baseUrl = "http://5f5a8f24d44d640016169133.mockapi.io/api/"

        return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EventsAPI::class.java)
    }
}