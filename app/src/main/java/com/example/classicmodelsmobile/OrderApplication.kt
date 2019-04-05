package com.example.classicmodelsmobile

import android.app.Application
import com.example.classicmodelsmobile.repository.DbHelper

class OrderApplication: Application() {

    var db: DbHelper = DbHelper(this)

}