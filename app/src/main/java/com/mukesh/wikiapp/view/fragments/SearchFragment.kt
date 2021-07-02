package com.mukesh.wikiapp.view.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mukesh.wikiapp.R
import com.mukesh.wikiapp.databinding.FragmentSearchBinding
import com.mukesh.wikiapp.view.activity.MainActivity
import com.mukesh.wikiapp.view.adapter.SearchResultAdapter
import com.mukesh.wikiapp.viewmodel.SearchViewModel

class SearchFragment : Fragment() {

    private lateinit var fragBinding: FragmentSearchBinding
    private lateinit var searchResultAdapter: SearchResultAdapter
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragBinding = FragmentSearchBinding.inflate(layoutInflater)

        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        fragBinding.rvSearchResult.layoutManager = LinearLayoutManager(requireActivity())
        searchResultAdapter = SearchResultAdapter(requireActivity())
        fragBinding.rvSearchResult.adapter = searchResultAdapter

        val actionBar = (activity as MainActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)
        actionBar?.title = "Search"

        getSearchResult(fragBinding.etSearch.text.toString())

        fragBinding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(txt: Editable?) {
                getSearchResult(txt.toString())
            }
        })

        fragBinding.ivClear.setOnClickListener {
            if (fragBinding.ivClear.tag == "true") {
                searchClear()
                fragBinding.etSearch.setText("")
            }
        }
        return fragBinding.root
    }

    private fun getSearchResult(txt: String) {
        if (!TextUtils.isEmpty(txt)) {
            fragBinding.tvDataNotFound.visibility = View.GONE
            fragBinding.rvSearchResult.visibility = View.VISIBLE
            fragBinding.ivClear.setImageResource(R.drawable.ic_clear)
            fragBinding.ivClear.tag = "true"
            searchViewModel.getSearchResult(txt)
                ?.observe(viewLifecycleOwner, { finalResult ->
                    finalResult?.let {
                        finalResult.query?.pages?.let { it1 ->
                            searchResultAdapter.refreshList(it1)
                        }
                    }
                })
        } else {
            searchClear()
        }
    }

    private fun searchClear(){
        fragBinding.ivClear.setImageResource(R.drawable.ic_search)
        fragBinding.ivClear.tag = "false"
        searchResultAdapter.refreshList(emptyList())
        fragBinding.tvDataNotFound.visibility = View.VISIBLE
        fragBinding.rvSearchResult.visibility = View.GONE
        fragBinding.tvDataNotFound.text = "Search result comes here"
    }
}