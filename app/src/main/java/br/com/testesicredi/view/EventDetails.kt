package br.com.testesicredi.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import br.com.testesicredi.R
import br.com.testesicredi.databinding.FragmentEventDetailsBinding
import br.com.testesicredi.model.EventDetails
import br.com.testesicredi.util.Util
import br.com.testesicredi.viewmodel.EventDetailsViewModel

class EventDetails : Fragment(R.layout.fragment_event_details) {
    private lateinit var binding: FragmentEventDetailsBinding
    private val eventDetailsViewModel = EventDetailsViewModel()
    private val util = Util()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentEventDetailsBinding.bind(view)
        binding.btnGoBack.setOnClickListener { openEvents() }

        val id = arguments?.getString("id")

        eventDetailsViewModel.getEventDetails(id!!)
        eventDetailsResponse()
    }

    private fun openEvents() {
        Navigation.findNavController(requireView()).navigate(R.id.eventDetailsToEvents)
    }

    private fun eventDetailsResponse() {
        eventDetailsViewModel.eventResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is EventDetails -> setEventDetails(response)

                else -> println("exception $response")
            }
        })
    }

    private fun setEventDetails(eventDetails: EventDetails) {
        with(eventDetails) {
            binding.txtEventTitle.text = title
            binding.txtEventDescription.text = description
            binding.txtEventDate.text = util.convertDate(date)
            binding.txtEventPrice.text = price
        }
    }
}