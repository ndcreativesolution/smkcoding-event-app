package com.ndcreative.eventsmk

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    val events = ArrayList<Event>()

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        getDataEvent()

        rv_event_list.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        sr_event.setOnRefreshListener {
            getDataEvent()
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun getDataEvent() {
        events.clear()
        rv_event_list.adapter = null
        AndroidNetworking.get(EndPoints.BASE_URL)
            .addQueryParameter("action", "read")
            .addQueryParameter("table_name", "event")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    Log.d("EVENT RESULT", response.toString())

                    val eventData = response.getJSONArray("data")
                    for (i in 0 until eventData.length()) {
                        val data = eventData.getJSONObject(i)

                        events.add(
                            Event(
                                data.optString("title"),
                                data.optString("date"),
                                data.optString("image"),
                                data.optString("description"),
                                data.optDouble("latitude"),
                                data.optDouble("longitude")
                            )
                        )

                    }
                    rv_event_list.adapter = EventAdapter(events)
                    sr_event.isRefreshing = false

                }

                override fun onError(anError: ANError?) {
                    Log.e("ERROR", anError.toString())
                }
            })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
