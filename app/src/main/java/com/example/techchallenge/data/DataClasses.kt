package com.example.techchallenge.data

data class DataResult(val data: Data)

data class Data(val topic: Topic, val items: List<Item>)

data class Topic(val title: String, val url: String)

data class Item(val type: String, val title: String, val url: String, val sectionLabel: String,
                val sectionUrl: String, val isLive: Boolean, val lastUpdatedText: String,
                val mediaType: String?, val contentType: String?, val lastUpdatedTimestamp: String, val image: Image)

data class Image(val small: String, val medium: String, val large: String, val altText: String, val copyrightHolder: String)