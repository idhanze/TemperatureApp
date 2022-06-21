package com.example.temperatureapp.ui.resultActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.temperatureapp.data.model.Current
import com.example.temperatureapp.databinding.ActivityResultBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultActivity : AppCompatActivity() {
    
    companion object{
        const val KEY_DATA = "data"
        const val KEY_LOCATION = "location"
    }
    
    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val currentTemperature = intent.extras?.getParcelable<Current>(KEY_DATA)
        val city = intent.extras?.getString(KEY_LOCATION, "")
    
        with(binding){
            btnBack.setOnClickListener { finish() }
            tvLocation.text = city
            
            currentTemperature?.let {
                tvCelcius.text = "${it.temp_c}°C"
                tvFahrenheit.text = "${it.temp_f}°F"
                
                it.condition?.let { condition ->
                    tvCondition.text = condition.text
                    
                    Glide.with(this@ResultActivity)
                        .load(condition.icon)
                        .into(icon)
                }
            }
            
        }
        
    }
}