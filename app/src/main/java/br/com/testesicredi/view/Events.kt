package br.com.testesicredi.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.testesicredi.R
import br.com.testesicredi.adapter.EventsAdapter
import br.com.testesicredi.databinding.FragmentEventsBinding
import br.com.testesicredi.model.Event
import br.com.testesicredi.util.Util
import br.com.testesicredi.viewmodel.EventsViewModel
import retrofit2.HttpException
import java.io.IOException

class Events : Fragment(R.layout.fragment_events) {
    private lateinit var binding: FragmentEventsBinding
    private val eventsViewModel = EventsViewModel()
    private val adapter = EventsAdapter()
    private val util = Util()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentEventsBinding.bind(view)

        eventsViewModel.getAllEvents()
        exceptionResponse()
        eventsResponse()
    }

    private fun eventsResponse() {
        eventsViewModel.eventsResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is ArrayList<Event> -> setEvents(response)
            }
        })
    }

    private fun exceptionResponse() {
        eventsViewModel.exceptionResponse.observe(viewLifecycleOwner, { exception ->
            when (exception) {
                is IOException -> {
                    util.showErrorDialog(
                        requireContext(),
                        getString(R.string.internet_exception_title),
                        getString(R.string.internet_exception_msg)
                    )
                }

                is HttpException -> {
                    util.showErrorDialog(
                        requireContext(),
                        getString(R.string.http_exception_title),
                        getString(R.string.http_exception_msg)
                    )
                }

                else -> {
                    util.showErrorDialog(
                        requireContext(),
                        getString(R.string.generic_exception_title),
                        getString(R.string.generic_exception_msg)
                    )
                }
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