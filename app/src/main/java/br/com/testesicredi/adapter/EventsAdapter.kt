package br.com.testesicredi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import br.com.testesicredi.R
import br.com.testesicredi.databinding.RecyclerViewEventBinding
import br.com.testesicredi.model.Event
import br.com.testesicredi.util.Util
import kotlin.collections.ArrayList

class EventsAdapter: RecyclerView.Adapter<EventsAdapter.ActivityViewHolder>() {
    private val events = ArrayList<Event>()
    private val util = Util()

    inner class ActivityViewHolder(val binding: RecyclerViewEventBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventsAdapter.ActivityViewHolder {
        val binding = RecyclerViewEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ActivityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventsAdapter.ActivityViewHolder, position: Int) {
        with(holder) {
            with(events[position]) {
                binding.txtEventTitle.text = title ?: "Título do evento"

                date?.let {
                    binding.txtEventDate.text = util.convertDate(it)
                }

                binding.cardEvent.setOnClickListener { openEventDetails(itemView, id) }
            }
        }
    }

    override fun getItemCount(): Int = events.size

    fun addItems(newEvents: ArrayList<Event>) {
        newEvents.forEach { t ->
            if (!events.contains(t)) {
                events.add(t)
            }
        }

        this.notifyDataSetChanged()
    }

    fun clear() = events.clear()

    private fun openEventDetails(view: View, id: String?) {
        val bundle = bundleOf("id" to id)

        Navigation.findNavController(view).navigate(R.id.eventsToEventDetails, bundle)
    }
}