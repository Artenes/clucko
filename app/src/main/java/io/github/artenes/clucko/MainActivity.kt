package io.github.artenes.clucko

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val model: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = mutableListOf<String>()
        list.addAll(model.getTimes())
        val adapter = ClockInsAdapter(list)
        val rv = findViewById<RecyclerView>(R.id.rvClockIns)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)

        findViewById<FloatingActionButton>(R.id.fabClockIn).setOnClickListener {

            //https://www.rockandnull.com/java-time-android/
            //https://stackoverflow.com/questions/32437550/whats-the-difference-between-instant-and-localdatetime
            model.putClockIn()
            list.clear()
            list.addAll(model.getTimes())
            adapter.notifyDataSetChanged()

        }

    }

}