package com.example.classicmodelsmobile

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.*
import com.example.classicmodelsmobile.model.Order
import com.example.classicmodelsmobile.presenter.OrderMvp
import com.example.classicmodelsmobile.presenter.OrderPresenter
import com.example.classicmodelsmobile.OrderApplication

import com.example.classicmodelsmobile.view.DialogOrder

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_list.view.*
import java.util.ArrayList

class MainActivity : AppCompatActivity(),OrderMvp.OrderView, SwipeRefreshLayout.OnRefreshListener {

    var adapter: RvAdapter = RvAdapter(ArrayList<Order>())
    var presenter: OrderPresenter? = null
    var dialog:DialogOrder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvMain.layoutManager = LinearLayoutManager(this)
        rvMain.adapter = adapter

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
        txtEmpty.visibility = View.GONE
        adapter.update(listOrders as MutableList<Order>)
    }

    override fun setEmpty() {
        txtEmpty.visibility = View.VISIBLE
    }

    override fun setResult(message: String) {
        Snackbar.make(layRoot, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onRefresh() {
        presenter!!.getAllData()
    }

    override fun onLoad(isLoad: Boolean) {
        refresh.isRefreshing = isLoad
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
                val menuOption: PopupMenu.OnMenuItemClickListener = PopupMenu.OnMenuItemClickListener {
                        menuItem:MenuItem ->
                    if(menuItem.itemId == R.id.menuDelete){
                        this@MainActivity.presenter?.deleteData(lsOrders[position].orderNumber)


                        //TODO: Need a refresh
                        true
                    }else{

                        this@MainActivity.dialog?.showDialog(true, lsOrders[position])
                        true
                    }
                }

                popUp.setOnMenuItemClickListener(menuOption)
                popUp.show()

            }
        }

        override fun getItemCount() = lsOrders.size

        fun update(lsOrders: MutableList<Order>){
            this.lsOrders = lsOrders
            notifyDataSetChanged()
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindValue(order: Order) {

            with(order){
                itemView.txtTitle.text = "$orderNumber"
                itemView.txtDesc.text = "$status"
                itemView.txtDate.text = "$orderDate"

            }
        }
    }

}
