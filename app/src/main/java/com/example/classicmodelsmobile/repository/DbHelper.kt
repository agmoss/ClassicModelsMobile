package com.example.classicmodelsmobile.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.classicmodelsmobile.model.Order
import java.util.ArrayList

class DbHelper: SQLiteOpenHelper {

    constructor(context: Context) : super(context, "DB", null, 1)

    val TB_ORDER : String = "tb_order"
    val COL_ORDERNUMBER : String = "orderNumber"
    val COL_ORDERDATE : String = "orderDate"
    val COL_REQUIREDDATE : String = "requiredDate"
    val COL_SHIPPEDDATE : String = "shippedDate"
    val COL_STATUS : String = "status"
    val COL_COMMENTS : String = "comments"
    val COL_CUSTOMERNUMBER : String = "customerNumber"


    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    override fun onCreate(db: SQLiteDatabase?) {

        var sql: String = "CREATE TABLE $TB_ORDER ($COL_ORDERNUMBER INTEGER PRIMARY KEY AUTOINCREMENT, $COL_ORDERDATE VARCHAR(10), $COL_REQUIREDDATE VARCHAR(10), $COL_SHIPPEDDATE VARCHAR(10), $COL_STATUS TEXT, $COL_COMMENTS TEXT, $COL_CUSTOMERNUMBER INTEGER)"
        db?.execSQL(sql)
    }

    fun getAllOrders(): List<Order>{
        var lsOrders: MutableList<Order> = ArrayList<Order>()

        val db: SQLiteDatabase = this.readableDatabase
        val sql: String = "SELECT * FROM $TB_ORDER"
        val cursor: Cursor = db.rawQuery(sql, null)
        if(cursor.moveToFirst()) {
            do {
                val order: Order = Order(cursor.getInt(0), cursor.getString(1), cursor.getString(2)
                    , cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getInt(6)
                )
                lsOrders.add(order)

            } while (cursor.moveToNext())
        }

        return lsOrders
    }
    fun insertData(order: Order): Boolean{
        val db: SQLiteDatabase = this.writableDatabase

        var cv = ContentValues()
        cv.put(COL_ORDERDATE, order.orderDate)
        cv.put(COL_REQUIREDDATE, order.requiredDate)
        cv.put(COL_SHIPPEDDATE, order.shippedDate)
        cv.put(COL_STATUS, order.status)
        cv.put(COL_COMMENTS,order.comments)
        cv.put(COL_CUSTOMERNUMBER, order.customerNumber)


        return db.insert(TB_ORDER, null, cv) > 0

    }


    fun updateData(order: Order): Boolean{
        val db: SQLiteDatabase = this.readableDatabase

        var cv = ContentValues()

        cv.put(COL_ORDERDATE, order.orderDate)
        cv.put(COL_REQUIREDDATE, order.requiredDate)
        cv.put(COL_SHIPPEDDATE, order.shippedDate)
        cv.put(COL_STATUS, order.status)
        cv.put(COL_COMMENTS,order.comments)
        cv.put(COL_CUSTOMERNUMBER, order.customerNumber)

        val id = order.orderNumber

        return db.update(TB_ORDER, cv, "$COL_ORDERNUMBER = $id", null) > 0

    }

    fun deleteData(id: Int): Boolean{
        val db: SQLiteDatabase = this.readableDatabase

        return db.delete(TB_ORDER, "$COL_ORDERNUMBER = $id", null) > 0

    }
}