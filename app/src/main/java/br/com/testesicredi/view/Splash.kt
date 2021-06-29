package br.com.testesicredi.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import br.com.testesicredi.R

class Splash : Fragment(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({ openEvents() }, 3000)
    }

    private fun openEvents() {
        Navigation.findNavController(requireView()).navigate(R.id.splashToEvents)
    }
}