package com.lm.hideapps.data.mapper

import com.lm.core.Mapper
import com.lm.core.Resource
import com.lm.hideapps.data.model.AnimeRequest
import com.lm.hideapps.data.model.AnimeModel
import okhttp3.ResponseBody

interface AnimeMapper : Mapper.DataToUI<AnimeRequest, Resource<List<AnimeModel>>> {
	class Base : AnimeMapper {
		override fun map(request: AnimeRequest) = Resource.Success(request.results)
		
		override fun map(throwable: Throwable): Resource<List<AnimeModel>> =
			Resource.Failure(throwable)
		
		override fun map(error: ResponseBody?): Resource<List<AnimeModel>> =
			Resource.Exception(error)
		
		override fun map() = Resource.Loading
	}
}
