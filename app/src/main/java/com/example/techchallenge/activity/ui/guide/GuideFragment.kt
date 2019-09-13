package com.example.techchallenge.activity.ui.guide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.techchallenge.R

class GuideFragment : Fragment() {

    private lateinit var guideViewModel: GuideViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        guideViewModel =
            ViewModelProviders.of(this).get(GuideViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_guide, container, false)
        val textView: TextView = root.findViewById(R.id.text_guide)
        guideViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}