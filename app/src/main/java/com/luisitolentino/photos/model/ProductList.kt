package com.luisitolentino.photos.model

data class ProductList(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)