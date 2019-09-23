package com.example.techchallenge.activity.ui.mysport

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.techchallenge.R
import com.example.techchallenge.activity.ui.article.ArticleFragment
import com.example.techchallenge.adapter.DataListAdapter
import com.example.techchallenge.model.Item
import com.example.techchallenge.request.PostEvent
import com.example.techchallenge.request.RequestPostStat
import kotlinx.android.synthetic.main.fragment_my_sport.*
import kotlinx.android.synthetic.main.fragment_my_sport.view.*

class MySportFragment : Fragment() {

    private lateinit var mySportViewModel: MySportViewModel
    private var savedState: Bundle? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        savedState = savedInstanceState

        val root = inflater.inflate(R.layout.fragment_my_sport, container, false)

        //set the manager for the recyclerView and setup ui elements
        root.articleList.layoutManager = LinearLayoutManager(context)
        root.nextImage.visibility = View.INVISIBLE
        root.progressBar.visibility = View.VISIBLE

        //get the viewModel and use factory to send the context as a parameter
        val c = context
        if(c != null) {
            mySportViewModel = getViewModel { MySportViewModel(c) }
        }

        //set an observer for result of the data request using live data
        mySportViewModel.modelResultException.observe(this, Observer {
            if (it != null) {
                when {
                    //error has occurred...
                    it.exception != null -> {
                        //send stat to endpoint
                        Log.i("mko", "Error: $it.exception")
                        context?.let { context -> RequestPostStat(context, PostEvent.ERROR).post(it.exception.toString())
                        }
                    }

                    //data has been found and mapped to a list of the model data and sent to the adapter
                    it.modelResult != null -> {
                        //an item click listener is sent to the adapter
                        articleList.adapter = DataListAdapter(it.modelResult) { item: Item -> itemClicked(item) }
                        mySportTextView.text = it.modelResult.topicTitle
                        Log.i("mko", "Data updated")

                        root.nextImage.visibility = View.VISIBLE
                        root.progressBar.visibility = View.INVISIBLE
                    }

                    //data has not been found
                    else -> {
                        //send stat to endpoint
                        Log.i("mko", "Error: Data not found")
                        context?.let { context -> RequestPostStat(context, PostEvent.ERROR).post("Data not found")
                        }
                    }
                }
            }
        } )
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