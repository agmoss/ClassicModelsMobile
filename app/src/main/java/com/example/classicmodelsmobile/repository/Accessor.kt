package com.example.classicmodelsmobile.repository

import android.media.session.MediaSession
import android.telecom.Call
import com.example.classicmodelsmobile.model.Order
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.getAs
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.jetbrains.anko.doAsync
import javax.security.auth.callback.Callback
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class Accessor {

    val URL = "https://classicmodelsrest.azurewebsites.net/api/orders"

     fun getAllOrders() : ArrayList<Order> {


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