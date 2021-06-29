package br.com.testesicredi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.testesicredi.databinding.RecyclerViewEventBinding
import br.com.testesicredi.model.Event
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class EventsAdapter: RecyclerView.Adapter<EventsAdapter.ActivityViewHolder>() {
    private val events = ArrayList<Event>()

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
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val dateConverted = Date(TimeUnit.SECONDS.toMillis(date))

                binding.txtEventTitle.text = title
                binding.txtEventDate.text = dateFormat.format(dateConverted)
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
}