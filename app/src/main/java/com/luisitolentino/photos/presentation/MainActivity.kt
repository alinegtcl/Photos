package com.luisitolentino.photos.presentation

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.ImageRequest
import com.luisitolentino.photos.R
import com.luisitolentino.photos.data.repository.DummyJSONAPI
import com.luisitolentino.photos.databinding.ActivityMainBinding
import com.luisitolentino.photos.domain.entity.Photo
import com.luisitolentino.photos.presentation.adapter.PhotoAdapter

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val photoList: MutableList<Photo> = mutableListOf()
    private val photoAdapter: PhotoAdapter by lazy {
        PhotoAdapter(this, photoList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        sutupAppBar()
        setupRecycler()

        // Chamar view model para trazer lista de fotos
        retrievePhotosList()
    }

    private fun sutupAppBar() {
        setSupportActionBar(binding.mainTb.apply {
            title = getString(R.string.app_name)
        })
    }

    private fun setupRecycler() {
        binding.photosSp.apply {
            adapter = photoAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    // Chamar viewModel para requisição de imagem
                    retrieveImage(photoList[position].thumbnailUrl, binding.imageThumbnail)
                    retrieveImage(photoList[position].image, binding.imagePhoto)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

    private fun retrievePhotosList() =
        DummyJSONAPI.PhotoListRequest({ photoList ->
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

    private fun retrieveImage(imageUrl: String, view: ImageView) {
        ImageRequest(
            imageUrl,
            { response ->
                view.setImageBitmap(response)
            },
            0,
            0,
            ImageView.ScaleType.CENTER,
            Bitmap.Config.ARGB_8888,
            {
                Toast.makeText(
                    this,
                    getString(R.string.request_problem),
                    Toast.LENGTH_SHORT
                ).show()
            }).also {
            DummyJSONAPI.getInstance(this).addToRequestQueue(it)
        }
    }
}