package br.com.testesicredi.model

data class EventDetails(
    val title: String,
    val date: Long,
    val price: String,
    val people: ArrayList<People>,
    val description: String,
    val image: String,
    val longitute: Double,
    val latitude: Double,
)
