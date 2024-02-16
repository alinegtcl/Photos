package com.luisitolentino.photos.presentation.adapter

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.luisitolentino.photos.domain.entity.Photo

class PhotoAdapter(
    private val activityContext: Context,
    private val photoList: MutableList<Photo>
) : ArrayAdapter<Photo>(activityContext, R.layout.simple_list_item_1, photoList) {
    private data class PhotoHolder(val photoTitleTV: TextView)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val photoView = convertView ?: LayoutInflater.from(activityContext)
            .inflate(R.layout.simple_list_item_1, parent, false).apply {
                tag = PhotoHolder(findViewById(R.id.text1))
            }

        (photoView.tag as PhotoHolder).photoTitleTV.text = photoList[position].title

        return photoView
    }
}