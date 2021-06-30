package br.com.testesicredi.util

import android.content.Context
import br.com.testesicredi.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class Util {

    fun convertDate(date: Long): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateConverted = Date(TimeUnit.SECONDS.toMillis(date))

        return dateFormat.format(dateConverted)
    }

    fun showErrorDialog(context: Context, title: String, message: String) {
        MaterialAlertDialogBuilder(context, R.style.ErrorDialogTheme)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { d, _ ->  d.dismiss() }
            .show()
    }
}