package com.example.techchallenge.model

//the required model data selected from the raw data
data class ModelResult(val topicTitle: String, val items: List<Item>)

data class Item(val title: String, val url: String, val lastUpdatedText: String, val image: Image)

data class Image(val small: String, val altText: String)