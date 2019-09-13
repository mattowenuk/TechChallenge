package com.example.techchallenge.activity.ui.allsport

import android.os.Bundle
import com.example.techchallenge.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

class AllSportFragment : Fragment() {

    private lateinit var allsportViewModel: AllSportViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        allsportViewModel =
            ViewModelProviders.of(this).get(AllSportViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_all_sport, container, false)
        val textView: TextView = root.findViewById(R.id.text_all_sport)
        allsportViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}