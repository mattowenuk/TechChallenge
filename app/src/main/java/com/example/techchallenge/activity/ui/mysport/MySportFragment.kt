package com.example.techchallenge.activity.ui.mysport

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.techchallenge.R
import com.example.techchallenge.activity.ui.article.ArticleFragment
import com.example.techchallenge.adapter.DataListAdapter
import com.example.techchallenge.request.PostEvent
import com.example.techchallenge.request.RequestData
import com.example.techchallenge.request.RequestPostStat
import kotlinx.android.synthetic.main.fragment_my_sport.*
import kotlinx.android.synthetic.main.fragment_my_sport.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException
import java.util.*

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

        root.articleList.layoutManager = LinearLayoutManager(context)

        doAsync {
            val titleList = RequestData().execute()

            uiThread {

                articleList.adapter = DataListAdapter(titleList) {
                    Log.i("mko", it.title)

                    //replace fragment with webView fragment

                    if (savedInstanceState == null) {
                        val fragment = ArticleFragment.newInstance(it.url)
                        fragmentManager?.beginTransaction()
                            ?.replace(R.id.nav_host_fragment, fragment, fragment.javaClass.simpleName)
                            ?.addToBackStack(fragment.javaClass.simpleName)
                            ?.commit()

                        //send stat to endpoint
                        Log.i("mko", "Replace with ${fragment.javaClass.simpleName}")
                        context?.let { context -> RequestPostStat(context, PostEvent.DISPLAY).post() }

                        //send stat to endpoint
                        //context?.let { context -> RequestPostStat(context, PostEvent.ERROR).post("it's all gone to pot") }
                    }
                }
            }
        }

        return root
    }
}