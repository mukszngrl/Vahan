package com.mukesh.wikiapp.model

data class SearchResponse (
    val batchcomplete: Boolean,

    val query: Query?
)

data class Query (
    val pages: List<Page>
)

data class Page (
    val pageid: Long,
    val ns: Long,
    val title: String,
    val index: Long,
    val thumbnail: Thumbnail?,
    val terms: Terms?
)

data class Terms (
    val description: List<String>
)

data class Thumbnail (
    val source: String,
    val width: Long,
    val height: Long
)