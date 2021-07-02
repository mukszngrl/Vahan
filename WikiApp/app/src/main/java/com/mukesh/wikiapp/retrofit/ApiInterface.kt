package com.mukesh.wikiapp.retrofit

import com.mukesh.wikiapp.model.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("api.php")
    fun getSearchList(
        @Query("action") action: String = "query",
        @Query("formatversion") formatVersion: Int = 2,
        @Query("generator") generator: String = "prefixsearch",
        @Query("gpssearch") gpsSearch: String,
        @Query("gpslimit") gpsLimit: Int = 20,
        @Query("prop") prop: String = "pageimages|pageterms",
        @Query("piprop") piProp: String = "thumbnail",
        @Query("pithumbsize") piThumbSize: Int = 50,
        @Query("pilimit") piLimit: Int = 20,
        @Query("redirects") reDirects: String = "",
        @Query("wbptterms") wbptTerms: String="description",
        @Query("format") format: String="json"
    ): Call<SearchResponse>
}