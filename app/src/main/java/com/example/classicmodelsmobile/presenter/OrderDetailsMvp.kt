package com.example.classicmodelsmobile.presenter

import com.example.classicmodelsmobile.model.Order
import com.example.classicmodelsmobile.model.OrderDetails

/**
 * Interfaces specifying necessary CRUD functions for the order details entity.
 */

class OrderDetailsMvp {

    // Implemented by ActivityOrderDetails
    interface OrderDetailsView {
        fun setData(listOrders: List<OrderDetails>)
        fun setEmpty()
        fun setResult(message: String)
        fun onLoad(isLoad: Boolean)

    }

    // Implemented by Order Presenter
    interface OrderPresenter {
        fun insertData(orderDetail: OrderDetails)
        fun deleteData(orderDetail: OrderDetails)
        fun updateData(orderDetail: OrderDetails)
        fun getAllData(order: Order)
    }
}