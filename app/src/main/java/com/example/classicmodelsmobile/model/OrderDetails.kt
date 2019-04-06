package com.example.classicmodelsmobile.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import java.io.Serializable

class OrderDetails(
    val orderNumber: Int,
    val productCode: String,
    val quantityOrdered: String,
    val priceEach: String,
    val orderLineNumber: Int
) : Serializable {

    class Deserializer : ResponseDeserializable<Array<OrderDetails>> {
        override fun deserialize(content: String): Array<OrderDetails>? =
            Gson().fromJson(content, Array<OrderDetails>::class.java)
    }
}