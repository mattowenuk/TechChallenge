package com.example.techchallenge.request

import android.content.Context
import com.example.techchallenge.R
import com.example.techchallenge.data.DataResult
import com.example.techchallenge.mapper.DataMapper
import com.example.techchallenge.model.ModelResult
import com.google.gson.Gson
import java.net.URL

class RequestData {

    fun execute(context: Context) : ModelResult {
        //json string retrieved from url
        val forecastJsonStr = URL(context.getString(R.string.data_url)).readText()
        //gson used to convert the json to data classes
        val dataResult = Gson().fromJson(forecastJsonStr, DataResult::class.java)
        //the data is mapped to the model and returned
        return DataMapper().convertFromDataToModel(dataResult)
    }
}