package com.lm.hideapps.data.api

import com.lm.hideapps.data.model.MemesRequest
import retrofit2.Call
import retrofit2.http.GET

interface MemesApi {
	@GET("get_memes")
	fun fetchMemes(): Call<MemesRequest>
}
