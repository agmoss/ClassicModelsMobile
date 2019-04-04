package com.example.classicmodelsmobile

import android.app.Application
import com.example.classicmodelsmobile.repository.DbHelper

class OrderApplication: Application() {

    public var db: DbHelper = DbHelper(this)
    override fun onCreate() {
        super.onCreate()

    }

}