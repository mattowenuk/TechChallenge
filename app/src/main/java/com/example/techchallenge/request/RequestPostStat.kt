package com.example.techchallenge.request

import android.content.Context
import android.util.Log
import com.example.techchallenge.R
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.util.*
import kotlin.collections.HashMap
import kotlin.concurrent.timer

class RequestPostStat(private val context: Context, private val postEvent: PostEvent) {

    //OkHttp used to post stats
    private val client = OkHttpClient()
    
    fun post(message: String = context.getString(R.string.stat_error)) : String {

        //post time is stored
        val time = Date().time.toString()

        //error posts use the optional parameter, else the time is used
        val data = when(postEvent) {
            PostEvent.ERROR -> message
            else -> time
        }

        //the url is built with the query parameters
        val urlBuilder = HttpUrl.parse(context.getString(R.string.endpoint_url))?.newBuilder()
        urlBuilder?.addQueryParameter(context.getString(R.string.stat_event), postEvent.event)
        urlBuilder?.addQueryParameter(context.getString(R.string.stat_data), data)
        val url = urlBuilder?.build().toString()

        //build the post body using gson to convert a map to json containing event and data
        val mediaType = MediaType.parse(context.getString(R.string.json_media_type))
        val map: HashMap<String, String> = 
            hashMapOf(context.getString(R.string.stat_event) to postEvent.event, context.getString(R.string.stat_data) to data)
        val body = Gson().toJson(map)
        val requestBody = RequestBody.create(mediaType, body)

        //build the request
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        //begin the request call asynchronously
        Log.i("mko", "Stat post $time: $url")
        client.newCall(request).enqueue(object : Callback {

            //process the result of the call
            override fun onFailure(call: Call, e: IOException) {
                Log.i("mko", "Stat post $time failure: $e")
            }
            override fun onResponse(call: Call, response: Response) {
                Log.i("mko", "Stat post $time received")
            }
        })

        return data
    }
}