package com.example.classicmodelsmobile.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.classicmodelsmobile.OrderApplication
import com.example.classicmodelsmobile.R
import com.example.classicmodelsmobile.model.Order
import com.example.classicmodelsmobile.presenter.OrderMvp
import com.example.classicmodelsmobile.presenter.OrderPresenter
import com.example.classicmodelsmobile.view.DialogOrder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_list.view.*
import java.util.*

class MainActivity : AppCompatActivity(), OrderMvp.OrderView, SwipeRefreshLayout.OnRefreshListener {

    var adapter: RvAdapter = RvAdapter(ArrayList<Order>())
    var presenter: OrderPresenter? = null
    var dialog: DialogOrder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvDetail_OrderDetail.layoutManager = LinearLayoutManager(this)
        rvDetail_OrderDetail.adapter = adapter

        val app: OrderApplication = this.application as OrderApplication
        presenter = OrderPresenter(this, app.db)
        dialog = DialogOrder(this, presenter!!)

        presenter?.getAllData()

        //setSupportActionBar(toolbar)

        fabAdd.setOnClickListener { view ->
            dialog?.clear()
            dialog?.showDialog(false, null)
        }
    }

    override fun setData(listOrders: List<Order>) {
        txtEmptyDetail.visibility = View.GONE
        adapter.update(listOrders as MutableList<Order>)
    }

    override fun setEmpty() {
        txtEmptyDetail.visibility = View.VISIBLE
    }

    override fun setResult(message: String) {
        Snackbar.make(layRootDetail, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onRefresh() {
        presenter!!.getAllData()
    }

    override fun onLoad(isLoad: Boolean) {
        refreshDetail.isRefreshing = isLoad
    }


    fun switchAct() {
        val intent = Intent(this, ActivityOrderDetails::class.java)
        // To pass any data to next activity
        //intent.putExtra("keyIdentifier", value)
        // start your next activity
        startActivity(intent)
    }


    inner class RvAdapter(lsOrders: MutableList<Order>) : RecyclerView.Adapter<ViewHolder>() {

        var lsOrders: MutableList<Order> = lsOrders

        override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): ViewHolder {
            val view: View = LayoutInflater.from(p0?.context).inflate(R.layout.item_list, null, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(p0: ViewHolder, position: Int) {

            p0?.bindValue(lsOrders[position])

            p0?.itemView?.vOption!!.setOnClickListener {
                val popUp: PopupMenu = PopupMenu(p0?.itemView!!.context, p0.itemView.vOption)
                popUp.inflate(R.menu.menu_more)
                val menuOption: PopupMenu.OnMenuItemClickListener =
                    PopupMenu.OnMenuItemClickListener { menuItem: MenuItem ->
                        if (menuItem.itemId == R.id.menuDelete) {
                            this@MainActivity.presenter?.deleteData(lsOrders[position].orderNumber)

                            //TODO: Need a refresh
                            true
                        } else if (menuItem.itemId == R.id.menuEdit) {

                            this@MainActivity.dialog?.showDialog(true, lsOrders[position])
                            true
                        } else {

                            // SWITCH ACTIVITY
                            this@MainActivity.switchAct()
                            true
                        }
                    }

                popUp.setOnMenuItemClickListener(menuOption)
                popUp.show()

            }
        }


        override fun getItemCount() = lsOrders.size

        fun update(lsOrders: MutableList<Order>) {
            this.lsOrders = lsOrders
            notifyDataSetChanged()
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindValue(order: Order) {

            with(order) {
                itemView.txtTitle.text = "$orderNumber"
                itemView.txtDesc.text = "$status"
                itemView.txtDate.text = "$orderDate"

            }
        }
    }

}