package com.example.temperatureapp.ui.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.temperatureapp.data.Repository
import com.example.temperatureapp.data.model.WeatherModel
import com.example.temperatureapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    fun getWeather(key: String, location: String): LiveData<Resource<WeatherModel>>{
        return repository.getCurrentWeather(key, location)
    }
}