package com.luisitolentino.photos.framework.datasource

import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.Gson
import com.luisitolentino.photos.data.model.PhotoListResponse
import com.luisitolentino.photos.domain.entity.Photo
import com.luisitolentino.photos.framework.contants.Constants
import java.net.HttpURLConnection

class PhotoDataSource(
    private val responseListener: Response.Listener<List<Photo>>,
    errorListener: Response.ErrorListener
) : Request<List<Photo>>(Method.GET, Constants.PHOTOS_ENDPOINT, errorListener) {
    override fun parseNetworkResponse(response: NetworkResponse?): Response<List<Photo>> =
        if (response?.statusCode == HttpURLConnection.HTTP_OK || response?.statusCode == HttpURLConnection.HTTP_NOT_MODIFIED) {
            String(response.data).run {
                Response.success(
                    Gson().fromJson(this, PhotoListResponse::class.java).toPhotoList(),
                    HttpHeaderParser.parseCacheHeaders(response)
                )
            }
        } else {
            Response.error(VolleyError())
        }

    override fun deliverResponse(response: List<Photo>?) {
        responseListener.onResponse(response)
    }
}
