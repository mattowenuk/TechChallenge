package com.example.techchallenge.mapper

//cast same name classes for ease
import com.example.techchallenge.data.DataResult
import com.example.techchallenge.model.Image
import com.example.techchallenge.model.Item as ModelItem
import com.example.techchallenge.data.Item as DataItem
import com.example.techchallenge.model.ModelResult

class DataMapper {

    fun convertFromDataToModel(dataResult: DataResult) : ModelResult {
        //returns the title and uses function, receiving dataItem list and returning modelItem list
        return ModelResult(dataResult.data.topic.title, convertDataItemsToModelItems(dataResult.data.items))
    }

    private fun convertDataItemsToModelItems(dataItemList: List<DataItem>) : List<ModelItem> {
        //loops through the data list and copies the item to the next convert function
        return dataItemList.map { dataItem -> convertDataItemToModelItem(dataItem.copy()) }
    }

    private fun convertDataItemToModelItem(dataItem: DataItem) : ModelItem {
        //creates the model item using the data item properties
        return ModelItem(dataItem.title, dataItem.url, dataItem.lastUpdatedText, Image(dataItem.image.small, dataItem.image.altText))
    }
}