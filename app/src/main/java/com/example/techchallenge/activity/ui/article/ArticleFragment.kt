package com.example.techchallenge.activity.ui.article

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.techchallenge.R


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

        //setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_article, container, false)
        val urlWebView: WebView = view.findViewById(R.id.articleWebView)

        urlWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        urlWebView.loadUrl("$articleUrl.app")

        return view
    }
}
