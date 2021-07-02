package com.mukesh.wikiapp.view.fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mukesh.wikiapp.databinding.FragmentWebviewBinding
import com.mukesh.wikiapp.view.activity.MainActivity
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException


class WebViewFragment : Fragment() {

    private lateinit var fragBinding: FragmentWebviewBinding
    private lateinit var url: String

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragBinding = FragmentWebviewBinding.inflate(layoutInflater)
        val title = arguments?.getSerializable("title") as String
        url = "https://en.m.wikipedia.org/wiki/$title"
        fragBinding.wbDetails.settings.javaScriptEnabled = true
        MyAsyncTask().execute()

        fragBinding.wbDetails.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK

        val actionBar = (activity as MainActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = title.replace("_", " ")

        fragBinding.wbDetails.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                url = request.url.toString()
                MyAsyncTask().execute()
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                actionBar?.title =
                    url?.replace("https://en.m.wikipedia.org/wiki/", "")?.replace("_", " ")
            }
        }

        setHasOptionsMenu(true)

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(requireActivity(), object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (fragBinding.wbDetails.canGoBack())
                        fragBinding.wbDetails.goBack()
                    else
                        findNavController().popBackStack()
                }
            }
            )

        return fragBinding.root
    }

    @SuppressLint("StaticFieldLeak")
    inner class MyAsyncTask :
        AsyncTask<Void?, Void?, Document?>() {

        override fun doInBackground(vararg p0: Void?): Document? {
            var document: Document? = null
            try {
                document = Jsoup.connect(url).get()
                document.getElementsByClass("header-container header-chrome").remove()
                document.getElementsByClass("banner-container").remove()
                document.getElementsByClass("pre-content heading-holder").remove()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return document
        }

        override fun onPostExecute(document: Document?) {
            super.onPostExecute(document)
            if (fragBinding.wbDetails.canGoBack())
                fragBinding.wbDetails.goBack()
            fragBinding.wbDetails.loadDataWithBaseURL(
                url,
                document.toString(),
                "text/html",
                "utf-8",
                ""
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (fragBinding.wbDetails.canGoBack())
                fragBinding.wbDetails.goBack()
            else
                requireActivity().onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}