package com.mukesh.wikiapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mukesh.wikiapp.model.SearchResponse
import com.mukesh.wikiapp.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SearchRepository {

    val searchResponse = MutableLiveData<SearchResponse>()

    fun getServicesApiCall(searchText : String): MutableLiveData<SearchResponse> {

        val call = RetrofitClient.apiInterface.getSearchList(gpsSearch = searchText)

        call.enqueue(object : Callback<SearchResponse> {
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                Log.v("DEBUG : ", response.body().toString())

                val data = response.body()

                searchResponse.value = data!!
            }
        })

        return searchResponse
    }
}