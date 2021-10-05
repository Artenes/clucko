package io.github.artenes.clucko

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.*
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db =
            Room.databaseBuilder(this, AppDatabase::class.java, "cluckodb").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build()

        val list = mutableListOf<String>()
        list.addAll(getTimes())
        val adapter = ClockInsAdapter(list)
        val rv = findViewById<RecyclerView>(R.id.rvClockIns)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)

        findViewById<FloatingActionButton>(R.id.fabClockIn).setOnClickListener {

            //https://www.rockandnull.com/java-time-android/
            //https://stackoverflow.com/questions/32437550/whats-the-difference-between-instant-and-localdatetime
            val date = Instant.now()
            db.clockInDao().insert(ClockIn(date.toEpochMilli()))
            list.clear()
            list.addAll(getTimes())
            adapter.notifyDataSetChanged()

        }

    }

    private fun getTimes(): List<String> =
        db.clockInDao().getAll().map {
            Instant
                .ofEpochMilli(it)
                .atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("HH:mm"))
        }
}