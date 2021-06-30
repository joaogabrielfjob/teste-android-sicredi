package br.com.testesicredi.view

import android.location.Geocoder
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import br.com.testesicredi.R
import br.com.testesicredi.databinding.FragmentEventDetailsBinding
import br.com.testesicredi.model.EventDetails
import br.com.testesicredi.util.GlideApp
import br.com.testesicredi.util.Util
import br.com.testesicredi.viewmodel.EventDetailsViewModel
import java.util.*

class EventDetails : Fragment(R.layout.fragment_event_details) {
    private lateinit var binding: FragmentEventDetailsBinding
    private val eventDetailsViewModel = EventDetailsViewModel()
    private val util = Util()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentEventDetailsBinding.bind(view)
        binding.btnGoBack.setOnClickListener { openEvents() }
        binding.btnCheckIn.setOnClickListener { openCheckIn() }

        eventDetailsViewModel.getEventDetails(getEventId())
        eventDetailsResponse()
    }

    private fun openEvents() {
        Navigation.findNavController(requireView()).navigate(R.id.eventDetailsToEvents)
    }

    private fun openCheckIn() {
        val bundle = bundleOf("id" to getEventId())

        Navigation.findNavController(requireView()).navigate(R.id.eventDetailsToCheckIn, bundle)
    }

    private fun eventDetailsResponse() {
        eventDetailsViewModel.eventDetailsResponse.observe(viewLifecycleOwner, { response ->
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

            setEventThumbnail(image)
            setEventLocation(latitude, longitude)
            showMoreOrLessDescription()
        }
    }

    private fun getEventId() = arguments?.getString("id")!!

    private fun setEventThumbnail(url: String) {
        GlideApp.with(requireView())
            .load(url)
            .placeholder(R.drawable.ic_no_image)
            .error(R.drawable.ic_no_image)
            .into(binding.imgEventThumbnail)
    }

    private fun setEventLocation(latitude: Double, longitude: Double) {
        val geoDecoder = Geocoder(requireContext(), Locale.getDefault())
        val eventLocation = geoDecoder.getFromLocation(latitude, longitude, 1)

        binding.txtEventLocation.text = eventLocation[0].getAddressLine(0)
    }

    private fun showMoreOrLessDescription() {
        val btnShowMore = binding.btnShowEventDescription

        btnShowMore.setOnClickListener {
            if (btnShowMore.text.equals(getString(R.string.btn_show_more_text_label))) {
                binding.txtEventDescription.maxLines = Int.MAX_VALUE
                btnShowMore.text = getString(R.string.btn_show_less_text_label)
            } else {
                binding.txtEventDescription.maxLines = 3
                btnShowMore.text = getString(R.string.btn_show_more_text_label)
            }
        }
    }
}