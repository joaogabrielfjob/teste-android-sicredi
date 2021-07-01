package br.com.testesicredi.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import br.com.testesicredi.R
import br.com.testesicredi.databinding.FragmentCheckInBinding
import br.com.testesicredi.model.EventCheckIn
import br.com.testesicredi.util.Util
import br.com.testesicredi.viewmodel.CheckInViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.HttpException
import java.io.IOException

class CheckIn : Fragment(R.layout.fragment_check_in) {
    private lateinit var binding: FragmentCheckInBinding
    private val checkInViewModel = CheckInViewModel()
    private val util = Util()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCheckInBinding.bind(view)
        binding.btnConfirmCheckIn.setOnClickListener { checkIn() }
        binding.btnGoBack.setOnClickListener { openEventDetails() }

        exceptionResponse()
        checkInResponse()

        hideErrorMessageWhenType()
    }

    private fun openEventDetails() {
        val eventId = arguments?.getString("id")
        val bundle = bundleOf("id" to eventId)

        Navigation.findNavController(requireView()).navigate(R.id.checkInToEventDetails, bundle)
    }

    private fun openEvents() {
        Navigation.findNavController(requireView()).navigate(R.id.checkInToEvents)
    }

    private fun checkInResponse() {
        checkInViewModel.checkInResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Boolean -> {
                    if (response) {
                        Toast.makeText(requireContext(), getString(R.string.toast_success), Toast.LENGTH_LONG).show()

                        openEvents()
                    }
                }
            }
        })
    }

    private fun exceptionResponse() {
        checkInViewModel.exceptionResponse.observe(viewLifecycleOwner, { exception ->
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
        })
    }

    private fun checkIn() {
        val eventId = arguments?.getString("id")

        if (eventId != null) {
            val name = binding.inputName.text.toString()
            val email = binding.inputEmail.text.toString()

            if (checkFields(name, email)) {
                val eventCheckIn = EventCheckIn(eventId, name, email)

                checkInViewModel.checkIn(eventCheckIn)
                binding.btnConfirmCheckIn.isEnabled = false
            }
        } else {
            showErrorDialog(
                getString(R.string.generic_exception_title),
                getString(R.string.generic_exception_msg)
            )
        }
    }

    private fun showErrorDialog(title: String, message: String) {
        MaterialAlertDialogBuilder(requireContext(), R.style.ErrorDialogTheme)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { d, _ ->
                d.dismiss()
                openEventDetails()
            }
            .show()
    }

    private fun checkFields(name: String?, email: String?): Boolean {
        if (name.isNullOrEmpty() || util.isNameInvalid(name)) {
            util.setErrorStatus(binding.filledTextFieldName,
                requireContext(),
                getString(R.string.txt_valid_name)
            )

            return false
        }

        if (email.isNullOrEmpty() || util.isEmailInvalid(email)) {
            util.setErrorStatus(binding.filledTextFieldEmail,
                requireContext(),
                getString(R.string.txt_valid_email)
            )

            return false
        }

        return true
    }

    private fun hideErrorMessageWhenType() {
        val editInputs = listOf(binding.inputName, binding.inputEmail)

        for (editInput in editInputs) {
            editInput.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    util.setNormalStatus(binding.filledTextFieldName, requireContext())
                    util.setNormalStatus(binding.filledTextFieldEmail, requireContext())
                }

                override fun afterTextChanged(s: Editable?) { }
            })
        }
    }
}