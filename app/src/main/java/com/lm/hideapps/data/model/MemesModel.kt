package com.lm.hideapps.data.model

data class MemesModel(
	val id: String = "",
	val name: String = "",
	val url: String = "",
	val width: Int = 0,
	val height: Int = 0,
	val box_count: Int = 0,
)


data class MemesRequest(val data: Memes)

data class Memes(val memes: List<MemesModel>)
