package com.example.techchallenge.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.techchallenge.R
import com.example.techchallenge.adapter.DataListAdapter
import com.example.techchallenge.data.DataResult
import com.example.techchallenge.mapper.DataMapper
import com.example.techchallenge.request.RequestData
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataList.layoutManager = LinearLayoutManager(this)

        doAsync {
            val titleList = RequestData().execute()

            uiThread {
                Log.i("mko", titleList.toString())
                dataList.adapter = DataListAdapter(titleList)
            }
        }
    }
}
