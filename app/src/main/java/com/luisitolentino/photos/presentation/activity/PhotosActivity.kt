package com.luisitolentino.photos.presentation.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.luisitolentino.photos.R
import com.luisitolentino.photos.databinding.ActivityPhotosBinding
import com.luisitolentino.photos.domain.entity.Photo
import com.luisitolentino.photos.presentation.adapter.PhotoAdapter
import com.luisitolentino.photos.presentation.viewmodel.PhotoState
import com.luisitolentino.photos.presentation.viewmodel.PhotosViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotosActivity : AppCompatActivity() {

    private val binding: ActivityPhotosBinding by lazy {
        ActivityPhotosBinding.inflate(layoutInflater)
    }
    private lateinit var photoAdapter: PhotoAdapter
    private val viewModel: PhotosViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupAppBar()
        viewModel.retrievePhotosList()
        setupViewModel()
    }

    private fun setupAppBar() {
        setSupportActionBar(binding.mainTb.apply {
            title = getString(R.string.app_name)
        })
    }

    private fun setupRecycler(list: List<Photo>) {
        photoAdapter = PhotoAdapter(this@PhotosActivity, list)
        binding.photosSp.apply {
            adapter = photoAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    retrieveImages(list[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

    private fun retrieveImages(photo: Photo) {
        viewModel.retrieveThumbnail(photo.thumbnailUrl)
        viewModel.retrieveImage(photo.image)
    }

    private fun setupViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    PhotoState.HideLoading -> binding.loadingPhotos.visibility = View.GONE
                    PhotoState.ShowLoading -> binding.loadingPhotos.visibility = View.VISIBLE
                    is PhotoState.ImageSuccess -> binding.imagePhoto.setImageBitmap(it.image)
                    is PhotoState.ThumbnailSuccess -> binding.imageThumbnail.setImageBitmap(it.image)
                    is PhotoState.PhotoSuccess -> {
                        setupRecycler(it.list)
                    }

                    is PhotoState.PhotoError -> Toast.makeText(
                        this@PhotosActivity,
                        it.error,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}