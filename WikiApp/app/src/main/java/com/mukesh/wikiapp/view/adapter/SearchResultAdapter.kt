package com.mukesh.wikiapp.view.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mukesh.wikiapp.R
import com.mukesh.wikiapp.databinding.SearchResultCellBinding
import com.mukesh.wikiapp.model.Page

class SearchResultAdapter(private val activity: Activity) :
    RecyclerView.Adapter<SearchResultAdapter.MyViewHolder>() {

    private var arrSearchResult: List<Page> = listOf()

    class MyViewHolder(view: SearchResultCellBinding) : RecyclerView.ViewHolder(view.root) {
        val ivPic = view.ivPic
        val tvTitle = view.tvTitle
        val tvDescription = view.tvDescription
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: SearchResultCellBinding =
            SearchResultCellBinding.inflate(LayoutInflater.from(activity), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val page = arrSearchResult[position]

        page.let {
            holder.tvTitle.text = it.title
            holder.tvDescription.text = it.terms?.description?.get(0) ?: ""

            Glide
                .with(activity)
                .load(it.thumbnail?.source)
                .centerCrop()
                .placeholder(R.drawable.ic_place_holder)
                .into(holder.ivPic)

            holder.itemView.setOnClickListener { view ->
                val bundle = bundleOf(
                    "title" to it.title.replace(" ", "_")
                )
                view.findNavController()
                    .navigate(R.id.action_searchFragment_to_webViewFragment, bundle)
            }
        }
    }

    override fun getItemCount(): Int {
        return arrSearchResult.size
    }

    fun refreshList(arrSearchResult: List<Page>) {
        this.arrSearchResult = arrSearchResult;
        notifyDataSetChanged()
    }
}