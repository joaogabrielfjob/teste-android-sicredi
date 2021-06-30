package br.com.testesicredi.util

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import br.com.testesicredi.R
import br.com.testesicredi.databinding.FragmentEventDetailsBinding
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

class Util {

    fun convertDate(date: Long): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateConverted = Date(TimeUnit.SECONDS.toMillis(date))

        return dateFormat.format(dateConverted)
    }

    fun isNameInvalid(name: String): Boolean {
        val patternNumber = Pattern.matches(".*\\d.*", name)
        val patterSpecialCharacters = Pattern.matches("\\p{Punct}", name)

        return patternNumber || patterSpecialCharacters
    }

    fun isEmailInvalid(email: String): Boolean {
        val patternAt = email.contains("@")
        val patternDot = email.contains(".")

        val checkEmail = email.split("@")

        return !patternAt ||
            !patternDot ||
            checkEmail[0].isEmpty() ||
            checkEmail[1].isEmpty()
    }

    fun setErrorStatus(textInputLayout: TextInputLayout, context: Context, message: String) {
        textInputLayout.error = message
        textInputLayout.boxBackgroundColor = ContextCompat.getColor(context, R.color.red)
    }

    fun setNormalStatus(textInputLayout: TextInputLayout, context: Context) {
        textInputLayout.error = null
        textInputLayout.boxBackgroundColor = ContextCompat.getColor(context, R.color.black)
    }
}