package com.lm.hideapps.data.mapper

import com.lm.core.Mapper
import com.lm.core.Resource
import com.lm.hideapps.data.model.MemesModel
import com.lm.hideapps.data.model.MemesRequest
import okhttp3.ResponseBody

interface MemesMapper : Mapper.DataToUI<MemesRequest, Resource<List<MemesModel>>> {
	
	class Base : MemesMapper {
		override fun map(request: MemesRequest) = Resource.Success(request.data.memes)
		
		override fun map(throwable: Throwable): Resource<List<MemesModel>> =
			Resource.Failure(throwable)
		
		override fun map(error: ResponseBody?): Resource<List<MemesModel>> =
			Resource.Exception(error)
		
		override fun map() = Resource.Loading
	}
}

