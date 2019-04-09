package com.example.classicmodelsmobile.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import java.io.Serializable

/**
 * Represents an order details entity from the database/API
 */

class OrderDetails(
    val orderNumber: Int,
    val productCode: String,
    val quantityOrdered: String,
    val priceEach: String,
    val orderLineNumber: Int
) : Serializable {

    /**
     * Convert an array of order details to a JSON array (for setting data in the recyclerview)
     */

    class Deserializer : ResponseDeserializable<Array<OrderDetails>> {
        override fun deserialize(content: String): Array<OrderDetails>? =
            Gson().fromJson(content, Array<OrderDetails>::class.java)
    }
}