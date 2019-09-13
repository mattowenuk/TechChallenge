package com.example.techchallenge.activity.ui.mysport

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MySportViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Formula 1"
    }
    val text: LiveData<String> = _text
}