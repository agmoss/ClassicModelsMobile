package com.example.classicmodelsmobile.presenter

import com.example.classicmodelsmobile.model.Order

/**
 * Interfaces specifying necessary CRUD functions for the orders entity
 */

open class OrderMvp {

    // Implemented by MainActivity
    interface OrderView {
        fun setData(listOrders: List<Order>)
        fun setEmpty()
        fun setResult(message: String)
        fun onLoad(isLoad: Boolean)

    }

    // Implemented by OrderPresenter
    interface OrderPresenter {
        fun insertData(order: Order)
        fun deleteData(order: Order)
        fun updateData(order: Order)
        fun getAllData()
    }
}