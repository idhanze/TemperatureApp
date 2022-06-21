package com.example.temperatureapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.temperatureapp.data.model.WeatherModel
import com.example.temperatureapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(val service: WeatherService) {
    
    fun getCurrentWeather(key: String, location: String): LiveData<Resource<WeatherModel>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading())
            
            try {
                service.getCurrentWeather(key, location).let {
                    if(it.isSuccessful){
                        if(it.body() != null){
                            it.body()?.let { weather ->
                                emit(Resource.success(weather))
                            }
                        } else {
                            emit(Resource.error("Data not found"))
                        }
            
                    } else {
                        emit(Resource.error(it.message()))
                    }
                }
            } catch (e: Exception){
                emit(Resource.error(e.message.toString()))
            }
        }
}