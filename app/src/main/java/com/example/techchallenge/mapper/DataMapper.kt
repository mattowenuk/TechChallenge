package com.example.techchallenge.mapper

import com.example.techchallenge.data.DataResult
import com.example.techchallenge.model.Image
import com.example.techchallenge.model.Item as ModelItem
import com.example.techchallenge.data.Item as DataItem
import com.example.techchallenge.model.ModelResult

class DataMapper {

    fun convertFromDataToModel(dataResult: DataResult) : ModelResult {

        return ModelResult(dataResult.data.topic.title, convertDataItemsToModelItems(dataResult.data.items))
    }



    private fun convertDataItemsToModelItems(dataItemList: List<DataItem>) : List<ModelItem> {

        return dataItemList.map { dataItem -> convertDataItemToModelItem(dataItem.copy())
        }
    }

    private fun convertDataItemToModelItem(dataItem: DataItem) : ModelItem {
        return ModelItem(dataItem.title, dataItem.url, dataItem.lastUpdatedText, Image(dataItem.image.small, dataItem.image.altText))
    }
}