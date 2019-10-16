package com.example.techchallenge.data.db

object VisitTable {
    const val NAME = "VisitTable"
    const val ID = "_id"
    const val START = "startTime"
    const val DURATION = "duration"
    const val END = "endTime"
}

object StatTable {
    const val NAME = "StatTable"
    const val ID = "_id"
    const val VISIT_ID = "visitId"
    const val TIME = "time"
    const val LOCATION = "location"
    const val CATEGORY = "category"
}