package com.example.classicmodelsmobile.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.classicmodelsmobile.model.Order
import com.example.classicmodelsmobile.model.OrderDetails
import java.util.ArrayList

class DbHelper: SQLiteOpenHelper {

    constructor(context: Context) : super(context, "DB", null, 1)

    // Orders
    val TB_ORDER : String = "tb_order"
    val COL_ORDERNUMBER : String = "orderNumber"
    val COL_ORDERDATE : String = "orderDate"
    val COL_REQUIREDDATE : String = "requiredDate"
    val COL_SHIPPEDDATE : String = "shippedDate"
    val COL_STATUS : String = "status"
    val COL_COMMENTS : String = "comments"
    val COL_CUSTOMERNUMBER : String = "customerNumber"

    // Order Details
    val TB_ORDERDETAILS: String = "tb_orderdetails"
    //Order number from above
    val COL_PRODUCTCODE: String = "productCode"
    val COL_QUANTITYORDERED: String = "quantityOrdered"
    val COL_PRICEEACH : String = "priceEach"
    val COL_ORDERLINENUMBER: String = "orderLineNumber"

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun removeAll() {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
        //val db = helper.getWritableDatabase() // helper is object extends SQLiteOpenHelper

        val db: SQLiteDatabase = this.writableDatabase
        db.delete(this.TB_ORDER, null, null)
        //db.delete(DatabaseHelper.TAB_USERS_GROUP, null, null)
    }

    override fun onCreate(db: SQLiteDatabase?) {

        var createOrderTable = "CREATE TABLE $TB_ORDER ($COL_ORDERNUMBER INTEGER PRIMARY KEY AUTOINCREMENT, $COL_ORDERDATE VARCHAR(10), $COL_REQUIREDDATE VARCHAR(10), $COL_SHIPPEDDATE VARCHAR(10), $COL_STATUS TEXT, $COL_COMMENTS TEXT, $COL_CUSTOMERNUMBER INTEGER)"
        var createOrderDetailsTable = "CREATE TABLE $TB_ORDERDETAILS($COL_ORDERNUMBER INTEGER PRIMARY KEY AUTOINCREMENT, $COL_PRODUCTCODE INTEGER, $COL_QUANTITYORDERED VARCHAR(10),$COL_PRICEEACH VARCHAR(10), $COL_ORDERLINENUMBER INTEGER)"

        db?.execSQL(createOrderDetailsTable)
        db?.execSQL(createOrderTable)

    }

    fun getAllOrders(): List<Order>{

        //removeAll()

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

    fun getOrderDetails(): List<OrderDetails>{
        var lsOrderDetails: MutableList<OrderDetails> = ArrayList<OrderDetails>()

        val db: SQLiteDatabase = this.readableDatabase
        val sql: String = "SELECT * FROM $TB_ORDERDETAILS"
        val cursor: Cursor = db.rawQuery(sql, null)
        if(cursor.moveToFirst()) {
            do {
                val order: OrderDetails = OrderDetails(cursor.getInt(0), cursor.getInt(1), cursor.getString(2)
                    , cursor.getString(3),cursor.getInt(4)
                )
                lsOrderDetails.add(order)

            } while (cursor.moveToNext())
        }

        return lsOrderDetails
    }


    fun insertData(order: Order): Boolean{
        val db: SQLiteDatabase = this.writableDatabase

        var cv = ContentValues()

        // Orders
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

        // Orders
        cv.put(COL_ORDERDATE, order.orderDate)
        cv.put(COL_REQUIREDDATE, order.requiredDate)
        cv.put(COL_SHIPPEDDATE, order.shippedDate)
        cv.put(COL_STATUS, order.status)
        cv.put(COL_COMMENTS,order.comments)
        cv.put(COL_CUSTOMERNUMBER, order.customerNumber)


        val id = order.orderNumber

        return db.update(TB_ORDER, cv, "$COL_ORDERNUMBER = $id", null) > 0

    }

    fun updateData(detail: OrderDetails): Boolean{
        val db: SQLiteDatabase = this.readableDatabase

        var cv = ContentValues()

        // Order details
        cv.put(COL_ORDERNUMBER,detail.orderNumber)
        cv.put(COL_PRODUCTCODE,detail.productCode)
        cv.put(COL_QUANTITYORDERED,detail.quantityOrdered)
        cv.put(COL_PRICEEACH,detail.priceEach)
        cv.put(COL_ORDERLINENUMBER,detail.orderLineNumber)


        //TODO : Composite key
        val id = detail.orderNumber

        return db.update(TB_ORDER, cv, "$COL_ORDERNUMBER = $id", null) > 0

    }

    fun deleteData(id: Int): Boolean{
        val db: SQLiteDatabase = this.readableDatabase

        return db.delete(TB_ORDER, "$COL_ORDERNUMBER = $id", null) > 0

    }


    fun insertData(detail:OrderDetails):Boolean{
        val db: SQLiteDatabase = this.writableDatabase

        var cv = ContentValues()

        cv.put(COL_ORDERNUMBER,detail.orderNumber)
        cv.put(COL_PRODUCTCODE,detail.productCode)
        cv.put(COL_QUANTITYORDERED,detail.quantityOrdered)
        cv.put(COL_PRICEEACH,detail.priceEach)
        cv.put(COL_ORDERLINENUMBER,detail.orderLineNumber)

        return db.insert(TB_ORDERDETAILS,null,cv) > 0

    }

    fun deleteData(detail:OrderDetails):Boolean{
        val db: SQLiteDatabase = this.readableDatabase

        // Unchecked
        return db.delete(TB_ORDERDETAILS,"$COL_ORDERNUMBER = $detail.orderNumber $COL_PRODUCTCODE = ${detail.productCode}",null) > 0

    }
}