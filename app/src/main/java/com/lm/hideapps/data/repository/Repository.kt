package com.lm.hideapps.data.repository

import com.lm.core.Callback.startRequest
import com.lm.core.Resource
import com.lm.core.log
import com.lm.hideapps.data.model.AnimeRequest
import com.lm.hideapps.data.model.MemesRequest
import com.lm.hideapps.data.retrofit.RetrofitInstances.animeApi
import com.lm.hideapps.data.retrofit.RetrofitInstances.memesApi
import kotlinx.coroutines.flow.Flow

interface Repository {
	
	suspend fun memes(): Flow<Resource<MemesRequest>>
	
	suspend fun anime(): Flow<Resource<AnimeRequest>>
	
	class Base : Repository {
		
		override suspend fun memes() = memesApi.fetchMemes().startRequest()
		
		override suspend fun anime() = animeApi.fetchAnime().startRequest()
		
	}
}