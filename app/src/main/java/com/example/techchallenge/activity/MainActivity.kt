package com.example.techchallenge.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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
    }

    //splash activity on first time only
    private var firstTime = true

    override fun onResume() {
        super.onResume()

        if(firstTime) {
            doAsync {
                Thread.sleep(2000)
                uiThread {
                    startActivity<NavigationActivity>()
                    firstTime = false
                }
            }
        } else {
            finish()
        }
    }
}
