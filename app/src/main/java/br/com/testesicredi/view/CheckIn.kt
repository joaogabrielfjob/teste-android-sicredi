package br.com.testesicredi.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import br.com.testesicredi.R
import br.com.testesicredi.databinding.FragmentCheckInBinding
import br.com.testesicredi.model.EventCheckIn
import br.com.testesicredi.viewmodel.CheckInViewModel

class CheckIn : Fragment(R.layout.fragment_check_in) {
    private lateinit var binding: FragmentCheckInBinding
    private val checkInViewModel = CheckInViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCheckInBinding.bind(view)

        binding.btnConfirmCheckIn.setOnClickListener { checkIn() }
        checkInResponse()
    }

    private fun checkInResponse() {
        checkInViewModel.checkInResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Boolean -> if (response) println("response $response")

                else -> println("exception $response")
            }
        })
    }

    private fun checkIn() {
        val eventId = arguments?.getString("id")!!
        val name = binding.inputName.text.toString()
        val email = binding.inputEmail.text.toString()

        val eventCheckIn = EventCheckIn(eventId, name, email)

        checkInViewModel.checkIn(eventCheckIn)
    }
}