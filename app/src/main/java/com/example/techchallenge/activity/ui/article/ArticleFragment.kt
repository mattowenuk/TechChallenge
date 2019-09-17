package com.example.techchallenge.activity.ui.article

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.techchallenge.R
import com.example.techchallenge.request.PostEvent
import com.example.techchallenge.request.RequestPostStat


class ArticleFragment : Fragment() {

    private var articleUrl = ""

    companion object {
        fun newInstance(url: String) =
            ArticleFragment().apply {
                arguments = Bundle().apply {
                    putString("url", url)
                }
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        arguments?.getString("url")?.let {
            articleUrl = it
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_article, container, false)
        val urlWebView: WebView = view.findViewById(R.id.articleWebView)

        var started = 0L

        urlWebView.loadUrl("$articleUrl.app")
        urlWebView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)

                //send stat to endpoint
                Log.i("mko", "Start page load")
                val data = context?.let { context -> RequestPostStat(context, PostEvent.DISPLAY).post() }
                started = data?.toLong() ?: 0
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                //send stat to endpoint
                Log.i("mko", "Finish page load")
                val data = context?.let { context -> RequestPostStat(context, PostEvent.DISPLAY).post() }
                val finished = data?.toLong() ?: 0
                Log.i("mko", "load duration: " + (finished - started).toString() + " ms")
            }
        }

        return view
    }
}