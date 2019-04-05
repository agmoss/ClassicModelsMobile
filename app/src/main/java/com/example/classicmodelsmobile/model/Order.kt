package com.example.classicmodelsmobile.model

import java.util.*
import kotlin.collections.ArrayList

class Order(val orderNumber:Int,
            val orderDate: String,
            val requiredDate: String,
            val shippedDate: String,
            val status: String,
            val comments:String,
            val customerNumber: Int,
            val details: ArrayList<OrderDetails> = arrayListOf()) {


}