package com.example.techchallenge.activity.ui.allsport

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AllSportViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "All Sport"
    }
    val text: LiveData<String> = _text
}