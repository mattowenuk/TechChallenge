package com.example.techchallenge.mapper

import com.example.techchallenge.data.DataResult

class DataMapper {

    fun convertFromDataToModel(dataResult: DataResult) : MutableList<String> {
        val titleList = mutableListOf<String>()
        dataResult.data.items.forEach { items -> titleList.add(items.title)}
        return titleList
    }
}