package com.example.techchallenge.request

import android.content.Context
import android.util.Log
import com.example.techchallenge.R
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.util.*
import kotlin.collections.HashMap

class RequestPostStat(private val context: Context, private val postEvent: PostEvent) {

    private val client = OkHttpClient()
    
    fun post(message: String = context.getString(R.string.stat_error)) : String {

        val data = when(postEvent) {
            PostEvent.ERROR -> message
            else -> Date().time.toString()
        }

        val urlBuilder = HttpUrl.parse(context.getString(R.string.endpoint_url))?.newBuilder()
        urlBuilder?.addQueryParameter(context.getString(R.string.stat_event), postEvent.event)
        urlBuilder?.addQueryParameter(context.getString(R.string.stat_data), data)
        val url = urlBuilder?.build().toString()

        Log.i("mko", "Stat post: $url")

        val mediaType = MediaType.parse(context.getString(R.string.json_media_type))
        val map: HashMap<String, String> = 
            hashMapOf(context.getString(R.string.stat_event) to postEvent.event, context.getString(R.string.stat_data) to data)
        val body = Gson().toJson(map)
        val requestBody = RequestBody.create(mediaType, body)

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i("mko", "message: $e")
            }
            override fun onResponse(call: Call, response: Response) {
                //Log.i("mko", response.body()?.string().toString())
            }
        })

        return data
    }
}