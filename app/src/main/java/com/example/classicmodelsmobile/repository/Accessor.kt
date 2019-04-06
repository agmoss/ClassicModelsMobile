package com.example.classicmodelsmobile.repository

import com.example.classicmodelsmobile.model.Order
import com.github.kittinunf.fuel.httpGet


class Accessor {

    val URL = "https://classicmodelsrest.azurewebsites.net/api/orders"

    fun getAllOrders(): ArrayList<Order> {


        var Orders = ArrayList<Order>()


        URL.httpGet().responseObject(Order.Deserializer()) { request, response, result ->
            val (order, err) = result

            println("API RESULT $result")
            //Add to ArrayList
            order?.forEach { ord ->

                Orders.add(ord)
            }

        }

        return Orders
    }

}