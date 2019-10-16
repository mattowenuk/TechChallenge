package com.example.techchallenge.data.db

import java.util.HashMap

class Visit(val map: MutableMap<String, Any?>) {
    var _id: Long by map
    var startTime: Long by map
    var duration: Long? by map
    var endTime: Long by map

    constructor(startTime: Long, duration: Long?, endTime: Long)
            : this(HashMap()) {
        this.startTime = startTime
        this.duration = duration
        this.endTime = endTime
    }
}

class Stat(val map: MutableMap<String, Any?>) {
    var _id: Long by map
    var visitId: Long by map
    var time: Long by map
    var location: String by map
    var category: String by map

    constructor(visitId: Long, time: Long, location: String, category: String)
            : this(HashMap()) {
        this.visitId = visitId
        this.time= time
        this.location = location
        this.category = category
    }
}