package com.lm.hideapps.data.api

import com.lm.hideapps.data.model.AnimeRequest
import retrofit2.Call
import retrofit2.http.GET

interface AnimeApi {
	@GET("v3/search/anime?q=naruto")
	fun fetchAnime(): Call<AnimeRequest>
}
