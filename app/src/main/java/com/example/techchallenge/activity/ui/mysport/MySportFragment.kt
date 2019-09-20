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
import com.example.techchallenge.model.Item
import com.example.techchallenge.model.ModelResult
import com.example.techchallenge.request.PostEvent
import com.example.techchallenge.request.RequestData
import com.example.techchallenge.request.RequestPostStat
import kotlinx.android.synthetic.main.fragment_my_sport.*
import kotlinx.android.synthetic.main.fragment_my_sport.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MySportFragment : Fragment() {

    private lateinit var mySportViewModel: MySportViewModel
    private var savedState: Bundle? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        savedState = savedInstanceState

        //load the title from the viewModel
        mySportViewModel = ViewModelProviders.of(this).get(MySportViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_my_sport, container, false)
        val textView: TextView = root.findViewById(R.id.text_my_sport)
        mySportViewModel.text.observe(this, Observer {
            textView.text = it
        })

        //set the manager for the recyclerView
        root.articleList.layoutManager = LinearLayoutManager(context)

        doAsync {

            //try to request the data
            var modelResult: ModelResult? = null
            var exception: Exception? = null
            try {
                modelResult = context?.let { RequestData().execute(it) }
            }
            catch(e: Exception){
                exception = e
            }

            uiThread {
                when {
                    //error has occurred...
                    exception != null -> {
                        //send stat to endpoint
                        Log.i("mko", "Error: $exception")
                        context?.let { context -> RequestPostStat(context, PostEvent.ERROR).post(exception.toString()) }
                    }

                    //data has been found and mapped to a list of the model data and sent to the adapter
                    modelResult != null -> articleList.adapter = DataListAdapter(modelResult) { item : Item -> itemClicked(item) }

                    //data has not been found
                    else -> {
                        //send stat to endpoint
                        Log.i("mko", "Error: Data not found")
                        context?.let { context -> RequestPostStat(context, PostEvent.ERROR).post("Data not found") }
                    }
                }
            }
        }

        return root
    }

    //function called by the item click listener of the recyclerView
    private fun itemClicked(item : Item) {
        //log the article title
        Log.i("mko", "Title: " + item.title)

        //replace fragment with webView fragment
        if (savedState == null) {
            val fragment = ArticleFragment.newInstance(item.url)
            fragmentManager?.beginTransaction()
                ?.replace(
                    R.id.nav_host_fragment,
                    fragment,
                    fragment.javaClass.simpleName
                )
                ?.addToBackStack(fragment.javaClass.simpleName)
                ?.commit()

            //send stat to endpoint
            Log.i("mko", "Replace with ${fragment.javaClass.simpleName}")
            context?.let { context -> RequestPostStat(context, PostEvent.DISPLAY).post() }
        }
    }
}