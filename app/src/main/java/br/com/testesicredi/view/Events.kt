package br.com.testesicredi.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.testesicredi.R
import br.com.testesicredi.adapter.EventsAdapter
import br.com.testesicredi.databinding.FragmentEventsBinding
import br.com.testesicredi.model.Event
import br.com.testesicredi.viewmodel.EventsViewModel

class Events : Fragment(R.layout.fragment_events) {
    private lateinit var binding: FragmentEventsBinding
    private val eventsViewModel = EventsViewModel()
    private val adapter = EventsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentEventsBinding.bind(view)

        eventsViewModel.getAllEvents()
        eventsResponse()
    }

    private fun eventsResponse() {
        eventsViewModel.eventsResponse.observe(viewLifecycleOwner, { response ->
            when(response) {
                is ArrayList<Event> -> setEvents(response)

                else -> println("exception $response")
            }
        })
    }

    private fun setEvents(events: ArrayList<Event>) {
        adapter.addItems(events)

        binding.rViewEvents.layoutManager = LinearLayoutManager(requireContext())
        binding.rViewEvents.adapter = adapter
        binding.rViewEvents.setHasFixedSize(true)
        binding.rViewEvents.setItemViewCacheSize(10)
    }
}