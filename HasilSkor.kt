package com.example.geoboost

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.geoboost.databinding.ActivityHasilSkorBinding

class HasilSkor : AppCompatActivity() {

    private lateinit var binding: ActivityHasilSkorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHasilSkorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val score = intent.getIntExtra("SCORE", 0)
        binding.scoreCurrentValue.text = score.toString()

        binding.buttonHome.setOnClickListener {
            val intent = Intent(this, Beranda::class.java)
            startActivity(intent)
            finish()
        }

        binding.buttonRetry.setOnClickListener {
            val intent = Intent(this, Soal::class.java)
            startActivity(intent)
            finish()
        }
    }
}
