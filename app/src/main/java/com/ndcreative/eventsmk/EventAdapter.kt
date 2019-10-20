package com.ndcreative.eventsmk

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_event.view.*


/**
 * Created by Umar Fadil on 19,Oct,2019
 * ND Creative Solution
 * id.ndcreativesolution@gmail.com
 */

class EventAdapter(private val itemEventList: ArrayList<Event>) :
    RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return itemEventList.size
    }

    override fun onBindViewHolder(holder: EventAdapter.ViewHolder, position: Int) {
        holder.bindItems(itemEventList[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.context

        fun bindItems(event: Event) {

            val txtEventName = itemView.tv_event_title!!
            val txtEventDate = itemView.tv_event_date!!
            val imgPoster = itemView.img_poster!!
            val cardEvent = itemView.card_event!!

            txtEventName.text = event.title
            txtEventDate.text = event.date
            Picasso.get().load(event.image).into(imgPoster)
            cardEvent.setOnClickListener {
                Toast.makeText(context, event.title, Toast.LENGTH_SHORT).show()
                val intent = Intent(context,DetailEventActivity::class.java)
                intent.putExtra("event_name", event.title)
                intent.putExtra("event_date", event.date)
                intent.putExtra("img_poster", event.image)
                intent.putExtra("event_desc", event.description)
                intent.putExtra("latitude", event.latitude)
                intent.putExtra("longitude", event.longitude)
                context.startActivity(intent)
            }
        }


    }

}