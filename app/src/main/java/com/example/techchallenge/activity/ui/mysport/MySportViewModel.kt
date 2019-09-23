package com.example.techchallenge.activity.ui.mysport

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.techchallenge.model.ModelResult
import com.example.techchallenge.model.ModelResultException
import com.example.techchallenge.request.RequestSchedule

class MySportViewModel(context: Context) : ViewModel() {

    private val liveData = MutableLiveData<ModelResultException?>().apply {
        value = null
        RequestSchedule().begin(context) {
            value = it
        }
    }
    val modelResultException: LiveData<ModelResultException?> = liveData
}