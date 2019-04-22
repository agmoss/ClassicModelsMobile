package com.example.classicmodelsmobile.presenter


import com.example.classicmodelsmobile.model.Order
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut
import com.google.gson.Gson

/**
 * API requests/ populate data on the UI. Implements OrderPresenter from OrderMvp
 * @param orderView = MainActivity presenter
 */


class OrderPresenter(orderView: OrderMvp.OrderView) : OrderMvp.OrderPresenter {

    private val orderView: OrderMvp.OrderView = orderView

    override fun insertData(order: Order) {

        val orderJson = Gson().toJson(order)

        val URL = "https://classicmodelsrest.azurewebsites.net/api/orders/"

        URL.httpPost().header("Content-Type" to "application/json").body(orderJson.toString())
            .response { req, res, result ->
                val (res, err) = result


                println(result)

                if (err == null) {
                    orderView.setResult("New data added")
                    //TODO: Refresh (comes from call to getAllData()
                } else {
                    orderView.setResult(err.toString() + res.toString())
                }
            }

    }

    override fun getAllData() {

        val URL = "https://classicmodelsrest.azurewebsites.net/api/orders"

        var Orders = ArrayList<Order>()

        URL.httpGet().responseObject(Order.Deserializer()) { request, response, result ->
            val (order, err) = result

            println("API RESULT $result")
            //Add to ArrayList
            order?.forEach { ord ->

                Orders.add(ord)
            }
            orderView.setEmpty()
            orderView.setData(Orders)
        }
    }

    override fun deleteData(order: Order) {

        val URL = "https://classicmodelsrest.azurewebsites.net/api/orders/" + order.orderNumber

        URL.httpDelete().header("Content-Type" to "application/json").response { req, res, result ->

            val (res, err) = result

            if (err == null) {
                orderView.setResult("Data Deleted")
                //TODO: Refresh (comes from call to getAllData()
            } else {
                orderView.setResult(err.toString() + res.toString())
            }
        }
    }

    override fun updateData(order: Order) {

        val orderJson = Gson().toJson(order)

        val URL = "https://classicmodelsrest.azurewebsites.net/api/orders/" + order.orderNumber

        URL.httpPut().header("Content-Type" to "application/json").body(orderJson.toString())
            .response { req, res, result ->
                val (res, err) = result

                println(orderJson)
                println(result)

                if (err == null) {
                    orderView.setResult("Data updated")
                    //TODO: Refresh (comes from call to getAllData()
                } else {
                    orderView.setResult(err.toString() + res.toString())
                }
            }
    }
}