package com.example.techchallenge.model

import java.lang.Exception

//the required model data selected from the raw data
data class ModelResultException(val modelResult: ModelResult?, val exception: Exception?)

data class ModelResult(val topicTitle: String, val items: List<Item>)

data class Item(val title: String, val url: String, val lastUpdatedText: String, val image: Image)

data class Image(val small: String, val altText: String)