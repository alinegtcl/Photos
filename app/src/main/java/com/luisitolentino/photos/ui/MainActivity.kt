package com.luisitolentino.photos.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.luisitolentino.photos.R
import com.luisitolentino.photos.adapter.ProductAdapter
import com.luisitolentino.photos.databinding.ActivityMainBinding
import com.luisitolentino.photos.model.DummyJSONAPI
import com.luisitolentino.photos.model.Product

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val productList: MutableList<Product> = mutableListOf()
    private val productAdapter: ProductAdapter by lazy {
        ProductAdapter(this, productList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        setSupportActionBar(amb.mainTb.apply {
            title = getString(R.string.app_name)
        })

        amb.productsSp.apply {
            adapter = productAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    retrieveProductImages(productList[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // NSA
                }
            }

        }

        retrieveProducts()
    }

    private fun retrieveProducts() =
        DummyJSONAPI.ProductListRequest({ productList ->
            productList.products.also {
                productAdapter.addAll(it)
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

    private fun retrieveProductImages(product: Product) {
//        product.images.forEach { imageUrl ->
//            ImageRequest(
//                imageUrl,
//                { response ->
//                },
//                0,
//                0,
//                ImageView.ScaleType.CENTER,
//                Bitmap.Config.ARGB_8888,
//                {
//                    Toast.makeText(
//                        this,
//                        getString(R.string.request_problem),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }).also {
//                DummyJSONAPI.getInstance(this).addToRequestQueue(it)
//            }
//        }
    }
}