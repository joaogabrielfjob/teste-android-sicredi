package br.com.testesicredi.view

import android.content.Intent
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import br.com.testesicredi.R
import br.com.testesicredi.databinding.FragmentEventDetailsBinding
import br.com.testesicredi.model.EventDetails
import br.com.testesicredi.util.GlideApp
import br.com.testesicredi.util.Util
import br.com.testesicredi.viewmodel.EventDetailsViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.HttpException
import java.io.IOException
import java.util.Locale

class EventDetails : Fragment(R.layout.fragment_event_details) {
    private lateinit var binding: FragmentEventDetailsBinding
    private val eventDetailsViewModel = EventDetailsViewModel()
    private val util = Util()
    private val args: EventDetailsArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentEventDetailsBinding.bind(view)
        binding.btnGoBack.setOnClickListener { openEvents() }
        binding.btnCheckIn.setOnClickListener { openCheckIn() }

        val eventId = getEventId()
        val eventIdFromDeepLink = args.id

        if (eventId != null || eventIdFromDeepLink != null) {
            eventDetailsViewModel.getEventDetails(eventId ?: eventIdFromDeepLink!!)
            eventDetailsResponse()
            exceptionResponse()
        } else {
            showErrorDialog(
                getString(R.string.generic_exception_title),
                getString(R.string.generic_exception_msg)
            )
        }
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
            }
        })
    }

    private fun exceptionResponse() {
        eventDetailsViewModel.exceptionResponse.observe(viewLifecycleOwner, { exception ->
            when (exception) {
                is IOException -> {
                    showErrorDialog(
                        getString(R.string.internet_exception_title),
                        getString(R.string.internet_exception_msg)
                    )
                }

                is HttpException -> {
                    showErrorDialog(
                        getString(R.string.http_exception_title),
                        getString(R.string.http_exception_msg)
                    )
                }

                else -> {
                    showErrorDialog(
                        getString(R.string.generic_exception_title),
                        getString(R.string.generic_exception_msg)
                    )
                }
            }

            binding.containerEventDetails.visibility = View.INVISIBLE
        })
    }

    private fun setEventDetails(eventDetails: EventDetails) {
        with(eventDetails) {
            binding.txtEventTitle.text = title ?: "Sem título"
            binding.txtEventDescription.text = description ?: "Sem descrição"

            image?.let {
                setEventThumbnail(image)
            }

            validEventPrice(price)
            validEventDate(date)
            validEventLocation(latitude, longitude)
            showMoreOrLessDescription()

            binding.containerEventDetails.visibility = View.VISIBLE
            binding.btnShare.setOnClickListener { shareEvent(eventDetails) }
        }
    }

    private fun getEventId() = arguments?.getString("id")

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
        binding.txtEventLocation.setOnClickListener { openEventLocationOnMaps(latitude, longitude) }
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

    private fun showErrorDialog(title: String, message: String) {
        MaterialAlertDialogBuilder(requireContext(), R.style.ErrorDialogTheme)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { d, _ ->
                d.dismiss()
                openEvents()
            }
            .show()
    }

    private fun validEventPrice(price: String?) {
        if (price != null) {
            binding.txtEventPrice.text = price
            binding.imgPrice.visibility = View.VISIBLE
        } else {
            binding.imgPrice.visibility = View.INVISIBLE
        }
    }

    private fun validEventDate(date: Long?) {
        if (date != null) {
            binding.txtEventDate.text = util.convertDate(date)

            binding.txtEventDate.visibility = View.VISIBLE
            binding.imgCalendar.visibility = View.VISIBLE
        } else {
            binding.txtEventDate.visibility = View.INVISIBLE
            binding.imgCalendar.visibility = View.INVISIBLE
        }
    }

    private fun validEventLocation(latitude: Double?, longitude: Double?) {
        if (latitude != null && longitude != null) {
            setEventLocation(latitude, longitude)

            binding.imgLocation.visibility = View.VISIBLE
        } else {
            binding.imgLocation.visibility = View.INVISIBLE
        }
    }

    private fun openEventLocationOnMaps(latitude: Double, longitude: Double) {
        val mapUri = Uri.parse("geo:0,0?q=$latitude,$longitude")
        val mapIntent = Intent(Intent.ACTION_VIEW, mapUri)

        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    private fun shareEvent(eventDetails: EventDetails) {
        val sendIntent = Intent()
        val title = eventDetails.title ?: getString(R.string.share_no_title)
        var date = getString(R.string.share_no_date)

        eventDetails.date?.let {
           date = util.convertDate(it)
        }

        val msg = String.format(getString(R.string.share_body), title, date, eventDetails.id)

        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, msg)
        sendIntent.type = "text/plain"

        startActivity(sendIntent)
    }
}