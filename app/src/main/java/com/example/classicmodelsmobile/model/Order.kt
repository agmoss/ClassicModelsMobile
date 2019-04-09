package com.example.classicmodelsmobile.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import java.io.Serializable

/**
 * Represents an order entity from the database/API
 */

class Order(
    val orderNumber: Int,
    val orderDate: String,
    val requiredDate: String,
    val shippedDate: String,
    val status: String,
    val comments: String,
    val customerNumber: Int,
    val details: ArrayList<OrderDetails> = arrayListOf()
) : Serializable {

    /**
     * Convert an array of orders to a JSON array (for setting data in the recyclerview)
     */
    class Deserializer : ResponseDeserializable<Array<Order>> {
        override fun deserialize(content: String): Array<Order>? = Gson().fromJson(content, Array<Order>::class.java)
    }


}