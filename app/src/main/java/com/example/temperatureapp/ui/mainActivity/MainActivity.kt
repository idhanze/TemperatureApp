package com.example.temperatureapp.ui.mainActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.temperatureapp.R
import com.example.temperatureapp.databinding.ActivityMainBinding
import com.example.temperatureapp.ui.resultActivity.ResultActivity
import com.example.temperatureapp.ui.resultActivity.ResultActivity.Companion.KEY_DATA
import com.example.temperatureapp.ui.resultActivity.ResultActivity.Companion.KEY_LOCATION
import com.example.temperatureapp.utils.LoadingDialog
import com.example.temperatureapp.utils.RemoveErrorTextWatcher
import com.example.temperatureapp.utils.Resource
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var loadingDialog: LoadingDialog
    private val viewModel: MainViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        loadingDialog = LoadingDialog(this)
        
        with(binding){
            edtKey.addTextChangedListener(RemoveErrorTextWatcher(layoutKey))
            edtCity.addTextChangedListener(RemoveErrorTextWatcher(layoutCity))
            
            val array = resources.getStringArray(R.array.list_city)
            val arrayAdapter = ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_dropdown_item_1line, array)
            edtCity.setAdapter(arrayAdapter)
            
            btnCheck.setOnClickListener {
                checkTemperature()
            }
        }
    }
    
    private fun checkTemperature(){
        with(binding){
            val key = edtKey.text.toString()
            val city = edtCity.text.toString()
            
            
            if(checkError(key, layoutKey, "Key cannot be empty") ||
                checkError(city, layoutCity, "City cannot be empty")){
                Toast.makeText(this@MainActivity, "Please check your input", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.getWeather(key, city).observe(this@MainActivity){
                    when(it.status){
                        Resource.Status.LOADING -> {
                            loadingDialog.show()
                        }
                        
                        Resource.Status.ERROR -> {
                            loadingDialog.dismiss()
                            Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
                        }
                        
                        Resource.Status.SUCCESS -> {
                            loadingDialog.dismiss()
                            val current = it.data?.current
                            val intent = Intent(this@MainActivity, ResultActivity::class.java)
                            intent.putExtra(KEY_LOCATION, city)
                            intent.putExtra(KEY_DATA, current)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
        
    }
    
    private fun checkError(s: String, layout: TextInputLayout, errorMessage: String): Boolean{
        if(s.isEmpty()){
            layout.error = errorMessage
            return true
        }
        return false
    }
}