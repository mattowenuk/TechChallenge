package com.example.techchallenge.model

data class ModelResult(val topicTitle: String, val items: List<Item>)

data class Item(val title: String, val url: String, val lastUpdatedText: String, val image: Image)

data class Image(val small: String, val altText: String)