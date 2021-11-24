package kr.ac.kumoh.s20190101.gunplaapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kr.ac.kumoh.s20190101.gunplaapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val model: GunplaViewModel  by viewModels()
    private lateinit var adapter: GunplaAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = GunplaAdapter(model) { mechanic -> adapterOnClick(mechanic) }
        binding.list.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            adapter = this@MainActivity.adapter
        }

        model.list.observe(this) {
            adapter.notifyDataSetChanged()
        }

        model.requestMechanic()
    }

    private fun adapterOnClick(mechanic: GunplaViewModel.Mechanic) {
        //Toast.makeText(this, mechanic.model, Toast.LENGTH_SHORT).show()
        val uri = Uri.parse("https://www.youtube.com/results?search_query=${mechanic.model}")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}