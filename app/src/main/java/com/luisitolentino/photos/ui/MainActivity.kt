package com.luisitolentino.photos.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.luisitolentino.photos.R
import com.luisitolentino.photos.adapter.PhotoAdapter
import com.luisitolentino.photos.databinding.ActivityMainBinding
import com.luisitolentino.photos.model.DummyJSONAPI
import com.luisitolentino.photos.model.Photo

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val photoList: MutableList<Photo> = mutableListOf()
    private val photoAdapter: PhotoAdapter by lazy {
        PhotoAdapter(this, photoList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        setSupportActionBar(amb.mainTb.apply {
            title = getString(R.string.app_name)
        })

        amb.photosSp.apply {
            adapter = photoAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    retrieveImage(photoList[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // NSA
                }
            }

        }

        retrievePhotosList()
    }

    private fun retrievePhotosList() =
        DummyJSONAPI.PhototListRequest({ photoList ->
            photoList.also {
                photoAdapter.addAll(it)
            }
        }, {
            Toast.makeText(
                this,
                getString(R.string.request_problem),
                Toast.LENGTH_SHORT
            ).show()
        }).also {
            DummyJSONAPI.getInstance(this).addToRequestQueue(it)
        }

    private fun retrieveImage(item: Photo) {
        // implementar chamada para exibir as fotos
    }
}