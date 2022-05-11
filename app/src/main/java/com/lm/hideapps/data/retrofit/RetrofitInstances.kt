package com.lm.hideapps.data.retrofit

import com.lm.hideapps.data.api.AnimeApi
import com.lm.hideapps.data.api.MemesApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstances {
	
	val memesApi: MemesApi by lazy {
		Retrofit.Builder()
			.baseUrl(MEMES_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.build().create(MemesApi::class.java)
	}
	
	val animeApi: AnimeApi by lazy {
		Retrofit.Builder()
			.baseUrl(ANIME_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.build().create(AnimeApi::class.java)
	}
	
	private const val MEMES_URL = "https://api.imgflip.com"
	
	private const val ANIME_URL = "https://api.jikan.moe/"
	
}




