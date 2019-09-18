package com.example.techchallenge.activity.ui.article

import android.content.Context
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.fragment.app.Fragment
import com.example.techchallenge.R
import com.example.techchallenge.request.PostEvent
import com.example.techchallenge.request.RequestPostStat


class ArticleFragment : Fragment() {

    private var articleUrl = ""

    //new instance of the class receives a url as a string and puts it in the arguments bundle
    companion object {
        fun newInstance(url: String) =
            ArticleFragment().apply {
                arguments = Bundle().apply {
                    putString(getString(R.string.url), url) }
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        //when the fragment is attached the url is retrieved from the arguments and stored as a property
        arguments?.getString(getString(R.string.url))?.let {
            articleUrl = it
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //view inflated and the webView found
        val view = inflater.inflate(R.layout.fragment_article, container, false)
        val urlWebView: WebView = view.findViewById(R.id.articleWebView)

        //long variable to track the article load duration
        var started = 0L

        //loads url and detects events
        urlWebView.loadUrl("$articleUrl.app")
        urlWebView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)

                //send stat to endpoint
                Log.i("mko", "Start page load")
                val data = context?.let { context -> RequestPostStat(context, PostEvent.LOAD).post() }

                //resets the start time
                started = data?.toLong() ?: 0
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                //send stat to endpoint
                Log.i("mko", "Finish page load")
                val data = context?.let { context -> RequestPostStat(context, PostEvent.DISPLAY).post() }

                //sets the finish time and displays the difference
                val finished = data?.toLong() ?: 0
                Log.i("mko", "Load duration: " + (finished - started).toString() + " ms")
            }

            //when errors occur...
            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                //send stat to endpoint
                Log.i("mko", "Error: ${error?.description.toString()}")
                context?.let { context -> RequestPostStat(context, PostEvent.ERROR).post(error?.description.toString()) }

                super.onReceivedError(view, request, error)
            }

            override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
                val error = "Code: ${errorResponse?.statusCode}: ${errorResponse?.reasonPhrase}"

                //send stat to endpoint
                Log.i("mko", "HttpError: $error")
                context?.let { context -> RequestPostStat(context, PostEvent.ERROR).post(error) }

                super.onReceivedHttpError(view, request, errorResponse)
            }
        }

        return view
    }
}