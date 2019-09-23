package com.example.techchallenge.request

import android.content.Context
import com.example.techchallenge.R
import com.example.techchallenge.model.ModelResult
import com.example.techchallenge.model.ModelResultException
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class RequestSchedule {

    fun begin(context: Context, returnResult: (result: ModelResultException?) -> Unit) {
        val urls = listOf(context.getString(R.string.data_url_1),
            context.getString(R.string.data_url_2),
            context.getString(R.string.data_url_3))

        val delay = 4000L
        urls.forEach {
            doAsync {

                Thread.sleep(delay * urls.indexOf(it))

                //try to request the data
                var modelResult: ModelResult? = null
                var exception: Exception? = null
                try {
                    modelResult = RequestData().execute(it)
                } catch (e: Exception) {
                    exception = e
                }

                uiThread {
                    returnResult(ModelResultException(modelResult, exception))
                }
            }
        }
    }
}