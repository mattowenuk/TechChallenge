package com.example.techchallenge.activity.ui.mysport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.techchallenge.R

class MySportFragment : Fragment() {

    private lateinit var mySportViewModel: MySportViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mySportViewModel =
            ViewModelProviders.of(this).get(MySportViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_my_sport, container, false)
        val textView: TextView = root.findViewById(R.id.text_my_sport)
        mySportViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}