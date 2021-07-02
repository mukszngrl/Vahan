package com.mukesh.wikiapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mukesh.wikiapp.model.SearchResponse
import com.mukesh.wikiapp.repository.SearchRepository

class SearchViewModel : ViewModel() {

    var searchResponse: MutableLiveData<SearchResponse>? = null

    fun getSearchResult(searchText: String): LiveData<SearchResponse>? {
        searchResponse = SearchRepository.getServicesApiCall(searchText)
        return searchResponse
    }
}